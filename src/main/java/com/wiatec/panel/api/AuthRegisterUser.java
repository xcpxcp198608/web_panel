package com.wiatec.panel.api;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.service.AuthRegisterUserService;
import com.wiatec.panel.common.result.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class AuthRegisterUser {

    @Resource
    private AuthRegisterUserService authRegisterUserService;

    @PostMapping(value = "/register")
    @ResponseBody
    public ResultInfo register(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.register(request, authRegisterUserInfo);
    }

    @GetMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        ResultInfo resultInfo = authRegisterUserService.activate(token);
        model.addAttribute("resultInfo", resultInfo);
        return "users/result";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResultInfo login(AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.login(authRegisterUserInfo);
    }

    @PostMapping(value = "/validate")
    @ResponseBody
    public ResultInfo validate(HttpSession session, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.validate(session, authRegisterUserInfo);
    }

    @GetMapping(value = "/go_reset")
    @ResponseBody
    public ResultInfo goReset(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.goReset(request, authRegisterUserInfo);
    }

    @GetMapping(value = "/reset/{token}")
    public String reset(@PathVariable String token, Model model){
        return authRegisterUserService.reset(token, model);
    }

    @PostMapping(value = "/update")
    public String updatePassword(AuthRegisterUserInfo authRegisterUserInfo, Model model){
        return authRegisterUserService.updatePassword(authRegisterUserInfo, model);
    }

}
