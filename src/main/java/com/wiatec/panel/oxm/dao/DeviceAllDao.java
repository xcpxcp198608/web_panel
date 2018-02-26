package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DeviceAllDao {

    /**
     * select DeviceAllInfo list from special id (exclude start id)
     * @param start start id
     * @return list of DeviceAllInfo
     */
    List<DeviceAllInfo> selectAll(int start);
    List<DeviceAllInfo> selectAllByDis(@Param("distributor") String distributor, @Param("start") int start);

    DeviceAllInfo selectOneByMac(String mac);

    int countOneByMac(String mac);

    int insertOne(@Param("mac") String mac, @Param("distributor") String distributor);

    int bathInsert(@Param("macs") String[] macs, @Param("distributor") String distributor);

    int updateStatusByMac(@Param("mac") String mac, @Param("status") int status);
}
