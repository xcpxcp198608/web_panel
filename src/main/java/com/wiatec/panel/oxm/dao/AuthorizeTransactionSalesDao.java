package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.authorize.AuthorizeTransactionSalesInfo;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.AllDealerMonthCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.AllSalesMonthCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopAmountInfo;
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
public interface AuthorizeTransactionSalesDao {

    /**
     * add a sales transaction recorder
     * @param authorizeTransactionSalesInfo authorizeTransactionSalesInfo
     */
    void insertOne(AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo);

    /**
     * select all sales transaction recorders
     * @return list of AuthorizeTransactionSalesInfo
     */
    List<AuthorizeTransactionSalesInfo> selectAll();

}
