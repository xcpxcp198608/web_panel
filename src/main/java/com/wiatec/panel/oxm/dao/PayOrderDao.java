package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.paypal.PayOrderInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface PayOrderDao {
    void insertOne(PayOrderInfo payOrderInfo);
    void updateByInvoice(PayOrderInfo payOrderInfo);
    PayOrderInfo selectOneByInvoice(String invoice);
}
