package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.commission.CommissionMonthlySalesInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.commission.SalesMonthlyCommission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author patrick
 */
public interface CommissionMonthlySalesDao {

    int insertOne(CommissionMonthlySalesInfo commissionMonthlySalesInfo);
    int batchUpdateToCheckedByIds(@Param("ids") String[] ids, @Param("checkNumber") String checkNumber);

    List<SalesMonthlyCommission> selectAllSalesCommissionByYearAndMonth(YearOrMonthInfo yearOrMonthInfo);
    List<CommissionMonthlySalesInfo> selectBySalesIdAndYearMonth(@Param("salesId")int salesId,
                                                                 @Param("start")String start,
                                                                 @Param("end")String end);
}
