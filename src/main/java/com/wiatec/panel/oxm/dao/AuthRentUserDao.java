package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;

import java.util.List;

public interface AuthRentUserDao {

    int countOneByMac(AuthRentUserInfo authRentUserInfo);
    int countOneByEmail(AuthRentUserInfo authRentUserInfo);
    String selectStatusByMac(AuthRentUserInfo authRentUserInfo);

    void insertOne(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByMac(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByClientKey(String key);

    List<AuthRentUserInfo> selectBySalesId(int salesId);
    List<AuthRentUserInfo> selectAll();

    void updateStatusToActivate(String clientKey);

    //api
    int countOneByKeyAndMac(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectByClientKey(String key);
    void updateLocation(AuthRentUserInfo authRentUserInfo);

}
