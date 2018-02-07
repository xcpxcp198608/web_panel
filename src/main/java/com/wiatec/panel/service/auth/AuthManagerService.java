package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.common.utils.MacUtil;
import com.wiatec.panel.common.utils.TextUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.common.utils.TokenUtil;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.dao.AuthManagerDao;
import com.wiatec.panel.oxm.dao.AuthRegisterUserDao;
import com.wiatec.panel.oxm.dao.DeviceDao;
import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.DeviceInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.MonthVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.YearVolumeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class AuthManagerService {

    @Autowired
    private AuthRegisterUserDao authRegisterUserDao;
    @Autowired
    private AuthManagerDao authManagerDao;
    @Autowired
    private DeviceDao deviceDao;

    public String home(){
        return "manager/home";
    }

    public String users(HttpSession session, Model model){
        String username = (String) session.getAttribute(SessionListener.KEY_AUTH_USER_NAME);
        if(username == null){
            throw new XException(EnumResult.ERROR_RE_LOGIN);
        }
        AuthManagerInfo authManagerInfo = authManagerDao.selectOneByUsername(username);
        List<AuthRegisterUserInfo> authRegisterUserInfoList;
        if(authManagerInfo.getPermission() >= AuthManagerInfo.LEVEL_HIGHEST ) {
            authRegisterUserInfoList = authRegisterUserDao.selectAll(0);
        }else{
            authRegisterUserInfoList = authRegisterUserDao.selectAll(100);
        }
        for(AuthRegisterUserInfo authRegisterUserInfo: authRegisterUserInfoList){
            if(SessionListener.userSessionMap.containsKey(authRegisterUserInfo.getUsername())){
                authRegisterUserInfo.setOnline(true);
            }
        }
        model.addAttribute("authRegisterUserInfoList", authRegisterUserInfoList);
        return "manager/customers";
    }

    public AuthRegisterUserInfo userDetails(int id){
        return authRegisterUserDao.selectOneById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo activate(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setToken(TokenUtil.create(id+"", System.currentTimeMillis()+""));
        authRegisterUserDao.updateEmailStatusById(authRegisterUserInfo);
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo delete(int id){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        int i = authRegisterUserDao.deleteOneById(authRegisterUserInfo);
        if(i != 1){
            throw new XException(1001, "delete failure");
        }
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo updateLevel(int id, int level, String expiresTime){
        AuthRegisterUserInfo authRegisterUserInfo = new AuthRegisterUserInfo();
        authRegisterUserInfo.setId(id);
        authRegisterUserInfo.setLevel(level);
        expiresTime = expiresTime + " 00:00:00";
        if(level == 1){
            authRegisterUserInfo.setExpiresTime(new Date(TimeUtil.DEFAULT_TIME));
        }else if(level == 0){
            authRegisterUserInfo.setExpiresTime(new Date(TimeUtil.getUnixFromStr(authRegisterUserDao.selectExpiresTimeById(id))));
        }else {
            authRegisterUserInfo.setExpiresTime(new Date(TimeUtil.getUnixFromStr(expiresTime)));
        }
        authRegisterUserDao.updateLevelById(authRegisterUserInfo);
        return ResultMaster.success(authRegisterUserInfo);
    }

    public String level(Model model){
        LevelDistributionInfo levelDistributionInfo = authRegisterUserDao.selectAllLevelDistribution();
        model.addAttribute("levelDistributionInfo", levelDistributionInfo);
        return "manager/level";
    }

    public String distribution(){
        return "manager/distribution";
    }

    public String devices(Model model){
        List<DeviceInfo> enableDeviceInfoList = deviceDao.selectAllWithEnable(1);
        List<DeviceInfo> disableDeviceInfoList = deviceDao.selectAllWithEnable(0);
        model.addAttribute("enableDeviceInfoList", enableDeviceInfoList);
        model.addAttribute("disableDeviceInfoList", disableDeviceInfoList);
        return "manager/devices";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo insertDevice(String mac){
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        String newMac = mac.toUpperCase();
        int i = deviceDao.insertOne(new DeviceInfo(newMac));
        if(i != 1){
            throw new XException(1001, "mac address duplicate");
        }
        return ResultMaster.success(deviceDao.selectOneByMac(newMac));
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo bathInsertDevices(String startMac, String endMac){
        if(!MacUtil.validateMac(startMac) || !MacUtil.validateMac(endMac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(!MacUtil.compare(startMac, endMac, 5)){
            throw new XException(1001, "start mac less than end mac");
        }
        String[] macs = MacUtil.createMacs(startMac, endMac, 5);
        int i = deviceDao.bathInsert(macs);
        if(i != macs.length){
            throw new XException(1001, "mac add failure, check duplicate");
        }
        return ResultMaster.success();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo enableDevice(String mac, String username){
        if(!MacUtil.validateMac(mac)){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(TextUtil.isEmpty(username)){
            username = "unknown";
        }
        DeviceInfo deviceInfo = deviceDao.selectOneByMac(mac);
        if(deviceInfo == null){
            throw new XException(EnumResult.ERROR_MAC_NOT_EXISTS);
        }
        if(deviceInfo.isEnable()){
            throw new XException(1001, "the device has already enable");
        }
        int i = deviceDao.updateOneToEnable(mac, username);
        if(i != 1){
            throw new XException(EnumResult.ERROR_UPDATE_FAILURE);
        }
        return ResultMaster.success(deviceDao.selectOneByMac(mac));
    }

    public List<VolumeDistributionInfo> getDistributionData(){
        return authRegisterUserDao.getDistributionData();
    }


    public ResultInfo getMonthVolume(int year, int month){
        List<MonthVolumeInfo> monthVolumeInfoList = authRegisterUserDao.selectVolumeOfMonth(new YearOrMonthInfo(year, month));
        return ResultMaster.success(monthVolumeInfoList);
    }

    public ResultInfo getYearVolume(int year, int month){
        List<YearVolumeInfo> yearVolumeInfoList = authRegisterUserDao
                .selectVolumeOfYear(new YearOrMonthInfo(year, month, true));
        return ResultMaster.success(yearVolumeInfoList);
    }

    public ResultInfo<Integer> getLevelChart(int level, int year){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo(year);
        //sale id replace level
        yearOrMonthInfo.setSalesId(level + "");
        return ResultMaster.success(authRegisterUserDao.selectLevelOfYear(yearOrMonthInfo));
    }

}
