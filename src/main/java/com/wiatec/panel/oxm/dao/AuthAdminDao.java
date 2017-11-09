package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;

public interface AuthAdminDao {

    int countOne(AuthAdminInfo authAdminInfo);
    void selectOne(AuthAdminInfo authAdminInfo);
}
