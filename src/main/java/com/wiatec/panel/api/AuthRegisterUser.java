package com.wiatec.panel.api;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.AuthUserLogInfo;
import com.wiatec.panel.service.AuthRegisterUserService;
import com.wiatec.panel.common.result.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/user")
public class AuthRegisterUser {

    @Resource
    private AuthRegisterUserService authRegisterUserService;

    /**
     * btv user register
     * @param request   {@link HttpServletRequest}
     * @param authRegisterUserInfo  {@link AuthRegisterUserInfo}
     *        required:  username, password, mac ,email, phone, firstName, lastName
     * @return  {@link ResultInfo}
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public ResultInfo register(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo, String language){
        return authRegisterUserService.register(request, authRegisterUserInfo, language);
    }

    /**
     * mobile app sign up
     * @param request   {@link HttpServletRequest}
     * @param authRegisterUserInfo  {@link AuthRegisterUserInfo}
     *        required:  username, password, mac ,email, phone, firstName, lastName
     * @return  {@link ResultInfo}
     */
    @PostMapping(value = "/signup")
    @ResponseBody
    public ResultInfo signUp(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.signUp(request, authRegisterUserInfo);
    }

    /**
     * activate user
     * @param token  token from email
     * @param model  spring model view
     * @return  jsp -> users -> result
     */
    @GetMapping(value = "/activate/{token}")
    public String activate(@PathVariable String token, Model model){
        ResultInfo resultInfo = authRegisterUserService.activate(token);
        model.addAttribute("resultInfo", resultInfo);
        return "manager/result";
    }

    /**
     * login
     * @param authRegisterUserInfo  {@link AuthRegisterUserInfo}
     *        required:  username, password, mac
     * @return  {@link ResultInfo}
     */
    @PostMapping(value = "/login")
    @ResponseBody
    public ResultInfo login(AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.login(authRegisterUserInfo);
    }

    /**
     * validate
     * @param session   {@link HttpSession}
     * @param authRegisterUserInfo   {@link AuthRegisterUserInfo}
     *        required:  username, country, region, city, timeZone
     * @return  {@link ResultInfo}
     */
    @PostMapping(value = "/validate")
    @ResponseBody
    public ResultInfo validate(HttpSession session, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.validate(session, authRegisterUserInfo);
    }

    /**
     * send reset password email to user
     * @param request  {@link HttpServletRequest}
     * @param authRegisterUserInfo    {@link AuthRegisterUserInfo}
     *        required:  username, email
     * @return   {@link ResultInfo}
     */
    @PostMapping(value = "/go_reset")
    @ResponseBody
    public ResultInfo goReset(HttpServletRequest request, AuthRegisterUserInfo authRegisterUserInfo){
        return authRegisterUserService.goReset(request, authRegisterUserInfo);
    }

    /**
     * response the reset email link from user
     * @param token  current user token
     * @param model  spring model view
     * @return  jsp -> user -> reset
     */
    @GetMapping(value = "/reset/{token}")
    public String reset(@PathVariable String token, Model model){
        return authRegisterUserService.reset(token, model);
    }

    /**
     * update user password
     * @param authRegisterUserInfo {@link AuthRegisterUserInfo}
     * @param model  spring model view
     * @return jsp -> users -> result
     */
    @PostMapping(value = "/update")
    public String updatePassword(AuthRegisterUserInfo authRegisterUserInfo, Model model){
        return authRegisterUserService.updatePassword(authRegisterUserInfo, model);
    }

    /**
     *  upload user log data
     * @param authUserLogInfo  {@link AuthUserLogInfo}
     * @return  {@link ResultInfo}
     */
    @PostMapping(value = "/log")
    @ResponseBody
    public ResultInfo saveData(AuthUserLogInfo authUserLogInfo){
        return authRegisterUserService.insertOneUserLog(authUserLogInfo);
    }

}
