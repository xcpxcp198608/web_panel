package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.paypal.NotifyInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotifyDao {
    void insertOne(NotifyInfo notifyInfo);
    void insertBath(List<NotifyInfo> notifyInfoList);
}
