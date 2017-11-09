package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;

import java.util.List;

public interface AuthSalesDao {

    int countOne(AuthSalesInfo authSalesInfo);
    void selectOne(AuthSalesInfo authSalesInfo);
    List<AuthSalesInfo> selectAll();
    void insertOne(AuthSalesInfo authSalesInfo);
}
