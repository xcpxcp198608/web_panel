package com.wiatec.panel.service.auth;

import com.wiatec.panel.aop.Session;
import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.oxm.pojo.chart.SalesDaysCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesMonthCommissionInfo;
import com.wiatec.panel.paypal.PayInfo;
import com.wiatec.panel.paypal.PayOrderInfo;
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
    @Resource
    private CommissionCategoryDao commissionCategoryDao;
    @Resource
    private PayOrderDao payOrderDao;

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
    public ResultInfo<String> createUser(HttpServletRequest request, AuthRentUserInfo authRentUserInfo){
        ResultInfo<String> resultInfo = new ResultInfo<>();
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
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authRentUserInfo.getCategory());
            commissionCategoryInfo.setPrice();
            //create pay order info in table pay_order
            PayOrderInfo payOrderInfo = new PayOrderInfo();
            payOrderInfo.setInvoice("s"+System.currentTimeMillis());
            payOrderInfo.setCategory(authRentUserInfo.getCategory());
            payOrderInfo.setPrice(commissionCategoryInfo.getPrice());
            payOrderInfo.setCurrency("USD");
            payOrderInfo.setSalesId(authRentUserInfo.getSalesId());
            payOrderInfo.setClientKey(authRentUserInfo.getClientKey());
            payOrderInfo.setDescription("rent");
            payOrderDao.insertOne(payOrderInfo);
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("create successfully");
            resultInfo.setData(authRentUserInfo.getClientKey());
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("server error, try again later ");
            return resultInfo;
        }
    }

    @Transactional
    public String activate(HttpServletRequest request, Model model, String clientKey){
        PayOrderInfo payOrderInfo = payOrderDao.selectOneByClientKey(clientKey);
        PayInfo payInfo = new PayInfo();
        payInfo.setInvoice(payOrderInfo.getInvoice());
        payInfo.setItemName(payOrderInfo.getCategory());
        payInfo.setItemNumber(payOrderInfo.getCategory());
        payInfo.setAmount(payOrderInfo.getPrice());
        payInfo.setTax(0f);
        payInfo.setCurrency(payOrderInfo.getCurrency());
        model.addAttribute("payInfo", payInfo);
        return "sales/payment";
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
