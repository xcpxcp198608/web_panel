package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizePayInfo;
import com.wiatec.panel.authorize.CreditCardTransaction;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TokenUtil;
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

@Service
public class AuthSalesService {

    private Logger logger = LoggerFactory.getLogger(AuthSalesService.class);

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthOrderDao authOrderDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;

    public String home(HttpServletRequest request, Model model){
        model.addAttribute("authorizePayInfoList", authorizeTransactionDao.selectBySalesId(getSalesId(request)));
        return "sales/home";
    }

    public String users(HttpServletRequest request, Model model){
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectBySalesId(getSalesId(request));
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
    public ResultInfo createUser(HttpServletRequest request, AuthRentUserInfo authRentUserInfo){
        try {
            if(authRentUserDao.countOneByEmail(authRentUserInfo) == 1){
                throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
            }
            if(authRentUserDao.countOneByMac(authRentUserInfo) == 1){
                if(!"canceled".equals(authRentUserDao.selectStatusByMac(authRentUserInfo))){
                    throw new XException(EnumResult.ERROR_MAC_USING);
                }
            }
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authRentUserInfo.getCategory());
            commissionCategoryInfo.setPrice();
            commissionCategoryInfo.setFirstPay();
            authRentUserInfo.setSalesId(getSalesId(request));
            authRentUserInfo.setClientKey(TokenUtil.create(authRentUserInfo.getMac(), System.currentTimeMillis() + ""));
            String activateTime = TimeUtil.getStrTime();
            authRentUserInfo.setActivateTime(activateTime);
            authRentUserInfo.setExpiresTime(TimeUtil.getExpiresTime(activateTime, commissionCategoryInfo.getExpires()));
            authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
            authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
            authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            authRentUserDao.insertOne(authRentUserInfo);

            AuthorizePayInfo authorizePayInfo = new AuthorizePayInfo();
            authorizePayInfo.setAmount(commissionCategoryInfo.getFirstPay());
            authorizePayInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authorizePayInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
            authorizePayInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
            authorizePayInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            authorizePayInfo.setClientKey(authRentUserInfo.getClientKey());
            authorizePayInfo.setSalesId(authRentUserInfo.getSalesId());
            authorizePayInfo.setCategory(authRentUserInfo.getCategory());
            authorizePayInfo.setCardNumber(authRentUserInfo.getCardNumber());
            authorizePayInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
            authorizePayInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
            authorizePayInfo.setType(AuthorizePayInfo.TYPE_LEASE);
            AuthorizePayInfo payInfo = CreditCardTransaction.pay(authorizePayInfo);
            if(payInfo == null){
                throw new XException(EnumResult.ERROR_AUTHORIZE);
            }
            authorizeTransactionDao.insertOne(payInfo);
            return ResultMaster.success(authRentUserInfo.getClientKey());
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    ////////////////////////////////////////////////////////// chart ///////////////////////////////////////////////////
    public ResultInfo getCommissionByYear(HttpServletRequest request, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        yearOrMonthInfo.setSalesId(getSalesId(request)+"");
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
        yearOrMonthInfo.setSalesId(getSalesId(request)+"");
        try {
            List<SalesCommissionOfDaysInfo> salesCommissionOfDaysInfoList = authorizeTransactionDao.getCommissionOfDayBySales(yearOrMonthInfo);
            return ResultMaster.success(salesCommissionOfDaysInfoList);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(ResultMaster.error(5000, e.getMessage()));
        }
    }

    private int getSalesId(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        AuthSalesInfo authSalesInfo = authSalesDao.selectOne(new AuthSalesInfo(username));
        return authSalesInfo.getId();
    }
}
