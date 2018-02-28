package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionSalesDepositInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface AuthorizeTransactionSalesDepositDao {

    /**
     * add a sales deposit transaction recorder
     * @param authorizeTransactionSalesDepositInfo authorizeTransactionSalesDepositInfo
     */
    void insertOne(AuthorizeTransactionSalesDepositInfo authorizeTransactionSalesDepositInfo);

    /**
     * select all sales deposit transaction recorders
     * @return list of AuthorizeTransactionSalesDepositInfo
     */
    List<AuthorizeTransactionSalesDepositInfo> selectAll();

}
