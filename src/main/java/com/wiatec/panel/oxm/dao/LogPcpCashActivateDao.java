package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.log.LogPcpCashActivateInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogPcpCashActivateDao {

    int insertOne(LogPcpCashActivateInfo logPcpCashActivateInfo);
}
