package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceInfo;
import com.wiatec.panel.oxm.pojo.DeviceRentInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface DeviceDao {


    List<DeviceInfo> selectAllWithEnable(int enable);
    DeviceInfo selectOneByMac(String mac);
    int countOne(String mac);

    int insertOne(DeviceInfo deviceInfo);
    int bathInsert(@Param("macs") String[] macs);

    int updateOneToEnable(@Param("mac") String mac, @Param("enableName") String enableName);
}
