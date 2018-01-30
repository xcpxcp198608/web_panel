package com.wiatec.panel.service;

import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Service
public class AuthService {

    private static final String DEVICE_USER = "ldevice";

    @Resource
    private AuthManagerDao authManagerDao;
    @Resource
    private AuthAdminDao authAdminDao;
    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthDeviceDao authDeviceDao;

    public String signIn(HttpSession session, String username, String password, int type){
        session.setAttribute(SessionListener.KEY_AUTH_USER_NAME, username);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_FORMAT);
        }
        if(TextUtil.isEmpty(password)){
            throw new XException(EnumResult.ERROR_PASSWORD_FORMAT);
        }
        switch (type){
            case 0:
                if(authManagerDao.countOne(new AuthManagerInfo(username, password)) == 1) {
                    return "redirect:/manager/home";
                }else{
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
            case 1:
                if(authAdminDao.countOne(new AuthAdminInfo(username, password)) == 1) {
                    return "redirect:/admin/";
                }else{
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
            case 2:
                if(authDealerDao.countOne(new AuthDealerInfo(username, password)) == 1) {
                    return "redirect:/dealer/";
                }else{
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
            case 3:
                if(authSalesDao.countOne(new AuthSalesInfo(username, password)) == 1) {
                    AuthSalesInfo authSalesInfo = authSalesDao.selectOneByUsername(new AuthSalesInfo(username));
                    if(TimeUtil.isOutExpires(authSalesInfo.getExpiresTime())){
                        throw new XException(1001, "your account is out expiration");
                    }else {
                        return "redirect:/sales/";
                    }
                }else{
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
            default:
                throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
    }

    public String signOut(HttpServletRequest request){
        String sessionId = request.getCookies()[0].getValue();
        HttpSession session = SessionListener.idSessionMap.get(sessionId);
        String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        SessionListener.idSessionMap.remove(sessionId);
        SessionListener.authUserSessionMap.remove(username);
        session.invalidate();
        return "redirect:/";
    }


    public String signInDevice(HttpSession session, String username, String password){
        session.setAttribute(SessionListener.KEY_AUTH_USER_NAME, username);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_FORMAT);
        }
        if(TextUtil.isEmpty(password)){
            throw new XException(EnumResult.ERROR_PASSWORD_FORMAT);
        }
        if(authDeviceDao.countOne(new AuthDeviceInfo(username, password)) == 1){
            return "redirect:/device/home";
        }else{
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }

    }
}
