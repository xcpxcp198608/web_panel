package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.paypal.NotifyInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifyDao {
    void insertOne(NotifyInfo notifyInfo);
}
