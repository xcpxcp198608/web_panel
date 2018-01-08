package com.wiatec.panel.service;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthAdminDao;
import com.wiatec.panel.oxm.dao.AuthDealerDao;
import com.wiatec.panel.oxm.dao.AuthManagerDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
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

    @Resource
    private AuthManagerDao authManagerDao;
    @Resource
    private AuthAdminDao authAdminDao;
    @Resource
    private AuthDealerDao authDealerDao;
    @Resource
    private AuthSalesDao authSalesDao;

    public String signIn(HttpSession session, String username, String password, int type){
        session.setAttribute("username", username);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_FORMAT);
        }
        if(TextUtil.isEmpty(password)){
            throw new XException(EnumResult.ERROR_PASSWORD_FORMAT);
        }
        switch (type){
            case 0:
                if(authManagerDao.countOne(new AuthManagerInfo(username, password)) == 1) {
                    return "redirect:/manager/";
                }
            case 1:
                if(authAdminDao.countOne(new AuthAdminInfo(username, password)) == 1) {
                    return "redirect:/admin/";
                }
            case 2:
                if(authDealerDao.countOne(new AuthDealerInfo(username, password)) == 1) {
                    return "redirect:/dealer/";
                }
            case 3:
                if(authSalesDao.countOne(new AuthSalesInfo(username, password)) == 1) {
                    return "redirect:/sales/";
                }
            default:
                throw new XException(EnumResult.ERROR_AUTHORIZE);
        }
    }

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
