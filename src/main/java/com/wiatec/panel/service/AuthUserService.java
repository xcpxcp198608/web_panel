package com.wiatec.panel.service;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthUserDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthUserInfo;
import com.wiatec.panel.xutils.EmailMaster;
import com.wiatec.panel.xutils.TimeUtil;
import com.wiatec.panel.xutils.TokenUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

@Service
public class AuthUserService {

    @Resource
    private AuthUserDao authUserDao;

    @Transactional
    public ResultInfo<AuthUserInfo> register(HttpServletRequest request, AuthUserInfo authUserInfo){
        ResultInfo<AuthUserInfo> resultInfo = new ResultInfo<>();
        if(authUserDao.countByMac(authUserInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("this device has been register");
            return resultInfo;
        }
        if(authUserDao.countByUsername(authUserInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("username is exists");
            return resultInfo;
        }
        if(authUserDao.countByEmail(authUserInfo) == 1){
            resultInfo.setCode(ResultInfo.CODE_INVALID);
            resultInfo.setStatus(ResultInfo.STATUS_INVALID);
            resultInfo.setMessage("email is exists");
            return resultInfo;
        }
        String token = TokenUtil.create32(authUserInfo.getUsername(), authUserInfo.getEmail());
        authUserInfo.setToken(token);
        authUserDao.saveOneUser(authUserInfo);
        EmailMaster emailMaster = new EmailMaster();
        String url = request.getRequestURL().toString();
        String path = url.substring(0, url.lastIndexOf("/"));
        emailMaster.setEmailContent(path, authUserInfo.getUsername(), token);
        emailMaster.send(authUserInfo.getEmail());
        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("register successfully");
        return resultInfo;
    }

    @Transactional
    public String activate(String token){
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setToken(token);
        if(authUserDao.countByToken(authUserInfo) != 1){
            return "token not exists";
        }
        authUserDao.updateEmailStatus(authUserInfo);
        return "activate successfully";
    }

    public ResultInfo<AuthUserInfo> login(HttpServletRequest request, AuthUserInfo authUserInfo){
        ResultInfo<AuthUserInfo> resultInfo = new ResultInfo<>();
        if(authUserDao.countByUsername(authUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("username is not exists");
            return resultInfo;
        }
        if(authUserDao.countByMac(authUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("the device have no register");
            return resultInfo;
        }
        if(authUserDao.countByUsernameAndPassword(authUserInfo) != 1){
            resultInfo.setCode(ResultInfo.CODE_UNAUTHORIZED);
            resultInfo.setStatus(ResultInfo.STATUS_UNAUTHORIZED);
            resultInfo.setMessage("username and password not match");
            return resultInfo;
        }




        return resultInfo;
    }

    @Transactional
    public ResultInfo<AuthUserInfo> validate(HttpServletRequest request, AuthUserInfo authUserInfo){
        ResultInfo<AuthUserInfo> resultInfo = new ResultInfo<>();



        resultInfo.setCode(ResultInfo.CODE_OK);
        resultInfo.setStatus(ResultInfo.STATUS_OK);
        resultInfo.setMessage("validate success");
        return resultInfo;
    }

}
