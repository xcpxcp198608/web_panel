package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.DeviceRentInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.service.auth.AuthAdminService;
import com.wiatec.panel.service.auth.AuthDeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
        return authDeviceService.devices(model);
    }


    /**
     * save a device rent info
     * @param deviceRentInfo  DeviceRentInfo required: mac, dealerId
     * @return         ResultInfo
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public ResultInfo saveDevice(DeviceRentInfo deviceRentInfo){
        return authDeviceService.saveDevice(deviceRentInfo);
    }

}
