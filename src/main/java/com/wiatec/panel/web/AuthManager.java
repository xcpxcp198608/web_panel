package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.service.auth.AuthManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * register user manager
 * @author patrick
 */
@Controller
@RequestMapping(value = "/manager")
public class AuthManager {

    @Resource
    private AuthManagerService authManagerService;

    @GetMapping(value = "/")
    public String index(){
        return "manager/index";
    }

    @GetMapping(value = "/home")
    public String home(){
        return authManagerService.home();
    }

    @GetMapping(value = "/level")
    public String level(Model model){
        return authManagerService.level(model);
    }

    @GetMapping(value = "/distribution")
    public String distribution(){

        return authManagerService.distribution();
    }

    @GetMapping(value = "/chart/distribution")
    @ResponseBody
    public List<VolumeDistributionInfo> getDistributionData(){
        return authManagerService.getDistributionData();
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

    @PutMapping(value = "/update/level/{id}/{level}")
    @ResponseBody
    public ResultInfo updateLevel(@PathVariable int id, @PathVariable int level,
                                  @RequestBody AuthRegisterUserInfo authRegisterUserInfo){
        return authManagerService.updateLevel(id, level, authRegisterUserInfo.getExpiresTime());
    }

    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public ResultInfo getMonthVolume(@PathVariable int year, @PathVariable int month){
        return authManagerService.getMonthVolume(year, month);
    }

    @GetMapping(value = "/chart/volume/year/{year}/{month}")
    @ResponseBody
    public ResultInfo getYearVolume(@PathVariable int year, @PathVariable int month){
        return authManagerService.getYearVolume(year, month);
    }

    @GetMapping(value = "/chart/level/{level}/{year}")
    @ResponseBody
    public ResultInfo getLevelChart(@PathVariable int level, @PathVariable int year){
        return authManagerService.getLevelChart(level, year);
    }
}
