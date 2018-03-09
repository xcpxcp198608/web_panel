package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransaction;
import com.wiatec.panel.authorize.AuthorizeTransactionInfo;
import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.common.utils.*;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceInfoMaker;
import com.wiatec.panel.invoice.RentalInvoiceUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 */
@Service
public class AuthSalesService {

    private Logger logger = LoggerFactory.getLogger(AuthSalesService.class);
    private static final int PAYMENT_METHOD_CASH = 0;
    private static final int PAYMENT_METHOD_CREDIT_CARD = 1;
    private static final int PAYMENT_METHOD_PAYPAL = 2;

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthorizeTransactionRentalDao authorizeTransactionRentalDao;
    @Resource
    private DevicePCPDao devicePCPDao;

    public String home(HttpServletRequest request, Model model){
        int salesId = getSalesInfo(request).getId();
        int totalVolume = authRentUserDao.countTotalVolumeBySalesId(salesId);
        float totalCommission = authorizeTransactionRentalDao.countTotalCommissionBySalesId(salesId);
        model.addAttribute("totalVolume", totalVolume);
        model.addAttribute("totalCommission", totalCommission);
        return "sales/home";
    }

    public String users(HttpServletRequest request, Model model){
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectBySalesId(getSalesInfo(request).getId());
        Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if(sessionMap.containsKey(authRentUserInfo.getClientKey())){
                authRentUserInfo.setOnline(true);
            }
        }
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "sales/customers";
    }

    /**
     * select commissionCategoryInfo list and show create user jsp page
     * @param model  view model
     * @return  create_user jsp page
     */
    public String createUsers(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao
                .selectAllByDistributor(AuthRentUserInfo.DISTRIBUTOR_LDE);
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setFirstPay();
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "sales/create_user";
    }

    /**
     * select rental user all information by client key
     * @param key  client key
     * @return  AuthRentUserInfo
     */
    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    /**
     * create rental user
     * @param request  HttpServletRequest
     * @param authRentUserInfo
     *        required: commission category, mac, email, first name, last name,
     *        phone, post code, post address,
     *        if pay method is credit card, must provide: credit card number,
     *        expiration date, security key
     * @param paymentMethod   pay method: option: cash, credit card, paypal
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo createUser(HttpServletRequest request, AuthRentUserInfo authRentUserInfo, int paymentMethod){
        AuthSalesInfo authSalesInfo = getSalesInfo(request);
        if (authRentUserDao.countOneByMac(authRentUserInfo) == 1) {
            if (!AuthRentUserInfo.STATUS_CANCELED.equals(authRentUserDao.selectStatusByMac(authRentUserInfo))) {
                throw new XException(EnumResult.ERROR_DEVICE_USING);
            }
        }
        if(devicePCPDao.countOne(new DevicePCPInfo(authRentUserInfo.getMac())) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_CHECK_IN);
        }
        if(devicePCPDao.selectSalesIdByMac(authRentUserInfo.getMac()) != authSalesInfo.getId()){
            throw new XException(6000, "the device belongs to other sales");
        }
        if(authRegisterUserDao.countByMac(new AuthRegisterUserInfo(authRentUserInfo.getMac())) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authRentUserInfo.getCategory());
        commissionCategoryInfo.setPrice();
        commissionCategoryInfo.setFirstPay();
        authRentUserInfo.setMac(authRentUserInfo.getMac().toUpperCase());
        authRentUserInfo.setSalesId(authSalesInfo.getId());
        authRentUserInfo.setSalesName(authSalesInfo.getUsername());
        authRentUserInfo.setDealerName(authSalesInfo.getDealerName());
        authRentUserInfo.setDealerId(authSalesInfo.getDealerId());
        authRentUserInfo.setDistributor(AuthRentUserInfo.DISTRIBUTOR_LDE);
        authRentUserInfo.setClientKey(TokenUtil.create(authRentUserInfo.getMac(), System.currentTimeMillis() + ""));
        String activateTime = TimeUtil.getStrTime();
        authRentUserInfo.setActivateTime(activateTime);
        authRentUserInfo.setExpiresTime(TimeUtil.getExpiresTime(activateTime,
                commissionCategoryInfo.getExpires() + commissionCategoryInfo.getBonus()));
        authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
        authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
        authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
        authRentUserInfo.setLdeCommission(commissionCategoryInfo.getLdeCommission());
        authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
        authRentUserInfo.setSvcCharge(commissionCategoryInfo.getSvcCharge());
        if(authSalesInfo.isGold()) {
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission() + 1);
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission() -1);
        }else{
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
        }
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
        int salesStoreCount = devicePCPDao.countNoRentedBySalesId(authSalesInfo.getId());
        if(salesStoreCount <= 0){
            authSalesDao.updateNoGoldById(authSalesInfo.getId());
        }
        int sdcnCount = devicePCPDao.countSDCNBySalesId(authSalesInfo.getId());
        if(sdcnCount >= AuthSalesInfo.SDCN_NOTICE_COUNT){
            authSalesDao.updateSDCNById(authSalesInfo.getId());
        }
        return ResultMaster.success(authRentUserInfo.getClientKey());
    }



    ////////////////////////////////////////////////////////// chart ///////////////////////////////////////////////////

    public List<SalesVolumeInDayOfMonthInfo> selectSalesVolumeEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        return authRentUserDao.countSalesVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<SalesCommissionOfDaysInfo> selectSalesCommissionEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        return authorizeTransactionRentalDao.getCommissionOfDayBySales(yearOrMonthInfo);
    }

    public List<SalesCommissionOfDaysInfo> selectSalesActivationCommissionEveryDayInMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        return authorizeTransactionRentalDao.getActivationCommissionOfDayBySales(yearOrMonthInfo);
    }

    public List<SalesCommissionOfMonthInfo> selectSalesCommissionEveryMonthInYear(HttpServletRequest request, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        return authorizeTransactionRentalDao.getCommissionOfMonthBySales(yearOrMonthInfo);
    }



    public ResultInfo getCommissionByYear(HttpServletRequest request, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        try {
            List<SalesCommissionOfMonthInfo> salesCommissionOfMonthInfoList = authorizeTransactionRentalDao.getCommissionOfMonthBySales(yearOrMonthInfo);
            return ResultMaster.success(salesCommissionOfMonthInfoList);
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    public ResultInfo getCommissionByMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month, AuthRentUserInfo.DISTRIBUTOR_LDE);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        try {
            List<SalesCommissionOfDaysInfo> salesCommissionOfDaysInfoList = authorizeTransactionRentalDao.getCommissionOfDayBySales(yearOrMonthInfo);
            return ResultMaster.success(salesCommissionOfDaysInfoList);
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    private AuthSalesInfo getSalesInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authSalesDao.selectOneByUsername(new AuthSalesInfo(username));
    }
}
