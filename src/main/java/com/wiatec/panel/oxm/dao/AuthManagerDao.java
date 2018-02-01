package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthManagerDao {

    int countOne(AuthManagerInfo authManagerInfo);
    AuthManagerInfo selectOneByUsername(String username);
}
