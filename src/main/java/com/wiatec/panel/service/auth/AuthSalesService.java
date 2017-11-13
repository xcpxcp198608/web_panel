package com.wiatec.panel.service.auth;

import com.wiatec.panel.aop.Session;
import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.dao.AuthOrderDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesDaysCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesMonthCommissionInfo;
import com.wiatec.panel.xutils.TextUtil;
import com.wiatec.panel.xutils.TokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthSalesService {

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthOrderDao authOrderDao;

    /////////////////////////////////////////////////// sales //////////////////////////////////////////////////////////
    public String home(HttpServletRequest request, Model model){
        List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectBySalesId(getSalesId(request));
        model.addAttribute("authOrderInfoList", authOrderInfoList);
        return "sales/home";
    }

    @Transactional
    public ResultInfo<SalesDaysCommissionInfo> getCommissionByMonth(HttpServletRequest request, int year, int month){
        ResultInfo<SalesDaysCommissionInfo> resultInfo = new ResultInfo<>();
        String start = year + "-" + month + "-1";
        String end = "";
        if(month == 12){
            end = year + 1 + "-" + "1" + "-1";
        }else{
            month ++;
            end = year + "-" + month + "-1";
        }
        Map<String, String> map = new HashMap<>();
        map.put("salesId", getSalesId(request)+"");
        map.put("start", start);
        map.put("end", end);
        try {
            List<SalesDaysCommissionInfo> salesDaysCommissionInfoList = authOrderDao.getCommissionByMonth(map);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("create successfully");
            resultInfo.setDataList(salesDaysCommissionInfoList);
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later ");
            return resultInfo;
        }
    }

    @Transactional
    public ResultInfo<SalesMonthCommissionInfo> getCommissionByYear(HttpServletRequest request, int year){
        ResultInfo<SalesMonthCommissionInfo> resultInfo = new ResultInfo<>();
        String start = year + "-01-01";
        year ++;
        String end =  year + "-01-01";
        Map<String, String> map = new HashMap<>();
        map.put("salesId", getSalesId(request)+"");
        map.put("start", start);
        map.put("end", end);
        try {
            List<SalesMonthCommissionInfo> salesMonthCommissionInfoList = authOrderDao.getCommissionByYear(map);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("create successfully");
            resultInfo.setDataList(salesMonthCommissionInfoList);
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later ");
            return resultInfo;
        }
    }




    /////////////////////////////////////////////////// users //////////////////////////////////////////////////////////
    @Transactional
    public String users(HttpServletRequest request, Model model){
        List<AuthRentUserInfo> authRentUserInfoList = authRentUserDao.selectBySalesId(getSalesId(request));
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "sales/users";
    }

    @Transactional
    public ResultInfo<AuthRentUserInfo> createUser(HttpServletRequest request, AuthRentUserInfo authRentUserInfo){
        ResultInfo<AuthRentUserInfo> resultInfo = new ResultInfo<>();
        try {
            if(authRentUserDao.countOneByEmail(authRentUserInfo) == 1){
                resultInfo.setCode(ResultInfo.CODE_INVALID);
                resultInfo.setStatus(ResultInfo.STATUS_INVALID);
                resultInfo.setMessage("email is exists");
                return resultInfo;
            }
            if(authRentUserDao.countOneByMac(authRentUserInfo) == 1){
                if(!"canceled".equals(authRentUserDao.selectStatusByMac(authRentUserInfo))){
                    resultInfo.setCode(ResultInfo.CODE_INVALID);
                    resultInfo.setStatus(ResultInfo.STATUS_INVALID);
                    resultInfo.setMessage("device has been use");
                    return resultInfo;
                }
            }
            authRentUserInfo.setSalesId(getSalesId(request));
            authRentUserInfo.setClientKey(TokenUtil.create(authRentUserInfo.getMac(), System.currentTimeMillis() + ""));
            authRentUserDao.insertOne(authRentUserInfo);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("create successfully");
            resultInfo.setData(authRentUserInfo);
            return resultInfo;
        }catch (Exception e){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later ");
            return resultInfo;
        }
    }


    private int getSalesId(HttpServletRequest request){
        String username = Session.getUserName(request);
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        AuthSalesInfo authSalesInfo = authSalesDao.selectOne(new AuthSalesInfo(username));
        return authSalesInfo.getId();
    }


}
