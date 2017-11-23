package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthUserDao {

    int countByUsername(AuthUserInfo authUserInfo);
    int countByEmail(AuthUserInfo authUserInfo);
    int countByMac(AuthUserInfo authUserInfo);
    void saveOneUser(AuthUserInfo authUserInfo);

    int countByToken(AuthUserInfo authUserInfo);
    void updateEmailStatus(AuthUserInfo authUserInfo);


    int countByUsernameAndPassword(AuthUserInfo authUserInfo);
    void updateLocation(AuthUserInfo authUserInfo);
}
