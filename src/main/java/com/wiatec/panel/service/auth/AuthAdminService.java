package com.wiatec.panel.service.auth;

import com.wiatec.panel.authorize.AuthorizePayInfo;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
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
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
public class AuthAdminService {

    private Logger logger = LoggerFactory.getLogger(AuthAdminService.class);

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

    public String home(Model model){
        return "admin/home";
    }

    public String sales(Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
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
            throw new XException(EnumResult.ERROR_SERVER_SQL);
        }
    }

    @Transactional
    public ResultInfo updateSalesPassword(AuthSalesInfo authSalesInfo){
        try {
            authSalesDao.updatePassword(authSalesInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error(e.getMessage());
            throw new XException(EnumResult.ERROR_SERVER_SQL);
        }
    }

    public String users(Model model, int salesId){
        List<AuthRentUserInfo> authRentUserInfoList;
        if(salesId > 0){
            authRentUserInfoList = authRentUserDao.selectBySalesId(salesId);
        }else {
            authRentUserInfoList = authRentUserDao.selectAll();
        }
        Map<String, HttpSession> sessionMap = SessionListener.sessionMap;
        for(AuthRentUserInfo authRentUserInfo: authRentUserInfoList){
            if(sessionMap.containsKey(authRentUserInfo.getClientKey())){
                authRentUserInfo.setOnline(true);
            }
        }
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
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
            return ResultMaster.error(EnumResult.ERROR_SERVER_SQL);
        }

    }

    @Transactional
    public ResultInfo activateUser(String key){
        authRentUserDao.updateStatusToActivate(key);
        return ResultMaster.success();
    }

    public String commission(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        List<AuthorizePayInfo> authorizePayInfoList = authorizeTransactionDao.selectAll();
        model.addAttribute("authorizePayInfoList", authorizePayInfoList);
        return "admin/commission";
    }

    /////////////////////////////////////////////////// chart //////////////////////////////////////////////////////////
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

}
