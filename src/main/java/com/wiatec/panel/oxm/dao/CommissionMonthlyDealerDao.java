package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.commission.CommissionMonthlyDealerInfo;
import com.wiatec.panel.oxm.pojo.commission.CommissionMonthlySalesInfo;
import com.wiatec.panel.oxm.pojo.commission.DealerMonthlyCommission;
import com.wiatec.panel.oxm.pojo.commission.SalesMonthlyCommission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author patrick
 */
public interface CommissionMonthlyDealerDao {

    int insertOne(CommissionMonthlyDealerInfo commissionMonthlyDealerInfo);
    int batchUpdateToCheckedByIds(@Param("ids") String[] ids, @Param("checkNumber") String checkNumber);

    List<DealerMonthlyCommission> selectAllDealerCommissionByYearAndMonth(YearOrMonthInfo yearOrMonthInfo);
    List<CommissionMonthlyDealerInfo> selectByDealerIdAndYearMonth(@Param("dealerId")int salesId,
                                                                 @Param("start")String start,
                                                                 @Param("end")String end);
}
