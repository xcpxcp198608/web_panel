package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import com.wiatec.panel.oxm.pojo.DevicePCPInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DevicePCPDao {

    /**
     * check the device is exist by device mac address
     * @param devicePCPInfo required: mac
     * @return int, 1 = exist
     */
    int countOne(DevicePCPInfo devicePCPInfo);
    int countOneByMac(String mac);

    /**
     * get the sales id by mac address
     * @param mac  mac
     * @return sales id
     */
    int selectSalesIdByMac(String mac);

    /**
     * get the device_rent full information by mac address
     * @return all field
     */
    DevicePCPInfo selectOneByMac(String mac);

    /**
     * get all device_rent info
     * @return all device_rent list
     */
    List<DevicePCPInfo> selectAll();

    List<DevicePCPInfo> selectAllByDis(@Param("distributor") String distributor);

    List<DevicePCPInfo> selectBySalesId(int salesId);

    List<DevicePCPInfo> selectRentedBySalesId(int salesId);

    /**
     * insert a new device rent info
     */
    int insertOne(@Param("mac") String mac, @Param("distributor") String distributor);

    int updateDeviceToSpecialSales(DevicePCPInfo devicePCPInfo);

    int bathUpdateDeviceToSpecialSales(@Param("macs") String[] macs, @Param("salesId") int salesId,
                                       @Param("dealerId") int dealerId, @Param("adminId") int adminId);

    int bathUpdateDeviceToChecked(@Param("macs") String[] macs, @Param("salesId") int salesId,
                                  @Param("checkNumber") String checkNumber);

    int updateDeviceToChecked(DevicePCPInfo devicePCPInfo);
    int updateDeviceToRented(String mac);

    int countNoRentedBySalesId(int salesId);
    int countSDCNBySalesId(int salesId);

}
