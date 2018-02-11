package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import com.wiatec.panel.oxm.pojo.DeviceMLMInfo;
import com.wiatec.panel.oxm.pojo.DevicePCPInfo;
import com.wiatec.panel.service.auth.AuthDeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/device")
public class AuthDevice {

    @Resource
    private AuthDeviceService authDeviceService;


    /**
     * show device login page
     * @return  jsp/devices/index.jsp
     */
    @GetMapping(value = "/")
    public String index(Model model){
        return "devices/index";
    }


    /**
     * show device home page
     * @return jsp/devices/home.jsp
     */
    @GetMapping(value = "/home")
    public String home(){
        return "devices/home";
    }


    /**
     * show all devices page
     * @return  jsp/devices/devices_all.jsp
     */
    @GetMapping(value = "/all")
    public String allDevices(Model model){
        List<DeviceAllInfo> deviceAllInfoList = authDeviceService.selectAllDevices();
        model.addAttribute("deviceAllInfoList", deviceAllInfoList);
        return "devices/devices_all";
    }

    /**
     * add a new device into device system
     * @param mac mac address
     * @return ResultInfo with DeviceAllInfo if insert successfully
     */
    @PostMapping(value = "/all/save")
    @ResponseBody
    public ResultInfo insertDeviceIntoAllDevices(String mac){
        return authDeviceService.insertDeviceIntoAllDevices(mac);
    }

    /**
     * bath add mac address into device system
     * @param startMac start mac address
     * @param endMac end mac address
     * @return ResultInfo
     */
    @PostMapping(value = "/all/saves")
    @ResponseBody
    public ResultInfo bathInsertDevices(String startMac, String endMac){
        return authDeviceService.bathInsertDevices(startMac, endMac);
    }



    /**
     * show mcm devices page
     * @return jsp/devices/devices_mcm.jsp
     */
    @GetMapping(value = "/mcm")
    public String mcmDevices(Model model){
        List<DeviceMLMInfo> deviceMLMInfoList = authDeviceService.selectAllMCMDevices();
        model.addAttribute("deviceMLMInfoList", deviceMLMInfoList);
        return "devices/devices_mcm";
    }

    /**
     * check in mcm devices, without this the user can not register
     * @param mac device mac address
     * @param username  target username
     * @param checkInCode employee authorization code when check in
     * @return v
     */
    @PutMapping(value = "/mcm/check")
    @ResponseBody
    public ResultInfo mcmCheckIn(String mac, String username, String checkInCode){
        return authDeviceService.mcmCheckIn(mac, username, checkInCode);
    }


    /**
     * show PCP devices page
     * @return  jsp/devices/devices_pcp.jsp
     */
    @GetMapping(value = "/pcp")
    public String pcpDevices(Model model){
        List<DevicePCPInfo> devicePCPInfoList = authDeviceService.pcpDevices();
        model.addAttribute("devicePCPInfoList", devicePCPInfoList);
        return "devices/devices_pcp";
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
