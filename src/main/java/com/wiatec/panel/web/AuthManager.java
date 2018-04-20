package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.oxm.pojo.log.LogUserLevelInfo;
import com.wiatec.panel.service.auth.AuthManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
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

    @GetMapping(value = "/logs")
    public String userLevelLogs(Model model){
        List<LogUserLevelInfo> logUserLevelInfoList = authManagerService.selectUserLevelLogs();
        model.addAttribute("logUserLevelInfoList", logUserLevelInfoList);
        return "manager/logs";
    }

    @GetMapping(value = "/users/{page}")
    public String users(HttpSession session, Model model, @PathVariable int page){
        return authManagerService.users(session, model, page);
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
    public ResultInfo updateLevel(HttpServletRequest request, @PathVariable int id, @PathVariable int level,
                                  @RequestBody AuthRegisterUserInfo authRegisterUserInfo){
        if(TextUtil.isEmpty(authRegisterUserInfo.getExpiresTime())){
            authRegisterUserInfo.setExpiresTime(new Date());
        }
        return authManagerService.updateLevel(request, id, level,
                new Date(TimeUtil.getUnixFromStr(authRegisterUserInfo.getExpiresTime())));
    }

    /**
     * export user information to excel file
     * @return
     */
    @PutMapping(value = "/users/export")
    @ResponseBody
    public ResultInfo export(HttpServletRequest request, @RequestParam(value = "macs[]") String[] macs){
        return authManagerService.export(request, macs);
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
