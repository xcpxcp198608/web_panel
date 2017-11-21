package com.wiatec.panel.service;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthSalesDao;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.xutils.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    @Resource
    private AuthSalesDao authSalesDao;

    @Transactional
    public String signIn(HttpSession session, String username, String password){
        session.setAttribute("username", username);
        String permission = authSalesDao.selectPermission(new AuthSalesInfo(username, password));
        if(TextUtil.isEmpty(permission)){
            throw new RuntimeException("username and password not match");
        }
        if("admin".equals(permission)){
            return "redirect:/admin/";
        }
        if("sales".equals(permission)){
            return "redirect:/sales/";
        }
        return "redirect:/";
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
