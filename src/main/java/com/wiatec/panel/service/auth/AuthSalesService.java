package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransaction;
import com.wiatec.panel.authorize.AuthorizeTransactionInfo;
import com.wiatec.panel.common.utils.*;
import com.wiatec.panel.invoice.InvoiceInfo;
import com.wiatec.panel.invoice.InvoiceUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthSalesService {

    private Logger logger = LoggerFactory.getLogger(AuthSalesService.class);

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;

    public String home(HttpServletRequest request, Model model){
        model.addAttribute("authorizeTransactionInfoList",
                authorizeTransactionDao.selectBySalesId(getSalesInfo(request).getId()));
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
        return "sales/users";
    }

    @Transactional
    public ResultInfo createUser(HttpServletRequest request, AuthRentUserInfo authRentUserInfo, int paymentMethod){
        try {
            if (authRentUserDao.countOneByEmail(authRentUserInfo) == 1) {
                throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
            }
            if (authRentUserDao.countOneByMac(authRentUserInfo) == 1) {
                if (!"canceled".equals(authRentUserDao.selectStatusByMac(authRentUserInfo))) {
                    throw new XException(EnumResult.ERROR_DEVICE_USING);
                }
            }
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authRentUserInfo.getCategory());
            commissionCategoryInfo.setPrice();
            commissionCategoryInfo.setFirstPay();
            authRentUserInfo.setSalesId(getSalesInfo(request).getId());
            authRentUserInfo.setDealerId(getSalesInfo(request).getDealerId());
            authRentUserInfo.setClientKey(TokenUtil.create(authRentUserInfo.getMac(), System.currentTimeMillis() + ""));
            String activateTime = TimeUtil.getStrTime();
            authRentUserInfo.setActivateTime(activateTime);
            authRentUserInfo.setExpiresTime(TimeUtil.getExpiresTime(activateTime,
                    commissionCategoryInfo.getExpires() + commissionCategoryInfo.getBonus()));
            authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
            authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
            authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            if (paymentMethod == 0) { //cash payment
                authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_CASH);
                authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
                authRentUserDao.insertOne(authRentUserInfo);
                return ResultMaster.success(authRentUserInfo.getClientKey());
            } else if (paymentMethod == 1) { //credit card
                authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_CREDIT_CARD);
                authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_ACTIVATE);
                authRentUserDao.insertOne(authRentUserInfo);
                AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
                authorizeTransactionInfo.setAmount(commissionCategoryInfo.getFirstPay());
                authorizeTransactionInfo.setDeposit(commissionCategoryInfo.getDeposit());
                authorizeTransactionInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
                authorizeTransactionInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
                authorizeTransactionInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
                authorizeTransactionInfo.setClientKey(authRentUserInfo.getClientKey());
                authorizeTransactionInfo.setSalesId(authRentUserInfo.getSalesId());
                authorizeTransactionInfo.setDealerId(authRentUserInfo.getDealerId());
                authorizeTransactionInfo.setCategory(authRentUserInfo.getCategory());
                authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
                authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
                authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
                authorizeTransactionInfo.setType(AuthorizeTransactionInfo.TYPE_CONTRACTED);
                AuthorizeTransactionInfo payInfo = AuthorizeTransaction.charge(authorizeTransactionInfo);
                if (payInfo == null) {
                    throw new XException(EnumResult.ERROR_AUTHORIZE);
                }
                authorizeTransactionDao.insertOne(payInfo);
                InvoiceUtil.setPath(PathUtil.getRealPath(request) + "invoice/");
                List<InvoiceInfo> invoiceInfoList;
                switch (commissionCategoryInfo.getCategory()){
                    case "B1":
                        invoiceInfoList = InvoiceInfo.B1Contracted();
                        break;
                    case "P1":
                        invoiceInfoList = InvoiceInfo.P1Contracted();
                        break;
                    case "P2":
                        invoiceInfoList = InvoiceInfo.P2Contracted();
                        break;
                    default:
                        throw new XException(ResultMaster.error(5003, "plan error"));
                }
                String invoicePath = InvoiceUtil.createInvoice(authRentUserInfo.getEmail(),
                        payInfo.getTransactionId(), invoiceInfoList);
                EmailMaster emailMaster = new EmailMaster();
                emailMaster.setInvoiceContent(authRentUserInfo.getFirstName());
                emailMaster.addAttachment(invoicePath);
                emailMaster.sendMessage(authRentUserInfo.getEmail());
                return ResultMaster.success(authRentUserInfo.getClientKey());
            }else if (paymentMethod == 2) { // paypal payment
                authRentUserInfo.setPaymentType(AuthRentUserInfo.PAYMENT_PAYPAL);
                authRentUserInfo.setStatus(AuthRentUserInfo.STATUS_DEACTIVATE);
                authRentUserDao.insertOne(authRentUserInfo);
            }else{
                throw new XException(ResultMaster.error(5001, "payment method error"));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
        return ResultMaster.error(5000, "server error");
    }

    ////////////////////////////////////////////////////////// chart ///////////////////////////////////////////////////
    public ResultInfo getCommissionByYear(HttpServletRequest request, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        try {
            List<SalesCommissionOfMonthInfo> salesCommissionOfMonthInfoList = authorizeTransactionDao.getCommissionOfMonthBySales(yearOrMonthInfo);
            return ResultMaster.success(salesCommissionOfMonthInfoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    public ResultInfo getCommissionByMonth(HttpServletRequest request, int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        yearOrMonthInfo.setSalesId(getSalesInfo(request).getId()+"");
        try {
            List<SalesCommissionOfDaysInfo> salesCommissionOfDaysInfoList = authorizeTransactionDao.getCommissionOfDayBySales(yearOrMonthInfo);
            return ResultMaster.success(salesCommissionOfDaysInfoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    private AuthSalesInfo getSalesInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        return authSalesDao.selectOne(new AuthSalesInfo(username));
    }
}
