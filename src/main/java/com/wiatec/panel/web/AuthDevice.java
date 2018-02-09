package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.oxm.pojo.DevicePCPInfo;
import com.wiatec.panel.service.auth.AuthDeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/device")
public class AuthDevice {

    @Resource
    private AuthDeviceService authDeviceService;

    /**
     * home page
     * @return        home page
     */
    @GetMapping(value = "/")
    public String index(Model model){
        return "devices/index";
    }

    /**
     * home page
     * @return        home page
     */
    @GetMapping(value = "/home")
    public String home(Model model){
        return authDeviceService.home(model);
    }




    /**
     * rent devices page
     * @return   rent devices page
     */
    @GetMapping(value = "/all")
    public String allDevices(Model model){
        return authDeviceService.allDevices(model);
    }

    @PostMapping(value = "/all/save")
    @ResponseBody
    public ResultInfo insertDevice(String mac){
        return authDeviceService.insertDevice(mac);
    }

    @PostMapping(value = "/all/saves")
    @ResponseBody
    public ResultInfo bathInsertDevices(String startMac, String endMac){
        return authDeviceService.bathInsertDevices(startMac, endMac);
    }





    /**
     * purchase devices page
     * @return   rent devices page
     */
    @GetMapping(value = "/mcm")
    public String mcmDevices(Model model){
        return authDeviceService.mcmDevices(model);
    }


    @PutMapping(value = "/mcm/check")
    @ResponseBody
    public ResultInfo mcmCheckIn(String mac, String username, String checkInCode){
        return authDeviceService.mcmCheckIn(mac, username, checkInCode);
    }


    /**
     * rent devices page
     * @return   rent devices page
     */
    @GetMapping(value = "/pcp")
    public String pcpDevices(Model model){
        return authDeviceService.pcpDevices(model);
    }

    /**
     * save a device rent info
     * @param mac  mac
     * @return    ResultInfo
     */
    @PostMapping(value = "/pcp/check")
    @ResponseBody
    public ResultInfo pcpCheckIn(String mac){
        return authDeviceService.pcpCheckIn(mac);
    }



}
