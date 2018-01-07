package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthUserLogInfo;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthUserLogDao {

    void insertOne(AuthUserLogInfo authUserLogInfo);
}
