package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionInfo;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizeTransactionDao {

    void insertOne(AuthorizeTransactionInfo authorizeTransactionInfo);
    List<AuthorizeTransactionInfo> selectAll();

    List<AuthorizeTransactionInfo> selectBySalesId(int salesId);

    //chart of admin
    List<TopAmountInfo> selectTopAmount(int top);
    List<TopAmountInfo> selectTopAmountByDealer(AuthDealerInfo authDealerInfo);
    List<AllSalesMonthCommissionInfo> selectAllSalesCommissionByMonth(YearOrMonthInfo yearOrMonthInfo);
    List<AllSalesMonthCommissionInfo> selectSalesCommissionByMonthAndDealer(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(YearOrMonthInfo yearOrMonthInfo);

    //chart of sales
    List<SalesCommissionOfDaysInfo> getCommissionOfDayBySales(YearOrMonthInfo yearOrMonthInfo);
    List<SalesCommissionOfMonthInfo> getCommissionOfMonthBySales(YearOrMonthInfo yearOrMonthInfo);

    //check is already check out on this month
    int countByKeyAndDate(AuthorizeTransactionInfo authorizeTransactionInfo);
}
