package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;

import java.util.List;

public interface AuthOrderDao {

    AuthOrderInfo selectOne(AuthOrderInfo authOrderInfo);
    List<AuthOrderInfo> selectAll();
    List<AuthOrderInfo> selectByTradingTime(String day);
}
