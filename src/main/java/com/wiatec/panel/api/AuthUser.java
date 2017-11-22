package com.wiatec.panel.api;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthUserInfo;
import com.wiatec.panel.service.AuthRentUserService;
import com.wiatec.panel.service.AuthUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class AuthUser {

    @Resource
    private AuthUserService authUserService;

    @PostMapping(value = "/register")
    @ResponseBody
    public ResultInfo<AuthUserInfo> register(HttpServletRequest request, AuthUserInfo authUserInfo){
        return authUserService.register(request, authUserInfo);
    }

    @GetMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        String message = authUserService.activate(token);
        model.addAttribute("message", message);
        return "result";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResultInfo<AuthUserInfo> login(HttpServletRequest request, AuthUserInfo authUserInfo){
        return authUserService.login(request, authUserInfo);
    }

    @PostMapping(value = "/validate")
    @ResponseBody
    public ResultInfo<AuthUserInfo> validate(HttpServletRequest request,  AuthUserInfo authUserInfo){
        return authUserService.validate(request, authUserInfo);
    }

}
