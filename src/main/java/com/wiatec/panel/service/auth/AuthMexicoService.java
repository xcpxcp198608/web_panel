package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransaction;
import com.wiatec.panel.authorize.AuthorizeTransactionInfo;
import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.security.MD5Util;
import com.wiatec.panel.common.utils.*;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.RentalInvoiceUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.commission.CommissionCategoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 */
@Service
public class AuthMexicoService {

    private final Logger logger = LoggerFactory.getLogger(AuthMexicoService.class);
    private static final int PAYMENT_METHOD_CASH = 0;
    private static final int PAYMENT_METHOD_CREDIT_CARD = 1;
    private static final int PAYMENT_METHOD_PAYPAL = 2;

    @Resource
    private AuthMexicoDao authMexicoDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private DevicePCPDao devicePCPDao;
    @Resource
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;
    @Resource
    private LogPcpUserMonthlyMexicoDao logPcpUserMonthlyMexicoDao;


    public List<AuthRentUserInfo> getCustomers(){
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao
                .selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDM);
        Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if(sessionMap.containsKey(authRentUserInfo.getClientKey())){
                authRentUserInfo.setOnline(true);
            }
        }
        return authRentUserInfoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateCustomerStatus(String status, String clientKey){
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setStatus(status);
        authRentUserInfo.setClientKey(clientKey);
        authRentUserDao.updateUserStatus(authRentUserInfo);
        return ResultMaster.success();
    }

    public ResultInfo customerCashActivate(HttpServletRequest request, String key, String password){
        AuthMexicoInfo authMexicoInfo = getMexicoInfo(request);
        if(!password.equals(authMexicoInfo.getPassword())){
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_ACTIVATE);
        authRentUserInfo.setClientKey(key);
        authRentUserDao.updateUserStatus(authRentUserInfo);
        AuthRentUserInfo authRentUserInfo1 = authRentUserDao.selectOneByClientKey(key);
        devicePCPDao.updateDeviceToRented(authRentUserInfo1.getMac());
        return ResultMaster.success();
    }

    public AuthRentUserInfo getUserByClientKey(String clientKey){
        return authRentUserDao.selectOneByClientKey(clientKey);
    }

    public List<CommissionCategoryInfo> getCommissionCategory(){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao
                .selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDM);
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setFirstPay();
            commissionCategoryInfo.setPrice();
        }
        return commissionCategoryInfoList;
    }


    public ResultInfo<AuthRentUserInfo> createCustomer(HttpServletRequest request, AuthRentUserInfo authRentUserInfo){
        if (authRentUserDao.countOneByMac(authRentUserInfo) == 1) {
            throw new XException(EnumResult.ERROR_DEVICE_USING);
        }
        if(devicePCPDao.countOne(new DevicePCPInfo(authRentUserInfo.getMac())) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_CHECK_IN);
        }
        if(authRegisterUserDao.countByMac(new AuthRegisterUserInfo(authRentUserInfo.getMac())) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne("M2");
        commissionCategoryInfo.setPrice();
        commissionCategoryInfo.setFirstPay();
        authRentUserInfo.setMac(authRentUserInfo.getMac().toUpperCase());
        authRentUserInfo.setDistributor(AuthRentUserInfo.DISTRIBUTOR_LDM);
        authRentUserInfo.setCategory(commissionCategoryInfo.getCategory());
        authRentUserInfo.setClientKey(MD5Util.create16(authRentUserInfo.getMac()));
        String activateTime = TimeUtil.getStrTime();
        authRentUserInfo.setActivateTime(activateTime);
        authRentUserInfo.setExpiresTime(TimeUtil.getExpiresTime(activateTime,
                commissionCategoryInfo.getExpires() + commissionCategoryInfo.getBonus()));
        authRentUserInfo.setCardNumber("ldm");
        authRentUserInfo.setExpirationDate("ldm");
        authRentUserInfo.setSecurityKey("ldm");
        authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
        authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
        authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
        authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
        authRentUserInfo.setLdeCommission(commissionCategoryInfo.getLdeCommission());
        authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
        authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
        authRentUserInfo.setSvcCharge(commissionCategoryInfo.getSvcCharge());
        authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_CASH);
        authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_ACTIVATE);
        authRentUserDao.insertOne(authRentUserInfo);
        return ResultMaster.success(authRentUserInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createPayCustomer(HttpServletRequest request, AuthRentUserInfo authRentUserInfo, int paymentMethod){
        if (authRentUserDao.countOneByMac(authRentUserInfo) == 1) {
            if (!AuthRentUserInfo.STATUS_CANCELED.equals(authRentUserDao.selectStatusByMac(authRentUserInfo))) {
                throw new XException(EnumResult.ERROR_DEVICE_USING);
            }
        }
        if(devicePCPDao.countOne(new DevicePCPInfo(authRentUserInfo.getMac())) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_CHECK_IN);
        }
        if(authRegisterUserDao.countByMac(new AuthRegisterUserInfo(authRentUserInfo.getMac())) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authRentUserInfo.getCategory());
        commissionCategoryInfo.setPrice();
        commissionCategoryInfo.setFirstPay();
        authRentUserInfo.setMac(authRentUserInfo.getMac().toUpperCase());
        authRentUserInfo.setDistributor(AuthRentUserInfo.DISTRIBUTOR_LDM);
        authRentUserInfo.setClientKey(MD5Util.create16(authRentUserInfo.getMac()));
        String activateTime = TimeUtil.getStrTime();
        authRentUserInfo.setActivateTime(activateTime);
        authRentUserInfo.setExpiresTime(TimeUtil.getExpiresTime(activateTime,
                commissionCategoryInfo.getExpires() + commissionCategoryInfo.getBonus()));
        authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
        authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
        authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
        authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
        authRentUserInfo.setLdeCommission(commissionCategoryInfo.getLdeCommission());
        authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
        authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
        authRentUserInfo.setSvcCharge(commissionCategoryInfo.getSvcCharge());
        if (paymentMethod == PAYMENT_METHOD_CASH) {
            authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_CASH);
            authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
            authRentUserDao.insertOne(authRentUserInfo);
        } else if (paymentMethod == PAYMENT_METHOD_CREDIT_CARD) {
            authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_CREDIT_CARD);
            authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_ACTIVATE);
            authRentUserDao.insertOne(authRentUserInfo);
            AuthorizeTransactionInfo authorizeTransactionInfo = AuthorizeTransactionInfo
                    .createFromAuthRentUser(authRentUserInfo, authRentUserInfo.getFirstPay() +
                            authRentUserInfo.getFirstPay() * AuthorizeTransactionInfo.TAX);
            AuthorizeTransactionInfo charge = null;
            try {
                charge = new AuthorizeTransaction().charge(authorizeTransactionInfo, request, authRentUserInfo.getClientKey());
            } catch (Exception e) {
                throw new XException(EnumResult.ERROR_TRANSACTION_FAILURE);
            }
            if (charge == null) {
                throw new XException(EnumResult.ERROR_TRANSACTION_FAILURE);
            }
            authorizeTransactionRentalDao.insertOne(AuthorizeTransactionRentalInfo
                    .contractedFromAuthRentInfo(authRentUserInfo, commissionCategoryInfo, charge));
            RentalInvoiceUtil.setPath(PathUtil.getRealPath(request) + "invoice/");
            List<InvoiceInfo> invoiceInfoList = InvoiceInfoMaker.rentalContracted(commissionCategoryInfo);
            String invoicePath = RentalInvoiceUtil.createInvoice(authRentUserInfo,
                    charge.getTransactionId(), invoiceInfoList);
            RentalInvoiceUtil.copyInvoice(invoicePath);
            logger.debug("invoicePath: {}", invoicePath);
            EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LDE);
            emailMaster.setInvoiceContent(authRentUserInfo.getFirstName());
            emailMaster.addAttachment(invoicePath);
            emailMaster.sendMessage(authRentUserInfo.getEmail());
            devicePCPDao.updateDeviceToRented(authRentUserInfo.getMac());
            return ResultMaster.success(authRentUserInfo.getClientKey());
        }else if (paymentMethod == PAYMENT_METHOD_PAYPAL) {
            authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_PAYPAL);
            authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
            authRentUserDao.insertOne(authRentUserInfo);
        }else{
            throw new XException(ResultMaster.error(5001, "payment method error"));
        }
        return ResultMaster.success(authRentUserInfo.getClientKey());
    }

    public List<AuthorizeTransactionRentalInfo> getTransactions(){
        List<AuthorizeTransactionRentalInfo> authorizeTransactionRentalInfoList = authorizeTransactionRentalDao.selectAllLDM();
        return authorizeTransactionRentalInfoList;
    }




    public List<AuthRentUserInfo> getMonthlyActivateUser(int year, int month){
        return logPcpUserMonthlyMexicoDao.selectByMonth(YearOrMonthInfo.createNextMonthly(year, month, AuthRentUserInfo.DISTRIBUTOR_LDM));
    }

    public List<SalesVolumeInDayOfMonthInfo> countSaleVolumeEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDM);
        return authRentUserDao.countAllSalesVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, AuthRentUserInfo.DISTRIBUTOR_LDM);
        return authorizeTransactionRentalDao.selectSaleAmountEveryMonthInYear(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDM);
        return authorizeTransactionRentalDao.selectSaleAmountEveryDayInMonth(yearOrMonthInfo);
    }

    private AuthMexicoInfo getMexicoInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authMexicoDao.selectOneByUsername(username);
    }

}
