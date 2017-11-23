package com.wiatec.panel.api;

import com.wiatec.panel.service.AuthRentUserService;
import com.wiatec.panel.common.result.ResultInfo;
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
    public ResultInfo login(HttpServletRequest request, @PathVariable String clientKey,
                            @PathVariable String mac){
        return authRentUserService.login(request, clientKey, mac);
    }

    @PostMapping(value = "/validate/{clientKey}/{mac}")
    @ResponseBody
    public ResultInfo validate(HttpServletRequest request, @PathVariable String clientKey,
                                                              @PathVariable String mac, String country, String region,
                                                              String city, String timeZone){
        return authRentUserService.validate(request, clientKey, mac,country, region, city, timeZone);
    }

}
