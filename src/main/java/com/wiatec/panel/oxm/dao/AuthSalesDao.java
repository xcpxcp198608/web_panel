package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;

import java.util.List;

public interface AuthSalesDao {


    int countUsername(AuthSalesInfo authSalesInfo);
    int countSSN(AuthSalesInfo authSalesInfo);
    int countEmail(AuthSalesInfo authSalesInfo);
    void insertOne(AuthSalesInfo authSalesInfo);
    void updatePassword(AuthSalesInfo authSalesInfo);

    int countOne(AuthSalesInfo authSalesInfo);
    String selectPermission(AuthSalesInfo authSalesInfo);
    AuthSalesInfo selectOne(AuthSalesInfo authSalesInfo);
    List<AuthSalesInfo> selectAll();

}
