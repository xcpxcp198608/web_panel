package com.wiatec.panel.service;

import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthUserLogDao;
import com.wiatec.panel.oxm.dao.DeviceRentDao;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.common.utils.EmailMaster;
import com.wiatec.panel.common.utils.TokenUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthUserLogInfo;
import com.wiatec.panel.oxm.pojo.DeviceRentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Service
public class AuthRegisterUserService {

    private static final Logger logger = LoggerFactory.getLogger(AuthRegisterUserService.class);

    @Resource
    private AuthRegisterUserDao authRegisterUserDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthUserLogDao authUserLogDao;
    @Autowired
    private DeviceRentDao deviceRentDao;

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo register(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo, String language){
        if(TextUtil.isEmpty(authRegisterUserInfo.getMac())){
            throw new XException(ResultMaster.error("device s/n error"));
        }
        if(deviceRentDao.countOne(new DeviceRentInfo(authRegisterUserInfo.getMac())) >= 1){
            throw new XException(1001, "the device only for rental");
        }
        if(authRegisterUserDao.countByMac(authRegisterUserInfo) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_ALREADY_REGISTER);
        }
        if(authRentUserDao.countOneByMac(new AuthRentUserInfo(authRegisterUserInfo.getMac())) == 1){
            throw new XException(EnumResult.ERROR_DEVICE_USING);
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
        EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LD);
        String url = request.getRequestURL().toString();
        String path = url.substring(0, url.lastIndexOf("/"));
        emailMaster.setEmailContent(path, authRegisterUserInfo.getUsername(), token, language);
        emailMaster.sendMessage(authRegisterUserInfo.getEmail());
        return ResultMaster.success(" Please check your email to confirm and activate the account. " +
                "The activation email may take up to 60 minutes to arrive, if you didn't get " +
                "the email, please contact customer service.");
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo activate(String token){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByToken(token);
        if(authRegisterUserInfo == null){
            throw new XException(EnumResult.ERROR_TOKEN_NOT_EXISTS);
        }
        String newToken = TokenUtil.create32(token, System.currentTimeMillis()+"");
        authRegisterUserInfo.setToken(newToken);
        authRegisterUserDao.updateEmailStatus(authRegisterUserInfo);
        return ResultMaster.success("Activation successfully");
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
        AuthRegisterUserInfo authRegisterUserInfo1;
        try {
            authRegisterUserInfo1 = authRegisterUserDao.selectOneByUsernameAndMac(authRegisterUserInfo);
        }catch (Exception e){
            throw new XException(EnumResult.ERROR_USERNAME_MAC_NO_MATCH);
        }
        if(authRegisterUserInfo1 == null){
            throw new XException(EnumResult.ERROR_USERNAME_MAC_NO_MATCH);
        }
        if(authRegisterUserInfo1.getEmailStatus() != 1){
            throw new XException(EnumResult.ERROR_USER_NO_ACTIVATE);
        }
        if(authRegisterUserInfo1.getLevel() <= 0){
            throw new XException(EnumResult.ERROR_DEVICE_LIMITED);
        }
        return ResultMaster.success(authRegisterUserInfo1);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo validate(HttpSession session, AuthRegisterUserInfo userInfo){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsername(userInfo);
        if (authRegisterUserInfo == null) {
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        session.setAttribute(SessionListener.KEY_USER_NAME, authRegisterUserInfo.getUsername());
        authRegisterUserDao.updateLocation(userInfo);
        if(authRegisterUserInfo.getLevel() == 0){
            return ResultMaster.success(authRegisterUserInfo);
        }
        String activateTime = authRegisterUserInfo.getActiveTime();
        if(TextUtil.isEmpty(activateTime)){
            activateTime = "2017-01-01 00:00:00";
        }
        if(authRegisterUserInfo.getLevel() > 1 && TimeUtil.isOutExpires(authRegisterUserInfo.getExpiresTime())) {
            authRegisterUserInfo.setLevel(1);
            authRegisterUserInfo.setExpiresTime("");
            authRegisterUserDao.updateLevelById(authRegisterUserInfo);
        }
        String e = TimeUtil.getExpiresTimeByDay(activateTime, 7);
        if (!TimeUtil.isOutExpires(e)) {
            authRegisterUserInfo.setExperience(true);
        }
        return ResultMaster.success(authRegisterUserInfo);
    }

    public ResultInfo goReset(HttpServletRequest request, AuthRegisterUserInfo userInfo){
        if(authRegisterUserDao.countByUsername(userInfo) != 1){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByUsername(userInfo);
        if(authRegisterUserInfo == null){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if(authRegisterUserInfo.getEmailStatus() == 0){
            throw new XException(EnumResult.ERROR_USER_NO_ACTIVATE);
        }
        if(!authRegisterUserInfo.getEmail().equals(userInfo.getEmail())){
            throw new XException(EnumResult.ERROR_EMAIL_NOT_MATCH);
        }
        try{
            EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LD);
            String url = request.getRequestURL().toString();
            String path = url.substring(0, url.lastIndexOf("/"));
            emailMaster.setResetPasswordContent(path, authRegisterUserInfo.getUsername(), authRegisterUserInfo.getToken());
            emailMaster.sendMessage(authRegisterUserInfo.getEmail());
            return ResultMaster.success("Please check your email to confirm and reset the account password. " +
                    "The reset email may take up to 60 minutes to arrive, if you didn't get the " +
                    "email, please contact customer service.");
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(EnumResult.ERROR_SERVER_EXCEPTION);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public String reset(String token, Model model){
        AuthRegisterUserInfo authRegisterUserInfo = authRegisterUserDao.selectOneByToken(token);
        if(authRegisterUserInfo == null){
            throw new XException(EnumResult.ERROR_TOKEN_NOT_EXISTS);
        }
        String newToken = TokenUtil.create32(token, System.currentTimeMillis()+"");
        authRegisterUserInfo.setToken(newToken);
        authRegisterUserDao.updateToken(authRegisterUserInfo);
        model.addAttribute("authRegisterUserInfo", authRegisterUserInfo);
        return "manager/reset";
    }

    @Transactional(rollbackFor = Exception.class)
    public String updatePassword(AuthRegisterUserInfo userInfo, Model model){
        try{
            authRegisterUserDao.updatePassword(userInfo);
            model.addAttribute("resultInfo", ResultMaster.success("Password reset successfully"));
            return "manager/result";
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(ResultMaster.error(e.getLocalizedMessage()));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo insertOneUserLog(AuthUserLogInfo authUserLogInfo){
        try{
            authUserLogDao.insertOne(authUserLogInfo);
            return ResultMaster.success();
        }catch (Exception e){
            logger.error("Exception:", e);
            throw new XException(ResultMaster.error(e.getLocalizedMessage()));
        }
    }

}
