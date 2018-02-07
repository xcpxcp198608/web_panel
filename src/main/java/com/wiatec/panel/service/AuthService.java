package com.wiatec.panel.service;

import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.XException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Service
public class AuthService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_FORMAT);
        }
        if(TextUtil.isEmpty(password)){
            throw new XException(EnumResult.ERROR_PASSWORD_FORMAT);
        }
        session.setAttribute(SessionListener.KEY_AUTH_USER_NAME, username);
        switch (type){
            case 0:
                AuthManagerInfo authManagerInfo = authManagerDao.selectOne(new AuthManagerInfo(username, password));
                if(authManagerInfo == null){
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
                session.setAttribute("permission", authManagerInfo.getPermission());
                return "redirect:/manager/home";
            case 1:
                AuthAdminInfo authAdminInfo = authAdminDao.selectOne(new AuthAdminInfo(username, password));
                if(authAdminInfo == null){
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
                session.setAttribute("permission", authAdminInfo.getPermission());
                return "redirect:/admin/";
            case 2:
                if(authDealerDao.countOne(new AuthDealerInfo(username, password)) == 1) {
                    session.setAttribute("permission", 0);
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
                        session.setAttribute("permission", 0);
                        return "redirect:/sales/";
                    }
                }else{
                    throw new XException(EnumResult.ERROR_UNAUTHORIZED);
                }
            default:
                throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
    }


    public String signInDevice(HttpSession session, String username, String password){
        session.setAttribute(SessionListener.KEY_AUTH_USER_NAME, username);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_FORMAT);
        }
        if(TextUtil.isEmpty(password)){
            throw new XException(EnumResult.ERROR_PASSWORD_FORMAT);
        }
        AuthDeviceInfo authDeviceInfo = authDeviceDao.selectOne(new AuthDeviceInfo(username, password));
        if(authDeviceInfo == null){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        session.setAttribute("permission", authDeviceInfo.getPermission());
        return "redirect:/device/home";
    }


    public String signOut(HttpServletRequest request){
        releaseSession(request);
        return "redirect:/";
    }

    public String signOut1(HttpServletRequest request){
        releaseSession(request);
        return "redirect:/manager/";
    }

    private void releaseSession(HttpServletRequest request){
        try{
            String sessionId = request.getCookies()[0].getValue();
            HttpSession session = SessionListener.idSessionMap.get(sessionId);
            String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
            SessionListener.idSessionMap.remove(sessionId);
            SessionListener.authUserSessionMap.remove(username);
            session.invalidate();
        }catch (Exception e) {
            logger.error("Exception", e);
        }
    }
}
