package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.YearOrMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.TopVolumeInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.VolumeDistributionInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthRentUserDao {

    int countOneByMac(AuthRentUserInfo authRentUserInfo);
    int countOneByEmail(AuthRentUserInfo authRentUserInfo);
    String selectStatusByMac(AuthRentUserInfo authRentUserInfo);

    void insertOne(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByMac(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectOneByClientKey(String key);
    void updateUserStatus(AuthRentUserInfo authRentUserInfo);
    void updateUserCategory(AuthRentUserInfo authRentUserInfo);

    List<AuthRentUserInfo> selectBySalesId(int salesId);
    List<AuthRentUserInfo> selectByDealerId(int dealerId);
    List<AuthRentUserInfo> selectAll();

    //chart
    List<VolumeDistributionInfo> getDistributionData();
    List<SalesVolumeInDayOfMonthInfo> countSalesVolumeByDayOfMonth(YearOrMonthInfo yearOrMonthInfo);
    List<SalesVolumeInDayOfMonthInfo> countSalesUnderDealerVolumeByDayOfMonth(YearOrMonthInfo yearOrMonthInfo);
    List<TopVolumeInfo> selectTopVolume(int top);
    List<TopVolumeInfo> selectTopVolumeByDealer(AuthDealerInfo authDealerInfo);

    //api
    int countOneByKeyAndMac(AuthRentUserInfo authRentUserInfo);
    AuthRentUserInfo selectByClientKey(String key);
    void updateLocation(AuthRentUserInfo authRentUserInfo);

}
