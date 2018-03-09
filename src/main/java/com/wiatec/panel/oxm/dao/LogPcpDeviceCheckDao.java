package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.log.LogPcpCashActivateInfo;
import com.wiatec.panel.oxm.pojo.log.LogPcpDeviceCheckInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface LogPcpDeviceCheckDao {

    int insertOne(LogPcpDeviceCheckInfo logPcpDeviceCheckInfo);
    int batchInsert(@Param("macs") String[] macs, @Param("executor") String executor);
}
