package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogMlmUserDailyDao {

    int batchInsert(@Param("authRegisterUserInfoList") List<AuthRegisterUserInfo> authRegisterUserInfoList);
}
