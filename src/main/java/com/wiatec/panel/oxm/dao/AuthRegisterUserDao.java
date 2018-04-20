package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.MonthVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.YearVolumeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface AuthRegisterUserDao {

    int countByUsername(AuthRegisterUserInfo authRegisterUserInfo);
    int countByEmail(AuthRegisterUserInfo authRegisterUserInfo);
    int countByMac(AuthRegisterUserInfo authRegisterUserInfo);
    int saveOneUser(AuthRegisterUserInfo authRegisterUserInfo);
    int updateEmailStatus(AuthRegisterUserInfo authRegisterUserInfo);
    int countByUsernameAndPassword(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByToken(String token);
    AuthRegisterUserInfo selectOneByUsername(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByUsernameAndMac(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByUsernameAndEmail(AuthRegisterUserInfo authRegisterUserInfo);
    int updateLocation(AuthRegisterUserInfo authRegisterUserInfo);
    int updateToken(AuthRegisterUserInfo authRegisterUserInfo);
    int updatePassword(AuthRegisterUserInfo authRegisterUserInfo);
    int updateTokenAndMac(AuthRegisterUserInfo authRegisterUserInfo);


    List<AuthRegisterUserInfo> selectAll(int start);

    int countAll(int start);
    List<AuthRegisterUserInfo> selectByPage(@Param("start") int start, @Param("from")int from,
                                            @Param("to")int to);
    List<AuthRegisterUserInfo> selectAllExpiresUsers(int start);
    List<AuthRegisterUserInfo> selectExportUsers(@Param("macs") String[] macs);

    int updateEmailStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    int updateStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    int deleteOneById(AuthRegisterUserInfo authRegisterUserInfo);
    String selectExpiresTimeById(int id);
    AuthRegisterUserInfo selectOneById(int id);
    int updateLevelById(AuthRegisterUserInfo authRegisterUserInfo);

    LevelDistributionInfo selectAllLevelDistribution();
    List<VolumeDistributionInfo> getDistributionData();


    //chart

    List<Integer> selectLevelOfYear(YearOrMonthInfo yearOrMonthInfo);
    List<YearVolumeInfo> selectVolumeOfYear(YearOrMonthInfo yearOrMonthInfo);
    List<MonthVolumeInfo> selectVolumeOfMonth(YearOrMonthInfo yearOrMonthInfo);

}
