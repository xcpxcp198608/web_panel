package com.wiatec.panel.service.auth;

import com.wiatec.panel.common.result.EnumResult;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.ResultMaster;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(AuthDeviceService.class);

    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private DeviceRentDao deviceRentDao;


    public String devices(Model model){
        List<DeviceRentInfo> deviceRentInfoList = deviceRentDao.selectAll();
        List<AuthSalesInfo> authSalesInfoList = authSalesDao.selectAll();
        model.addAttribute("deviceRentInfoList", deviceRentInfoList);
        model.addAttribute("authSalesInfoList", authSalesInfoList);
        return "devices/devices";
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultInfo saveDevice(DeviceRentInfo deviceRentInfo){
        if(deviceRentInfo.getMac().length() != 17){
            throw new XException(EnumResult.ERROR_MAC_FORMAT);
        }
        if(deviceRentDao.countOne(deviceRentInfo) ==1){
            throw new XException(1100, "this mac address already check in");
        }
        deviceRentInfo.setMac(deviceRentInfo.getMac().toUpperCase());
        AuthSalesInfo authSalesInfo = authSalesDao.selectOneById(deviceRentInfo.getSalesId());
        deviceRentInfo.setDealerId(authSalesInfo.getDealerId());
        deviceRentDao.insertOne(deviceRentInfo);
        int salesStoreCount = deviceRentDao.countNoRentedBySalesId(authSalesInfo.getId());
        if(salesStoreCount >= 10){
            authSalesDao.updateGoldById(authSalesInfo.getId());
        }
        return ResultMaster.success(deviceRentDao.selectOneByMac(deviceRentInfo));
    }


}
