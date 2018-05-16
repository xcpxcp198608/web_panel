package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.log.LogUserLevelInfo;
import com.wiatec.panel.oxm.pojo.log.LogUserOperationInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogUserOperationDao {



    int insertOne(@Param("userId") int userId,
                  @Param("type") int type,
                  @Param("description") String description);

    List<LogUserOperationInfo> selectAllByUserId(int userId);
}
