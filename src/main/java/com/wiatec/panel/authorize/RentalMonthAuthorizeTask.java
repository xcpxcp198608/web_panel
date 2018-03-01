package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.RentalInvoiceUtil;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionRentalDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author patrick
 */
@Component
public class RentalMonthAuthorizeTask {

    private static final Logger logger = LoggerFactory.getLogger(RentalMonthAuthorizeTask.class);

    private AuthRentUserDao authRentUserDao;
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;

    private static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    @Scheduled(cron="0 0 4 1-31 1-12 1-7")
    public void executeUploadBackTask() {
        logger.debug("MonthAuthorizeTask started");
        try {
            charge();
        }catch (Exception e){
            logger.error("Exception: ", e);
        }
    }

    private void charge(){
        CommissionCategoryDao commissionCategoryDao = sqlSession.getMapper(CommissionCategoryDao.class);
        authRentUserDao = sqlSession.getMapper(AuthRentUserDao.class);
        authorizeTransactionRentalDao = sqlSession.getMapper(AuthorizeTransactionRentalDao.class);
        CommissionCategoryInfo b1 = commissionCategoryDao.selectOne("B1");
        CommissionCategoryInfo p1 = commissionCategoryDao.selectOne("P1");
        CommissionCategoryInfo p2 = commissionCategoryDao.selectOne("P2");

        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDE);
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if("canceled".equals(authRentUserInfo.getStatus()) || "limited".equals(authRentUserInfo.getStatus())){
                continue;
            }
            CommissionCategoryInfo commissionCategoryInfo;
            switch (authRentUserInfo.getCategory()){
                case "B1":
                    commissionCategoryInfo = b1;
                    break;
                case "P1":
                    commissionCategoryInfo = p1;
                    break;
                case "P2":
                    commissionCategoryInfo = p2;
                    break;
                default:
                    commissionCategoryInfo = b1;
                    break;
            }
            if(!TimeUtil.isOutExpires(authRentUserInfo.getExpiresTime())){
                logger.debug("====================================================================");
                logger.debug("= prepare execute= {}", authRentUserInfo.getClientKey());
                logger.debug("= prepare execute= {}", authRentUserInfo.getFirstName());
                checkOutByMonth(commissionCategoryInfo, authRentUserInfo);
            }
        }
    }

    private void checkOutByMonth(CommissionCategoryInfo commissionCategoryInfo, AuthRentUserInfo authRentUserInfo){
        String today = TimeUtil.getStrDate();
        logger.debug("= today= {}", today);
        logger.debug("= active= {}", authRentUserInfo.getActivateTime());
        for(int i = 1; i <= commissionCategoryInfo.getExpires(); i ++){
            String date = TimeUtil.getExpiresDate(authRentUserInfo.getActivateTime(), i);
            if(today.equals(date)){
                logger.debug("= {} need check out on this month", date);
                if(AuthRentUserInfo.PAYMENT_CASH.equals(authRentUserInfo.getPaymentType())){
                    logger.debug("= payment method is cash");
                    authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
                    authRentUserDao.updateUserStatus(authRentUserInfo);
                    return;
                }
                if(AuthRentUserInfo.PAYMENT_PAYPAL.equals(authRentUserInfo.getPaymentType())){
                    logger.debug("= payment method is paypal");
                    authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
                    authRentUserDao.updateUserStatus(authRentUserInfo);
                    return;
                }
                logger.debug("= payment method is credit card");
                logger.debug("= checking check out on this month");
                //check is already check out on this month
                if(authorizeTransactionRentalDao.countByKeyAndDate(authRentUserInfo.getClientKey(), today) == 1){
                    logger.debug("= {} already check out on this month", authRentUserInfo.getClientKey());
                    logger.debug("====================================================================");
                    return;
                }
                logger.debug("= execute check out on this month");
                AuthorizeTransactionInfo authorizeTransactionInfo = AuthorizeTransactionInfo
                        .createFromAuthRentUser(authRentUserInfo, authRentUserInfo.getMonthPay() +
                                authRentUserInfo.getMonthPay() * AuthorizeTransactionInfo.TAX);
                AuthorizeTransactionInfo charge = new AuthorizeTransaction().charge(authorizeTransactionInfo);
                if(charge != null && "approved".equals(charge.getStatus())){
                    logger.debug("= {} check out month successfully", authRentUserInfo.getClientKey());
                    logger.debug("====================================================================");
                    AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = AuthorizeTransactionRentalInfo
                            .monthlyFromAuthRentInfo(authRentUserInfo, charge);
                    authorizeTransactionRentalDao.insertOne(authorizeTransactionRentalInfo);
                    handleInvoice(authRentUserInfo, authorizeTransactionRentalInfo, i+1, commissionCategoryInfo.getExpires());
                }else{
                    logger.debug("= check out month failure, deactivate = {}", authRentUserInfo.getClientKey());
                    authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
                    authRentUserDao.updateUserStatus(authRentUserInfo);
                }
                return;
            }
        }
        logger.debug("= {} no need check out", authRentUserInfo.getClientKey());
        logger.debug("====================================================================");
    }

    private void handleInvoice(AuthRentUserInfo authRentUserInfo,
                               AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo, int currentMonth, int totalMonth){
        try {
            List<InvoiceInfo> invoiceInfoList = InvoiceInfoMaker.rentalMonthly(authorizeTransactionRentalInfo, currentMonth, totalMonth);
            String invoicePath = RentalInvoiceUtil.createInvoice(authRentUserInfo,
                    authorizeTransactionRentalInfo.getTransactionId(), invoiceInfoList);
            RentalInvoiceUtil.copyInvoice(invoicePath);
            logger.debug("invoicePath: {}", invoicePath);
            EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LDE);
            emailMaster.setInvoiceContent(authRentUserInfo.getFirstName());
            emailMaster.addAttachment(invoicePath);
            emailMaster.sendMessage(authRentUserInfo.getEmail());
        }catch (Exception e){
            logger.error("Exception: ", e);
        }
    }
}