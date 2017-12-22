package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.springframework.stereotype.Repository;

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
    void updateLevel(AuthRegisterUserInfo authRegisterUserInfo);
    void updateLocation(AuthRegisterUserInfo authRegisterUserInfo);
    void updateToken(AuthRegisterUserInfo authRegisterUserInfo);
    void updatePassword(AuthRegisterUserInfo authRegisterUserInfo);
}
