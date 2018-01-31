package com.wiatec.panel.web;

import com.wiatec.panel.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Controller
public class Auth {

    @Resource
    private AuthService authService;

    /**
     * sign in
     * @param username  username
     * @param password  password
     * @return          home page
     */
    @PostMapping(value = "/signin")
    public String signIn(HttpSession session, String username, String password, int type){
        return authService.signIn(session, username, password, type);
    }

    /**
     * sign out
     * @param request  HttpServletRequest
     * @return         sign in page
     */
    @GetMapping(value = "/signout")
    public String signOut(HttpServletRequest request){
        return authService.signOut(request);
    }


    /**
     * sign out
     * @param request  HttpServletRequest
     * @return         manager sign in page
     */
    @GetMapping(value = "/signout1")
    public String signOut1(HttpServletRequest request){
        return authService.signOut1(request);
    }



    /**
     * sign in
     * @param username  username
     * @param password  password
     * @return          home page
     */
    @PostMapping(value = "/signin_device")
    public String signInDevice(HttpSession session, String username, String password){
        return authService.signInDevice(session, username, password);
    }

}
