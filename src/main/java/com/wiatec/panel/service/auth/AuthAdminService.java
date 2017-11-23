package com.wiatec.panel.service.auth;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthOrderDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
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
            return ResultMaster.success(authSalesInfo);
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
        List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectAll();
        model.addAttribute("authOrderInfoList", authOrderInfoList);
        return "admin/commission";
    }

    /////////////////////////////////////////////////// chart //////////////////////////////////////////////////////////
    public List<SalesDayVolumeInMonthInfo> countSaleVolumeEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authOrderDao.countSaleVolumeEveryDayInMonth(yearOrMonthInfo);
    }

    public List<TopVolumeInfo> getTopVolume(int top){
        return authOrderDao.selectTopVolume(top);
    }

    public List<TopAmountInfo> getTopAmount(int top){
        return authOrderDao.selectTopAmount(top);
    }

    public List<AllSalesMonthCommissionInfo> getAllSalesCommissionByMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authOrderDao.selectAllSalesCommissionByMonth(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        return authOrderDao.selectSaleAmountEveryMonthInYear(yearOrMonthInfo);
    }

    public List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(int year, int month){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year, month);
        return authOrderDao.selectSaleAmountEveryDayInMonth(yearOrMonthInfo);
    }

}
