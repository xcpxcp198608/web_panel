package com.wiatec.panel.service;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.common.utils.TokenUtil;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class AuthManagerService {

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    public String home(Model model){
        return "users/home";
    }

    public String users(HttpSession session, Model model){
        String username = (String) session.getAttribute("username");
        if(username == null){
            throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        List<AuthRegisterUserInfo> authRegisterUserInfoList = null;
        if(username.equals("wiatec")) {
            authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        }else{
            authRegisterUserInfoList = authRegisterUserDao.selectAll(100);
        }
        model.addAttribute("authRegisterUserInfoList", authRegisterUserInfoList);
        return "users/users";
    }

    public AuthRegisterUserInfo userDetails(int id){
        return authRegisterUserDao.selectOneById(id);
    }

    @Transactional
    public ResultInfo activate(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setToken(TokenUtil.create(id+"", System.currentTimeMillis()+""));
        authRegisterUserDao.updateEmailStatusById(authRegisterUserInfo);
        return ResultMaster.success();
    }

    @Transactional
    public ResultInfo delete(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserDao.deleteOneById(authRegisterUserInfo);
        return ResultMaster.success();
    }

    @Transactional
    public ResultInfo updateLevel(int id, int level, int days){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setLevel(level);
        String expiresTime = authRegisterUserDao.selectExpiresTimeById(id);
        String newExpiresTime = "";
        if(TextUtil.isEmpty(expiresTime)){
            newExpiresTime = TimeUtil.getExpiresTimeByDay(TimeUtil.getStrTime(), days);
        }else{
            newExpiresTime = TimeUtil.getExpiresTimeByDay(expiresTime, days);
        }
        authRegisterUserInfo.setExpiresTime(newExpiresTime);
        authRegisterUserDao.updateLevelById(authRegisterUserInfo);
        return ResultMaster.success(authRegisterUserInfo);
    }


    public ResultInfo<Integer> getLevelChart(int level, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        yearOrMonthInfo.setSalesId(level+""); //sale id replace level
        return ResultMaster.success(authRegisterUserDao.selectLevelOfYear(yearOrMonthInfo));
    }

}
