package com.wiatec.panel.authorize;

import com.wiatec.panel.oxm.dao.AuthRentUserDao;
import com.wiatec.panel.oxm.dao.AuthorizeTransactionDao;
import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class AutoPayTask implements Runnable {

    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private AuthorizeTransactionDao authorizeTransactionDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    @Override
    public void run() {

    }
}
