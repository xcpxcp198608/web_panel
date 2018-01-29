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
     * get the sales id by mac address
     * @param mac  mac
     * @return sales id
     */
    int selectSalesIdByMac(String mac);

    /**
     * get the device_rent full information by mac address
     * @param deviceRentInfo  required: mac
     * @return all field
     */
    DeviceRentInfo selectOneByMac(DeviceRentInfo deviceRentInfo);

    /**
     * get all device_rent info
     * @return all device_rent list
     */
    List<DeviceRentInfo> selectAll();

    /**
     * insert a new device rent info
     * @param deviceRentInfo required: mac, sales_id, dealer_id, admin_id
     */
    void insertOne(DeviceRentInfo deviceRentInfo);

    int updateRented(String mac);

    int countNoRentedBySalesId(int salesId);

}
