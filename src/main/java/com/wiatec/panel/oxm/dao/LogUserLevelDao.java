package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.DeviceAllInfo;
import com.wiatec.panel.oxm.pojo.log.LogUserLevelInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogUserLevelDao {

    List<LogUserLevelInfo> selectAll();
    int insertOne(LogUserLevelInfo logUserLevelInfo);
}
