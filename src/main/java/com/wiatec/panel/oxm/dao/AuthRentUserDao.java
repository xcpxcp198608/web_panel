package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;

import java.util.List;

public interface AuthRentUserDao {

    int countOneByMac(AuthRentUserInfo authRentUserInfo);
    int countOneByEmail(AuthRentUserInfo authRentUserInfo);




    void insertOne(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByMac(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByClientKey(String key);

    List<AuthRentUserInfo> selectOneBySalesId(int salesId);
    List<AuthRentUserInfo> selectAll();
}
