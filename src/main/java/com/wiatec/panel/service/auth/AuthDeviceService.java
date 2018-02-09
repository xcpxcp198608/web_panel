package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.MacUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class AuthDeviceService {

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private AuthEmployeeDao authEmployeeDao;
    @Resource
    private DeviceAllDao deviceAllDao;
    @Resource
    private DeviceMCMDao deviceMCMDao;
    @Resource
    private DevicePCPDao devicePCPDao;

    /**
     * show devices home page
     * @param model ui model
     * @return devices -> home.jsp
     */
    public String home(Model model){
        return "devices/home";
    }



    /**
     * show all devices page
     * @param model ui model
     * @return devices -> devices_all.jsp
     */
    public String allDevices(Model model){
        List<DeviceAllInfo> deviceAllInfoList = deviceAllDao.selectAll(10000);
        model.addAttribute("deviceAllInfoList", deviceAllInfoList);
        return "devices/devices_all";
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultInfo insertDevice(String mac){
        String newMac = mac.toUpperCase();
        if(!MacUtil.validateMac(newMac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        int i = deviceAllDao.insertOne(newMac);
        if(i != 1){
            throw new XException(1001, "mac address duplicate");
        }
        return ResultMaster.success(deviceAllDao.selectOneByMac(newMac));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo bathInsertDevices(String startMac, String endMac){
        if(!MacUtil.validateMac(startMac) || !MacUtil.validateMac(endMac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(!MacUtil.compare(startMac, endMac, 5)){
            throw new XException(1001, "start mac great than end mac");
        }
        String[] macs = MacUtil.createMacs(startMac, endMac, 5);
        int i = deviceAllDao.bathInsert(macs);
        if(i != macs.length){
            throw new XException(1001, "mac add failure, check duplicate");
        }
        return ResultMaster.success();
    }




    /**
     * show MCM devices page
     * @param model ui model
     * @return devices -> devices_rent.jsp
     */
    public String mcmDevices(Model model){
        List<DeviceMCMInfo> deviceMCMInfoList = deviceMCMDao.selectAll(0);
        model.addAttribute("deviceMCMInfoList", deviceMCMInfoList);
        return "devices/devices_mcm";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo mcmCheckIn(String mac, String username, String checkInCode){
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        if(deviceMCMDao.countOneByMac(mac) == 1){
            throw new XException(EnumResult.ERROR_MAC_EXISTS);
        }
        AuthEmployeeInfo authEmployeeInfo = authEmployeeDao.selectOneByCode(checkInCode);
        if(authEmployeeInfo == null){
            throw new XException(1001, "authorization code error");
        }
        int i = deviceMCMDao.insertOne(mac, username, authEmployeeInfo.getUsername());
        if(i != 1){
            throw new XException(EnumResult.ERROR_OPERATION_FAILURE);
        }
        deviceAllDao.updateStatusByMac(mac, DeviceAllInfo.STATUS_MCM);
        return ResultMaster.success(deviceMCMDao.selectOneByMac(mac));
    }





    /**
     * show rent devices page
     * @param model ui model
     * @return devices -> devices_rent.jsp
     */
    public String pcpDevices(Model model){
        List<DevicePCPInfo> devicePCPInfoList = devicePCPDao.selectAll();
        model.addAttribute("devicePCPInfoList", devicePCPInfoList);
        return "devices/devices_pcp";
    }

    /**
     * save a device in table of device_rent and delete the device from table of device
     * @param mac  mac
     * @return ResultInfo with DevicePCPInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo pcpCheckIn(String mac){
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(devicePCPDao.countOneByMac(mac) ==1){
            throw new XException(1100, "this mac address already check in");
        }
        int i = devicePCPDao.insertOne(mac);
        if(i != 1) {
            throw new XException(EnumResult.ERROR_OPERATION_FAILURE);
        }
        deviceAllDao.updateStatusByMac(mac, DeviceAllInfo.STATUS_PCP);
        return ResultMaster.success(devicePCPDao.selectOneByMac(mac));
    }

}
