package com.wiatec.panel.api;

import com.wiatec.panel.service.AuthRentUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/rent")
public class AuthRentUser {

    @Resource
    private AuthRentUserService authRentUserService;

    @PostMapping(value = "/login/{clientKey}/{mac}")
    @ResponseBody
    public void login(HttpServletRequest request, @PathVariable(value = "clientKey") String clientKey,
                      @PathVariable(value = "mac") String mac){
        authRentUserService.login(request, clientKey, mac);
    }

    @PostMapping(value = "/validate/{clientKey}/{mac}")
    @ResponseBody
    public void validate(HttpServletRequest request, @PathVariable(value = "clientKey") String clientKey,
                         @PathVariable(value = "mac") String mac){
        authRentUserService.validate(request, clientKey, mac);
    }

}
