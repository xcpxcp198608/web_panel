package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.AuthUserDao;
import com.wiatec.panel.oxm.pojo.AuthUserInfo;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.TokenUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class AuthUserService {

    @Resource
    private AuthUserDao authUserDao;

    @Transactional
    public ResultInfo register(HttpServletRequest request, AuthUserInfo authUserInfo){
        if(authUserDao.countByMac(authUserInfo) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        if(authUserDao.countByUsername(authUserInfo) == 1){
            throw new XException(EnumResult.ERROR_USERNAME_EXISTS);
        }
        if(authUserDao.countByEmail(authUserInfo) == 1){
            throw new XException(EnumResult.ERROR_EMAIL_EXISTS);
        }
        String token = TokenUtil.create32(authUserInfo.getUsername(), authUserInfo.getEmail());
        authUserInfo.setToken(token);
        authUserDao.saveOneUser(authUserInfo);
        EmailMaster emailMaster = new EmailMaster();
        String url = request.getRequestURL().toString();
        String path = url.substring(0, url.lastIndexOf("/"));
        emailMaster.setEmailContent(path, authUserInfo.getUsername(), token);
        emailMaster.send(authUserInfo.getEmail());
        return ResultMaster.success();
    }

    @Transactional
    public String activate(String token){
        AuthUserInfo authUserInfo = new AuthUserInfo();
        authUserInfo.setToken(token);
        if(authUserDao.countByToken(authUserInfo) != 1){
            return "token not exists";
        }
        authUserDao.updateEmailStatus(authUserInfo);
        return "activation successfully";
    }

    public ResultInfo login(HttpServletRequest request, AuthUserInfo authUserInfo){
        if(authUserDao.countByUsername(authUserInfo) != 1){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if(authUserDao.countByMac(authUserInfo) != 1){
            throw new XException(EnumResult.ERROR_DEVICE_NO_REGISTER);
        }
        if(authUserDao.countByUsernameAndPassword(authUserInfo) != 1){
            throw new XException(EnumResult.ERROR_USERNAME_PASSWORD_NO_MATCH);
        }
        return ResultMaster.success();
    }

    @Transactional
    public ResultInfo validate(HttpServletRequest request, AuthUserInfo authUserInfo){

        return ResultMaster.success();
    }

}
