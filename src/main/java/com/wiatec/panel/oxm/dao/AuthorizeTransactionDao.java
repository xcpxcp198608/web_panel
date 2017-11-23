package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizePayInfo;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorizeTransactionDao {

    void insertOne(AuthorizePayInfo authorizePayInfo);
}
