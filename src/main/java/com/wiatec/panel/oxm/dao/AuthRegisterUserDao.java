package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.LevelDistributionInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.MonthVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.manager.YearVolumeInfo;
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
    void saveOneUser(AuthRegisterUserInfo authRegisterUserInfo);
    void updateEmailStatus(AuthRegisterUserInfo authRegisterUserInfo);
    int countByUsernameAndPassword(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByToken(String token);
    AuthRegisterUserInfo selectOneByUsername(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByUsernameAndMac(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByUsernameAndEmail(AuthRegisterUserInfo authRegisterUserInfo);
    void updateLocation(AuthRegisterUserInfo authRegisterUserInfo);
    void updateToken(AuthRegisterUserInfo authRegisterUserInfo);
    void updatePassword(AuthRegisterUserInfo authRegisterUserInfo);


    List<AuthRegisterUserInfo> selectAll(int start);
    List<AuthRegisterUserInfo> selectAllExpiresUsers(int start);
    void updateEmailStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    void updateStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    int deleteOneById(AuthRegisterUserInfo authRegisterUserInfo);
    String selectExpiresTimeById(int id);
    AuthRegisterUserInfo selectOneById(int id);
    void updateLevelById(AuthRegisterUserInfo authRegisterUserInfo);

    LevelDistributionInfo selectAllLevelDistribution();
    List<VolumeDistributionInfo> getDistributionData();

    //chart

    List<Integer> selectLevelOfYear(YearOrMonthInfo yearOrMonthInfo);
    List<YearVolumeInfo> selectVolumeOfYear(YearOrMonthInfo yearOrMonthInfo);
    List<MonthVolumeInfo> selectVolumeOfMonth(YearOrMonthInfo yearOrMonthInfo);

}
