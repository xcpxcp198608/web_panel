package com.wiatec.panel.service.auth;

import com.wiatec.panel.entity.ResultInfo;
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

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthOrderDao authOrderDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    /////////////////////////////////////////////////// home ///////////////////////////////////////////////////////////
    public String home(HttpServletRequest request, Model model){
        return "admin/home";
    }

    /////////////////////////////////////////////////// sales //////////////////////////////////////////////////////////
    public String sales(HttpServletRequest request, Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "admin/sales";
    }

    @Transactional
    public ResultInfo<AuthSalesInfo> createSales(HttpServletRequest request, AuthSalesInfo authSalesInfo){
        ResultInfo<AuthSalesInfo> resultInfo = new ResultInfo<>();
        if(authSalesDao.countUsername(authSalesInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("username exists");
            return resultInfo;
        }
        if(authSalesDao.countSSN(authSalesInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("SSN exists");
            return resultInfo;
        }
        if(authSalesDao.countEmail(authSalesInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("email exists");
            return resultInfo;
        }
        try {
            authSalesDao.insertOne(authSalesInfo);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("create successfully");
            resultInfo.setData(authSalesDao.selectOne(authSalesInfo));
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo updateSalesPassword(HttpServletRequest request, AuthSalesInfo authSalesInfo){
        ResultInfo resultInfo = new ResultInfo<>();
        try {
            authSalesDao.updatePassword(authSalesInfo);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("update successfully");
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later ");
            return resultInfo;
        }
    }

    /////////////////////////////////////////////////// users //////////////////////////////////////////////////////////
    public String users(HttpServletRequest request, Model model, int salesId){
        List<AuthRentUserInfo> authRentUserInfoList = null;
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

    public AuthRentUserInfo getUserByKey(HttpServletRequest request, String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    ///////////////////////////////////////////////// commission ///////////////////////////////////////////////////////
    public String commission(HttpServletRequest request, Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectAll();
        model.addAttribute("authOrderInfoList", authOrderInfoList);
        return "admin/commission";
    }

    /////////////////////////////////////////////////// chart ///////////////////////////////////////////////////////////
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
