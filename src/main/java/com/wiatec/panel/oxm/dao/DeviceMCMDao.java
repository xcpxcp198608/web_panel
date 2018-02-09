package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import com.wiatec.panel.oxm.pojo.DeviceMCMInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DeviceMCMDao {

    /**
     * select DeviceAllInfo list from special id (exclude start id)
     * @param start start id
     * @return list of DeviceAllInfo
     */
    List<DeviceMCMInfo> selectAll(int start);

    DeviceMCMInfo selectOneByMac(String mac);

    int countOneByMac(String mac);

    int insertOne(@Param("mac") String mac, @Param("username") String username, @Param("checkInUser") String checkInUser);

}
