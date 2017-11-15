package com.wiatec.panel.web;

import com.wiatec.panel.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class Auth {

    @Resource
    private AuthService authService;

    /**
     * sign in
     * @param request
     * @param response
     * @param username
     * @param password
     * @return
     */
    @PostMapping(value = "/signin")
    public String signIn(HttpServletRequest request, HttpServletResponse response, String username, String password){
        return authService.signIn(request, response, username, password);
    }

    /**
     * sign out
     * @param request
     * @return
     */
    @GetMapping(value = "/signout")
    public String signOut(HttpServletRequest request){
        return authService.signOut(request);
    }

}
