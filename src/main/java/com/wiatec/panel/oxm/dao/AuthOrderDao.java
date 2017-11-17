package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.chart.*;

import java.util.List;
import java.util.Map;

public interface AuthOrderDao {

    int countOneByPaymentId(AuthOrderInfo authOrderInfo);
    void insertOne(AuthOrderInfo authOrderInfo);
    void updateOneByPaymentId(AuthOrderInfo authOrderInfo);

    AuthOrderInfo selectOne(AuthOrderInfo authOrderInfo);
    List<AuthOrderInfo> selectAll();

    List<SalesVolumeOfMonthInfo> selectSaleVolumeEveryMonth(Map<String, String> dateMap);

    List<AuthOrderInfo> selectByTradingTime(String day);
    List<TopVolumeInfo> selectTopVolume(int top);
    List<TopAmountInfo> selectTopAmount(int top);


    List<AuthOrderInfo> selectBySalesId(int salesId);

    List<SalesCommissionOfDaysInfo> getCommissionOfDayBySales(Map<String, String> map);
    List<SalesCommissionOfMonthInfo> getCommissionOfMonthBySales(Map<String, String> map);
    List<AllSalesMonthCommissionInfo> selectAllSalesCommissionByMonth(Map<String, String> map);


}
