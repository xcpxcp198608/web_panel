package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRegisterUserDao {

    int countByUsername(AuthRegisterUserInfo authRegisterUserInfo);
    int countByEmail(AuthRegisterUserInfo authRegisterUserInfo);
    int countByMac(AuthRegisterUserInfo authRegisterUserInfo);
    void saveOneUser(AuthRegisterUserInfo authRegisterUserInfo);
    void updateEmailStatus(AuthRegisterUserInfo authRegisterUserInfo);
    int countByUsernameAndPassword(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByToken(String token);
    AuthRegisterUserInfo selectOneByUsernameAndMac(AuthRegisterUserInfo authRegisterUserInfo);
    AuthRegisterUserInfo selectOneByUsernameAndEmail(AuthRegisterUserInfo authRegisterUserInfo);
    void updateLocation(AuthRegisterUserInfo authRegisterUserInfo);
    void updateToken(AuthRegisterUserInfo authRegisterUserInfo);
    void updatePassword(AuthRegisterUserInfo authRegisterUserInfo);


    List<AuthRegisterUserInfo> selectAll(int start);
    void updateEmailStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    void updateStatusById(AuthRegisterUserInfo authRegisterUserInfo);
    void deleteOneById(AuthRegisterUserInfo authRegisterUserInfo);
    String selectExpiresTimeById(int id);
    AuthRegisterUserInfo selectOneById(int id);
    void updateLevel(AuthRegisterUserInfo authRegisterUserInfo);

    List<Integer> selectLevelOfYear(YearOrMonthInfo yearOrMonthInfo);
}
