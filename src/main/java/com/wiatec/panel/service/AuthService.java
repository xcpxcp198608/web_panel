package com.wiatec.panel.service;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthAdminDao;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    @Resource
    private AuthAdminDao authAdminDao;

    @Resource
    private AuthSalesDao authSalesDao;

    @Transactional
    public String signIn(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                         String username, String password){
        session.setAttribute("username", username);
        if(username.startsWith("s")){
            if(authSalesDao.countOne(new AuthSalesInfo(username, password)) != 1){
                throw new RuntimeException("username and password not match");
            }
            return "redirect:/sales/";
        }else {
            if(authAdminDao.countOne(new AuthAdminInfo(username, password)) != 1){
                throw new RuntimeException("username and password not match");
            }
            return "redirect:/admin/";
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
