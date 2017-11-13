package com.wiatec.panel.service.auth;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.dao.AuthOrderDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.oxm.pojo.chart.TopAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.TopVolumeInfo;
import com.wiatec.panel.xutils.LoggerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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

    /////////////////////////////////////////////////// home //////////////////////////////////////////////////////////
    public String home(HttpServletRequest request, Model model){
        return "admin/home";
    }

    public void selectSaleVolumeEveryMonth(HttpServletRequest request, int year, int month){
        Map<String, String> dateMap = new HashMap<>();
        String start = year + "-" + month;
        String end = "";
        if(month == 12){
            end = year+1 + "-" + 1;
        }else{
            end = year + "-" + month + 1;
        }
        dateMap.put("start", start);
        dateMap.put("end", end);
        authOrderDao.selectSaleVolumeEveryMonth(dateMap);
    }


    /////////////////////////////////////////////////// sales //////////////////////////////////////////////////////////
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public String users(HttpServletRequest request, Model model, int salesId){
        List<AuthRentUserInfo> authRentUserInfoList = null;
        if(salesId > 0){
            authRentUserInfoList = authRentUserDao.selectBySalesId(salesId);
        }else {
            authRentUserInfoList = authRentUserDao.selectAll();
        }
        LoggerUtil.d(authRentUserInfoList);
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "admin/users";
    }

    @Transactional(readOnly = true)
    public AuthRentUserInfo getUserByKey(HttpServletRequest request, String key){
        return authRentUserDao.selectOneByClientKey(key);
    }

    ///////////////////////////////////////////////// commission ///////////////////////////////////////////////////////
    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public ResultInfo<AuthOrderInfo> getOrdersByMonth(HttpServletRequest request, int year, int month){
        ResultInfo<AuthOrderInfo> resultInfo = new ResultInfo<>();
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(year);
            stringBuilder.append("-");
            if(month < 10){
                stringBuilder.append("0");
            }
            stringBuilder.append(month);
            List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectByTradingTime(stringBuilder.toString());
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("successfully");
            resultInfo.setDataList(authOrderInfoList);
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("server error ");
            return resultInfo;
        }
    }

    @Transactional(readOnly = true)
    public ResultInfo<AuthOrderInfo> getOrdersByYear(HttpServletRequest request, String year){
        ResultInfo<AuthOrderInfo> resultInfo = new ResultInfo<>();
        try {
            List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectByTradingTime(year);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("successfully");
            resultInfo.setDataList(authOrderInfoList);
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("server error!");
            return resultInfo;
        }
    }

    @Transactional(readOnly = true)
    public List<TopVolumeInfo> getTopVolume(HttpServletRequest request, int top){
        return authOrderDao.selectTopVolume(top);
    }

    @Transactional(readOnly = true)
    public List<TopAmountInfo> getTopAmount(HttpServletRequest request, int top){
        return authOrderDao.selectTopAmount(top);
    }

}
