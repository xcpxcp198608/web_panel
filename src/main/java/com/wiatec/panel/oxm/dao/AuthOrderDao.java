package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.chart.TopAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.TopVolumeInfo;

import java.util.List;

public interface AuthOrderDao {

    AuthOrderInfo selectOne(AuthOrderInfo authOrderInfo);
    List<AuthOrderInfo> selectAll();
    List<AuthOrderInfo> selectByTradingTime(String day);
    List<TopVolumeInfo> selectTopVolume(int top);
    List<TopAmountInfo> selectTopAmount(int top);
}
