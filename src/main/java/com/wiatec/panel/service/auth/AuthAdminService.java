package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizeTransaction;
import com.wiatec.panel.authorize.AuthorizeTransactionInfo;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
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
public class AuthAdminService {

    private Logger logger = LoggerFactory.getLogger(AuthAdminService.class);

    @Resource
    private AuthAdminDao authAdminDao;
    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;

    public String home(Model model){
        return "admin/home";
    }

    public String dealer(Model model){
        List<AuthDealerInfo> authDealerInfoList = authDealerDao.selectAll();
        model.addAttribute("authDealerInfoList", authDealerInfoList);
        return "admin/dealer";
    }

    @Transactional
    public ResultInfo createDealer(HttpServletRequest request, AuthDealerInfo authDealerInfo) throws Exception{
        if(authDealerDao.countUsername(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authDealerDao.countSSN(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_SSN_EXISTS);
        }
        if(authDealerDao.countEmail(authDealerInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        try {
            authDealerInfo.setLeaderId(getAdminInfo(request).getId());
            authDealerDao.insertOne(authDealerInfo);
            return ResultMaster.success(authDealerDao.selectOne(authDealerInfo));
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional
    public ResultInfo updateDealerPassword(AuthDealerInfo authDealerInfo){
        try {
            authDealerDao.updatePassword(authDealerInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error(e.getMessage()+"");
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String sales(Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        List<AuthDealerInfo> authDealerInfoList = authDealerDao.selectAll();
        model.addAttribute("authDealerInfoList", authDealerInfoList);
        return "admin/sales";
    }

    @Transactional
    public ResultInfo createSales(AuthSalesInfo authSalesInfo) throws Exception{
        if(authSalesDao.countUsername(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authSalesDao.countSSN(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_SSN_EXISTS);
        }
        if(authSalesDao.countEmail(authSalesInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        try {
            authSalesDao.insertOne(authSalesInfo);
            return ResultMaster.success(authSalesDao.selectOne(authSalesInfo));
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional
    public ResultInfo updateSalesPassword(AuthSalesInfo authSalesInfo){
        try {
            authSalesDao.updatePassword(authSalesInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String users(Model model, int key, int value){
        List<AuthRentUserInfo> authRentUserInfoList;
        switch (key){
            case 0:
                authRentUserInfoList = authRentUserDao.selectAll();
                break;
            case 1:
                authRentUserInfoList = authRentUserDao.selectByDealerId(value);
                break;
            case 2:
                authRentUserInfoList = authRentUserDao.selectBySalesId(value);
                break;
            default:
                throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if(sessionMap.containsKey(authRentUserInfo.getClientKey())){
                authRentUserInfo.setOnline(true);
            }
        }
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "admin/users";
    }

    public AuthRentUserInfo getUserByKey(String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    @Transactional
    public ResultInfo updateUserStatus(String status, String key){
        try {
            AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
            authRentUserInfo.setStatus(status);
            authRentUserInfo.setClientKey(key);
            authRentUserDao.updateUserStatus(authRentUserInfo);
            return ResultMaster.success();
        }catch (Exception e){
            return ResultMaster.error(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional
    public ResultInfo updateUserCategory(String key, String category){
        try {
            AuthRentUserInfo authRentUserInfo = authRentUserDao.selectByClientKey(key);
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(category);
            commissionCategoryInfo.setFirstPay();
            authRentUserInfo.setCategory(category);
            authRentUserInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authRentUserInfo.setFirstPay(commissionCategoryInfo.getFirstPay());
            authRentUserInfo.setMonthPay(commissionCategoryInfo.getMonthPay());
            authRentUserInfo.setLdCommission(commissionCategoryInfo.getLdCommission());
            authRentUserInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission());
            authRentUserInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission());
            AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
            authorizeTransactionInfo.setSalesId(authRentUserInfo.getSalesId());
            authorizeTransactionInfo.setDealerId(authRentUserInfo.getDealerId());
            authorizeTransactionInfo.setSalesName(authRentUserInfo.getSalesName());
            authorizeTransactionInfo.setCategory(authRentUserInfo.getCategory());
            authorizeTransactionInfo.setClientKey(authRentUserInfo.getClientKey());
            authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
            authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
            authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
            authorizeTransactionInfo.setAmount(authRentUserInfo.getFirstPay());
            authorizeTransactionInfo.setDeposit(authRentUserInfo.getDeposit());
            authorizeTransactionInfo.setLdCommission(authRentUserInfo.getLdCommission());
            authorizeTransactionInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
            authorizeTransactionInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
            authorizeTransactionInfo.setType(AuthorizeTransactionInfo.TYPE_CONTRACTED);
            AuthorizeTransactionInfo charge = AuthorizeTransaction.charge(authorizeTransactionInfo);
            if(charge == null){
                throw new XException(EnumResult.ERROR_AUTHORIZE);
            }
            authorizeTransactionDao.insertOne(charge);
            authRentUserDao.updateUserCategory(authRentUserInfo);
            return ResultMaster.success(authRentUserInfo);
        }catch (Exception e){
            logger.debug(e.getLocalizedMessage());
            return ResultMaster.error(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public String commission(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        List<AuthorizeTransactionInfo> authorizeTransactionInfoList = authorizeTransactionDao.selectAll();
        model.addAttribute("authorizeTransactionInfoList", authorizeTransactionInfoList);
        return "admin/commission";
    }

    /////////////////////////////////////////////////// chart //////////////////////////////////////////////////////////
    public List<VolumeDistributionInfo> getDistributionData(){
        return authRentUserDao.getDistributionData();
    }


    public List<SalesVolumeInDayOfMonthInfo> countSaleVolumeEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authRentUserDao.countSalesVolumeByDayOfMonth(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(int top){
        return authRentUserDao.selectTopVolume(top);
    }

    public List<TopAmountInfo> getTopAmount(int top){
        return authorizeTransactionDao.selectTopAmount(top);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authorizeTransactionDao.selectAllSalesCommissionByMonth(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        return authorizeTransactionDao.selectSaleAmountEveryMonthInYear(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authorizeTransactionDao.selectSaleAmountEveryDayInMonth(yearOrMonthInfo);
    }

    private AuthAdminInfo getAdminInfo(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        return authAdminDao.selectOne(new AuthAdminInfo(username));
    }

}
