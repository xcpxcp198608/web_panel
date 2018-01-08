package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.service.auth.AuthManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * register user
 */
@Controller
@RequestMapping(value = "/manager")
public class AuthManager {

    @Resource
    private AuthManagerService authManagerService;

    @GetMapping(value = "/")
    public String home(Model model){
        return authManagerService.home(model);
    }


    @GetMapping(value = "/users")
    public String users(HttpSession session, Model model){
        return authManagerService.users(session, model);
    }

    @GetMapping(value = "/user/{id}")
    @ResponseBody
    public AuthRegisterUserInfo userDetails(@PathVariable int id){
        return authManagerService.userDetails(id);
    }

    @PutMapping(value = "/activate/{id}")
    @ResponseBody
    public ResultInfo activate(@PathVariable int id){
        return authManagerService.activate(id);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseBody
    public ResultInfo delete(@PathVariable int id){
        return authManagerService.delete(id);
    }

    @PutMapping(value = "/update/level/{id}/{level}/{days}")
    @ResponseBody
    public ResultInfo updateLevel(@PathVariable int id, @PathVariable int level, @PathVariable int days){
        return authManagerService.updateLevel(id, level, days);
    }


    @GetMapping(value = "/chart/level/{level}/{year}")
    @ResponseBody
    public ResultInfo getLevelChart(@PathVariable int level, @PathVariable int year){
        return authManagerService.getLevelChart(level, year);
    }
}
