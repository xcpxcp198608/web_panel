package com.wiatec.panel.authorize;

import net.authorize.Environment;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;

public class AuthorizeConfig {

    public static final String API_ID = "7j4kN3XKhjf";
    public static final String TRANSACTION_KEY = "3BkUS926633ndQUW";
    public static final String API_ID_JAMES = "9g4fGXhg7e";
    public static final String TRANSACTION_KEY_JAMES = "2Reu333Lj7CVv788";


    public static void init(){
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(API_ID);
        merchantAuthenticationType.setTransactionKey(TRANSACTION_KEY);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
    }
}
