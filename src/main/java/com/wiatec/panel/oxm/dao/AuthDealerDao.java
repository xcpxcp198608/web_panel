package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthDealerDao {

    int countOne(AuthDealerInfo authDealerInfo);
    AuthDealerInfo selectOne(AuthDealerInfo authDealerInfo);

}
