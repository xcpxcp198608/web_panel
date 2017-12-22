package com.wiatec.panel.service;

import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.TokenUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthRegisterUserService {

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;

    @Transactional
    public ResultInfo register(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo){
        if(authRegisterUserDao.countByMac(authRegisterUserInfo) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        if(authRegisterUserDao.countByUsername(authRegisterUserInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authRegisterUserDao.countByEmail(authRegisterUserInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        String token = TokenUtil.create32(authRegisterUserInfo.getUsername(), authRegisterUserInfo.getEmail());
        authRegisterUserInfo.setToken(token);
        authRegisterUserDao.saveOneUser(authRegisterUserInfo);
        EmailMaster emailMaster = new EmailMaster();
        String url = request.getRequestURL().toString();
        String path = url.substring(0, url.lastIndexOf("/"));
        emailMaster.setEmailContent(path, authRegisterUserInfo.getUsername(), token);
        emailMaster.send(authRegisterUserInfo.getEmail());
        return ResultMaster.success();
    }

    @Transactional
    public ResultInfo activate(String token){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByToken(token);
        if(authRegisterUserInfo == null){
            throw new XException(EnumResult.ERROR_TOKEN_NOT_EXISTS);
        }
        String newToken = TokenUtil.create32(token, System.currentTimeMillis()+"");
        authRegisterUserInfo.setToken(newToken);
        authRegisterUserDao.updateEmailStatus(authRegisterUserInfo);
        return ResultMaster.success();
    }

    public ResultInfo login(AuthRegisterUserInfo authRegisterUserInfo){
        if(authRegisterUserDao.countByUsername(authRegisterUserInfo) != 1){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if(authRegisterUserDao.countByMac(authRegisterUserInfo) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_REGISTER);
        }
        if(authRegisterUserDao.countByUsernameAndPassword(authRegisterUserInfo) != 1){
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        return ResultMaster.success();
    }

    @Transactional
    public ResultInfo validate(HttpSession session, AuthRegisterUserInfo userInfo){
        try {
            AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsernameAndMac(userInfo);
            if (authRegisterUserInfo == null) {
                throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
            }
            session.setAttribute(SessionListener.KEY_USER_NAME, authRegisterUserInfo.getUsername());
            authRegisterUserDao.updateLocation(userInfo);
            if(TextUtil.isEmpty(authRegisterUserInfo.getExpiresTime())){
                String e = TimeUtil.getExpiresTimeByDay(authRegisterUserInfo.getActiveTime(), 7);
                if(!TimeUtil.isOutExpires(e)){
                    authRegisterUserInfo.setExperience(true);
                }
            }else {
                if(TimeUtil.isOutExpires(authRegisterUserInfo.getExpiresTime())) {
                    authRegisterUserInfo.setLevel(1);
                    authRegisterUserInfo.setExpiresTime("");
                    authRegisterUserDao.updateLevel(authRegisterUserInfo);
                }
            }
            return ResultMaster.success(authRegisterUserInfo);
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    public ResultInfo goReset(HttpServletRequest request, AuthRegisterUserInfo userInfo){
        try{
            AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsernameAndEmail(userInfo);
            if(authRegisterUserInfo == null){
                throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
            }
            EmailMaster emailMaster = new EmailMaster();
            String url = request.getRequestURL().toString();
            String path = url.substring(0, url.lastIndexOf("/"));
            emailMaster.setResetPasswordContent(path, authRegisterUserInfo.getUsername(), authRegisterUserInfo.getToken());
            emailMaster.send(authRegisterUserInfo.getEmail());
            return ResultMaster.success();
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional
    public String reset(String token, Model model){
        try{
            AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByToken(token);
            if(authRegisterUserInfo == null){
                throw new XException(EnumResult.ERROR_TOKEN_NOT_EXISTS);
            }
            String newToken = TokenUtil.create32(token, System.currentTimeMillis()+"");
            authRegisterUserInfo.setToken(newToken);
            authRegisterUserDao.updateToken(authRegisterUserInfo);
            model.addAttribute("authRegisterUserInfo", authRegisterUserInfo);
            return "/users/reset";
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional
    public ResultInfo updatePassword(AuthRegisterUserInfo userInfo){
        try{
            authRegisterUserDao.updatePassword(userInfo);
            return ResultMaster.success();
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }




    //web
    public String home(Model model){

        return "users/home";
    }

}
