package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface AuthorizeTransactionRentalDao {

    void insertOne(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo);
    List<AuthorizeTransactionRentalInfo> selectAll();

    List<AuthorizeTransactionRentalInfo> selectBySalesId(int salesId);

    float countTotalCommissionByDealerId(int dealerId);
    float countTotalCommissionBySalesId(int salesId);

    //chart of admin
    List<TopAmountInfo> selectTopAmount(int top);
    List<TopAmountInfo> selectTopAmountByDealer(AuthDealerInfo authDealerInfo);
    List<AllSalesMonthCommissionInfo> selectAllSalesCommissionByMonth(YearOrMonthInfo yearOrMonthInfo);
    List<AllSalesMonthCommissionInfo> selectSalesCommissionByMonthAndDealer(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryMonthInYear(YearOrMonthInfo yearOrMonthInfo);
    List<SalesAmountInfo> selectSaleAmountEveryDayInMonth(YearOrMonthInfo yearOrMonthInfo);
    List<AllDealerMonthCommissionInfo> selectAllDealersCommissionByMonth(YearOrMonthInfo yearOrMonthInfo);

    //chart of sales
    List<SalesCommissionOfDaysInfo> getCommissionOfDayBySales(YearOrMonthInfo yearOrMonthInfo);
    List<SalesCommissionOfMonthInfo> getCommissionOfMonthBySales(YearOrMonthInfo yearOrMonthInfo);

    //chart of dealer
    List<DealerCommissionOfDaysInfo> getCommissionOfDayByDealer(YearOrMonthInfo yearOrMonthInfo);
    List<DealerCommissionOfMonthInfo> getCommissionOfMonthByDealer(YearOrMonthInfo yearOrMonthInfo);

    //check is already check out on this month
    int countByKeyAndDate(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo);
}
