package com.wiatec.panel.web;

import com.wiatec.panel.service.AuthRegisterUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * register user
 */
@Controller
@RequestMapping(value = "/manager")
public class AuthUser {

    @Resource
    private AuthRegisterUserService authRegisterUserService;

    @GetMapping(value = "/")
    public String home(Model model){
        return authRegisterUserService.home(model);
    }
}
