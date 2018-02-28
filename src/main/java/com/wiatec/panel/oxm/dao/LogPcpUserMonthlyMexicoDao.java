package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface LogPcpUserMonthlyMexicoDao {

    int insertOne(AuthRentUserInfo authRentUserInfo);
    int batchInsert(@Param("authRentUserInfoList") List<AuthRentUserInfo> authRentUserInfoList);
    int countOneByMac(String mac);
    List<AuthRentUserInfo> selectByMonth(YearOrMonthInfo yearOrMonthInfo);

}
