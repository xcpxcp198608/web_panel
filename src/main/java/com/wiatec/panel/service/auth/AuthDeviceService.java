package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.MacUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class AuthDeviceService {

    @Resource
    private AuthEmployeeDao authEmployeeDao;
    @Resource
    private DeviceAllDao deviceAllDao;
    @Resource
    private DeviceMLMDao deviceMLMDao;
    @Resource
    private DevicePCPDao devicePCPDao;
    @Resource
    private AuthDeviceDao authDeviceDao;


    /**
     * get all devices info from special start id
     * @return list of DeviceAllInfo
     */
    public List<DeviceAllInfo> selectAllDevices(HttpServletRequest request){
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            return deviceAllDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDE, 4000);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            return deviceAllDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDM, 4000);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.WIATEC) {
            return deviceAllDao.selectAll(4000);
        }
        return null;
    }


    /**
     * insert new device into table of device_all
     * 1. check mac address format
     * 2. insert into table
     * @param mac mac address
     * @return ResultInfo with DeviceAllInfo if insert successfully
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo insertDeviceIntoAllDevices(HttpServletRequest request, String mac){
        String newMac = mac.toUpperCase();
        if(!MacUtil.validateMac(newMac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        String distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDM;
        }
        int i = deviceAllDao.insertOne(newMac, distributor);
        if(i != 1){
            throw new XException(1001, "mac address duplicate");
        }
        return ResultMaster.success(deviceAllDao.selectOneByMac(newMac));
    }


    /**
     * bath insert new devices into table of device_all
     * 1.validate mac address format
     * 2.check end mac great than start mac
     * 3.bath insert mac into table of device_all
     * @param startMac start mac
     * @param endMac end mac, must great than start mac
     * @return ResultInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo bathInsertDevices(HttpServletRequest request, String startMac, String endMac){
        if(!MacUtil.validateMac(startMac) || !MacUtil.validateMac(endMac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(!MacUtil.compare(startMac, endMac, 5)){
            throw new XException(1001, "start mac great than end mac");
        }
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        String distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDM;
        }
        String[] macs = MacUtil.createMacs(startMac, endMac, 5);
        int i = deviceAllDao.bathInsert(macs, distributor);
        if(i != macs.length){
            throw new XException(1001, "mac add failure, check duplicate");
        }
        return ResultMaster.success();
    }




    /**
     * select all MCM devices
     * @return all mcm devices information
     */
    public List<DeviceMLMInfo> selectAllMCMDevices(HttpServletRequest request){
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            return deviceMLMDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDE, 0);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            return deviceMLMDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDM, 0);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.WIATEC) {
            return deviceMLMDao.selectAll(0);
        }
        return null;
    }

    /**
     * check in the MCM device
     * 1. validate mac address format
     * 2. check username, can't empty
     * 3. check the employee authorization code
     * 4. does the mac address exists?
     * 5. does the mac address check in the table of devices_all
     * 6. insert the mac, username, employee name(check_in_user), check in time into device_mcm
     * 7. update device status to MCM in table of device_all
     * @param mac device mac address
     * @param username target username
     * @param checkInCode employee authorization code when check in
     * @return ResultInfo with DeviceMLMInfo if check in successfully
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo mcmCheckIn(HttpServletRequest request, String mac, String username, String checkInCode){
        mac = mac.toUpperCase();
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_USERNAME_NOT_EXISTS);
        }
        AuthEmployeeInfo authEmployeeInfo = authEmployeeDao.selectOneByCode(checkInCode);
        if(authEmployeeInfo == null){
            throw new XException(1001, "authorization code error");
        }
        if(deviceAllDao.countOneByMac(mac) != 1){
            throw new XException(1001, "mac address no check in");
        }
        DeviceAllInfo deviceAllInfo = deviceAllDao.selectOneByMac(mac);
        if(deviceAllInfo.getStatus() != 0){
            throw new XException(1001, "the device already out going");
        }
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        String distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDM;
        }
        int i = deviceMLMDao.insertOne(mac, distributor, username, authEmployeeInfo.getUsername());
        if(i != 1){
            throw new XException(EnumResult.ERROR_OPERATION_FAILURE);
        }
        deviceAllDao.updateStatusByMac(mac, DeviceAllInfo.STATUS_MLM);
        return ResultMaster.success(deviceMLMDao.selectOneByMac(mac));
    }


    /**
     * select all PCP devices information
     * @return list of DevicePCPInfo
     */
    public List<DevicePCPInfo> pcpDevices(HttpServletRequest request){

        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            return devicePCPDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDE);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            return devicePCPDao.selectAllByDis(DeviceAllInfo.DISTRIBUTOR_LDM);
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.WIATEC) {
            return devicePCPDao.selectAll();
        }
        return null;
    }

    /**
     * check in the PCP device
     * 1. check the mac address format
     * 2. does the mac address already check in device_pcp
     * 3. insert the device in device_pcp
     * 4. update the device status to PCP in device_all
     * @param mac mac address
     * @return ResultInfo with DevicePCPInfo if successfully
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultInfo pcpCheckIn(HttpServletRequest request, String mac){
        mac = mac.toUpperCase();
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(deviceAllDao.countOneByMac(mac) != 1){
            throw new XException(1001, "mac address no check in");
        }
        DeviceAllInfo deviceAllInfo = deviceAllDao.selectOneByMac(mac);
        if(deviceAllInfo.getStatus() != 0){
            throw new XException(1001, "the device already out going");
        }
        AuthDeviceInfo authDeviceInfo = getAuthDevice(request);
        String distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDE){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDE;
        }else if(authDeviceInfo.getPermission() == AuthDeviceInfo.LDM){
            distributor = DeviceAllInfo.DISTRIBUTOR_LDM;
        }
        int i = devicePCPDao.insertOne(mac, distributor);
        if(i != 1) {
            throw new XException(EnumResult.ERROR_OPERATION_FAILURE);
        }
        deviceAllDao.updateStatusByMac(mac, DeviceAllInfo.STATUS_PCP);
        return ResultMaster.success(devicePCPDao.selectOneByMac(mac));
    }


    private AuthDeviceInfo getAuthDevice(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(TextUtil.isEmpty(username)){
            throw new XException(EnumResult.ERROR_UNAUTHORIZED);
        }
        return authDeviceDao.selectOneByUsername(username);
    }
}
