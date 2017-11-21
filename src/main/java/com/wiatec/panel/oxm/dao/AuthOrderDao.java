package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;

import java.util.List;
import java.util.Map;

public interface AuthOrderDao {

    int countOneByPaymentId(AuthOrderInfo authOrderInfo);
    void insertOne(AuthOrderInfo authOrderInfo);
    void updateOneByPaymentId(AuthOrderInfo authOrderInfo);

    AuthOrderInfo selectOne(AuthOrderInfo authOrderInfo);
    List<AuthOrderInfo> selectAll();
    List<AuthOrderInfo> selectBySalesId(int salesId);
    List<AuthOrderInfo> selectByTradingTime(String day);


    List<SalesDayVolumeInMonthInfo> countSaleVolumeEveryDayInMonth(YearOrMonthInfo yearOrMonthInfo);
    List<TopVolumeInfo> selectTopVolume(int top);
    List<TopAmountInfo> selectTopAmount(int top);
    List<AllSalesMonthCommissionInfo> selectAllSalesCommissionByMonth(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(YearOrMonthInfo yearOrMonthInfo);


    List<SalesCommissionOfDaysInfo> getCommissionOfDayBySales(YearOrMonthInfo yearOrMonthInfo);
    List<SalesCommissionOfMonthInfo> getCommissionOfMonthBySales(YearOrMonthInfo yearOrMonthInfo);

}
