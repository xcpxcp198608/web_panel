package com.wiatec.panel.api;

import com.wiatec.panel.oxm.pojo.AuthUserInfo;
import com.wiatec.panel.service.AuthUserService;
import com.wiatec.panel.xutils.result.ResultInfo;
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
    public ResultInfo register(HttpServletRequest request, AuthUserInfo authUserInfo){
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
    public ResultInfo login(HttpServletRequest request, AuthUserInfo authUserInfo){
        return authUserService.login(request, authUserInfo);
    }

    @PostMapping(value = "/validate")
    @ResponseBody
    public ResultInfo validate(HttpServletRequest request,  AuthUserInfo authUserInfo){
        return authUserService.validate(request, authUserInfo);
    }

}
