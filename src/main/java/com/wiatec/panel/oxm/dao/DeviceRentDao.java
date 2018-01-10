package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.DeviceRentInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DeviceRentDao {

    /**
     * check the device is exist by device mac address
     * @param deviceRentInfo required: mac
     * @return int, 1 = exist
     */
    int countOne(DeviceRentInfo deviceRentInfo);

    /**
     * get the device_rent full information by mac address
     * @param deviceRentInfo  required: mac
     * @return all field
     */
    DeviceRentInfo selectOne(DeviceRentInfo deviceRentInfo);

    /**
     * get all device_rent info
     * @return all device_rent list
     */
    List<DeviceRentInfo> selectAll();

    void insertOne(DeviceRentInfo deviceRentInfo);

}
