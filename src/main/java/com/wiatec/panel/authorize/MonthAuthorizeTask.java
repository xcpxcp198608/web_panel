package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.ApplicationContextHelper;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MonthAuthorizeTask {

    private static final Logger logger = LoggerFactory.getLogger(MonthAuthorizeTask.class);

    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    private static SqlSession sqlSession;

    static {
        sqlSession = (SqlSession) ApplicationContextHelper.getApplicationContext().getBean("sqlSessionTemplate");
    }

    @Scheduled(cron="0 0 2 1-31 1-12 1-7")
    public void executeUploadBackTask() {
        logger.debug("MonthAuthorizeTask started");
        charge();
    }

    private void charge(){
        commissionCategoryDao = sqlSession.getMapper(CommissionCategoryDao.class);
        authRentUserDao = sqlSession.getMapper(AuthRentUserDao.class);
        authorizeTransactionDao = sqlSession.getMapper(AuthorizeTransactionDao.class);
        CommissionCategoryInfo b1 = commissionCategoryDao.selectOne("B1");
        CommissionCategoryInfo p1 = commissionCategoryDao.selectOne("P1");
        CommissionCategoryInfo p2 = commissionCategoryDao.selectOne("P2");

        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectAll();
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

    public void checkOutByMonth(CommissionCategoryInfo commissionCategoryInfo, AuthRentUserInfo authRentUserInfo){
        String today = TimeUtil.getStrDate();
        logger.debug("= today= {}", today);
        logger.debug("= active= {}", authRentUserInfo.getActivateTime());
        for(int i = 1; i <= commissionCategoryInfo.getExpires(); i ++){
            String date = TimeUtil.getExpiresDate(authRentUserInfo.getActivateTime(), i);
            if(today.equals(date)){
                logger.debug("= {} need check out on this month", date);
                logger.debug("= checking check out on this month");
                AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
                authorizeTransactionInfo.setSalesId(authRentUserInfo.getSalesId());
                authorizeTransactionInfo.setDealerId(authRentUserInfo.getDealerId());
                authorizeTransactionInfo.setClientKey(authRentUserInfo.getClientKey());
                authorizeTransactionInfo.setCategory(authRentUserInfo.getCategory());
                authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
                authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
                authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
                authorizeTransactionInfo.setAmount(authRentUserInfo.getMonthPay());
                authorizeTransactionInfo.setDeposit(0);
                authorizeTransactionInfo.setLdCommission(authRentUserInfo.getLdCommission());
                authorizeTransactionInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
                authorizeTransactionInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
                authorizeTransactionInfo.setType(AuthorizeTransactionInfo.TYPE_MONTHLY);
                authorizeTransactionInfo.setCreateTime(today.substring(0, 7));
                //check is already check out on this month
                if(authorizeTransactionDao.countByKeyAndDate(authorizeTransactionInfo) == 1){
                    logger.debug("= {} already check out on this month", authRentUserInfo.getClientKey());
                    logger.debug("====================================================================");
                    return;
                }
                logger.debug("= execute check out on this month");
                AuthorizeTransactionInfo authorizeTransactionInfo1 = AuthorizeTransaction.charge(authorizeTransactionInfo);
                if(authorizeTransactionInfo1 != null && "approved".equals(authorizeTransactionInfo1.getStatus())){
                    logger.debug("= {} check out month successfully", authRentUserInfo.getClientKey());
                    logger.debug("====================================================================");
                    authorizeTransactionDao.insertOne(authorizeTransactionInfo1);
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
}