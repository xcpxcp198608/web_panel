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
import com.wiatec.panel.oxm.pojo.chart.DaysInfo;
import com.wiatec.panel.xutils.LoggerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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
    @Transactional
    public String home(HttpServletRequest request, Model model){

        return "admin/home";
    }

    @Transactional(readOnly = true)
    public ResultInfo<DaysInfo> getAllOrders(HttpServletRequest request, int year, int month, int days){
        ResultInfo<DaysInfo> resultInfo = new ResultInfo<>();
        List<DaysInfo> daysInfoList = new ArrayList<>();
        try {
            for(int i = 1 ; i <= days ; i ++){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(year);
                stringBuilder.append("-");
                if(month < 10){
                    stringBuilder.append("0");
                }
                stringBuilder.append(month);
                stringBuilder.append("-");
                if(i < 10){
                    stringBuilder.append("0");
                }
                stringBuilder.append(i);
                List<AuthOrderInfo> authOrderInfoList = authOrderDao.selectByTradingTime(stringBuilder.toString());
                DaysInfo daysInfo = transformOrderInfoToDaysInfo(authOrderInfoList);
                daysInfoList.add(daysInfo);
            }
            resultInfo.setCode(ResultInfo.CODE_OK);
            resultInfo.setStatus(ResultInfo.STATUS_OK);
            resultInfo.setMessage("successfully");
            resultInfo.setDataList(daysInfoList);
            return resultInfo;
        }catch (Exception e){
            e.printStackTrace();
            resultInfo.setCode(ResultInfo.CODE_SERVER_ERROR);
            resultInfo.setStatus(ResultInfo.STATUS_SERVER_ERROR);
            resultInfo.setMessage("server error");
            return resultInfo;
        }
    }

    private DaysInfo transformOrderInfoToDaysInfo(List<AuthOrderInfo> authOrderInfoList){
        DaysInfo daysInfo = new DaysInfo();
        if(authOrderInfoList == null || authOrderInfoList.size() <= 0){
            return daysInfo;
        }
        float amount = 0f;
        int B1 =0;
        int P1 =0;
        int P2 =0;
        for(AuthOrderInfo authOrderInfo: authOrderInfoList){
            amount += authOrderInfo.getPrice();
            if("B1".equals(authOrderInfo.getCategory())){
                B1 ++;
            }
            if("P1".equals(authOrderInfo.getCategory())){
                P1 ++;
            }
            if("P2".equals(authOrderInfo.getCategory())){
                P2 ++;
            }
        }
        daysInfo.setVolume(authOrderInfoList.size());
        daysInfo.setAmount(amount);
        daysInfo.setB1(B1);
        daysInfo.setP1(P1);
        daysInfo.setP2(P2);
        return daysInfo;
    }


    /////////////////////////////////////////////////// sales //////////////////////////////////////////////////////////
    @Transactional
    public String sales(HttpServletRequest request, Model model){
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "admin/sales";
    }

    /////////////////////////////////////////////////// users //////////////////////////////////////////////////////////
    @Transactional
    public String users(HttpServletRequest request, Model model, int salesId){
        List<AuthRentUserInfo> authRentUserInfoList = null;
        if(salesId > 0){
            authRentUserInfoList = authRentUserDao.selectOneBySalesId(salesId);
        }else {
            authRentUserInfoList = authRentUserDao.selectAll();
        }
        LoggerUtil.d(authRentUserInfoList);
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "admin/users";
    }

    @Transactional
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
    public ResultInfo<AuthOrderInfo> getCommissionOrders(HttpServletRequest request, int year, int month){
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

    @Transactional
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


    ////////////////////////////////////////////////// sign out ////////////////////////////////////////////////////////
    @Transactional
    public String signOut(HttpServletRequest request){
        String sessionId = request.getCookies()[0].getValue();
        HttpSession session = SessionListener.idSessionMap.get(sessionId);
        String username = (String) session.getAttribute("username");
        SessionListener.idSessionMap.remove(sessionId);
        SessionListener.userSessionMap.remove(username);
        session.invalidate();
        return "redirect:/";
    }
}
