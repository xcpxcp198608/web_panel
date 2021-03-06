package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthSalesDao {

    int countOne(AuthSalesInfo authSalesInfo);
    int countUsername(AuthSalesInfo authSalesInfo);
    int countSSN(AuthSalesInfo authSalesInfo);
    int countEmail(AuthSalesInfo authSalesInfo);
    void insertOne(AuthSalesInfo authSalesInfo);
    void updatePassword(AuthSalesInfo authSalesInfo);

    AuthSalesInfo selectOneByUsername(AuthSalesInfo authSalesInfo);
    AuthSalesInfo selectOneById(int id);
    List<AuthSalesInfo> selectAll();
    List<AuthSalesInfo> selectSales(int leaderId);

    int updateGoldById(int id);
    int updateNoGoldById(int id);
    int updateSDCNById(int id);
    int updateNoSDCNById(int id);


}
