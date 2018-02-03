package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.authorize.AuthorizeTransactionSalesDepositInfo;
import com.wiatec.panel.authorize.AuthorizeTransactionSalesMemberInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface AuthorizeTransactionSalesMemberDao {

    /**
     * add a sales member transaction recorder
     * @param authorizeTransactionSalesMemberInfo authorizeTransactionSalesMemberInfo
     */
    void insertOne(AuthorizeTransactionSalesMemberInfo authorizeTransactionSalesMemberInfo);

    /**
     * select all sales member transaction recorders
     * @return list of authorizeTransactionSalesMemberInfo
     */
    List<AuthorizeTransactionSalesMemberInfo> selectAll();

}
