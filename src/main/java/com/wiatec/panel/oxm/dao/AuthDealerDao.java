package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthDealerDao {

    List<AuthDealerInfo> selectAll();
    int countOne(AuthDealerInfo authDealerInfo);
    AuthDealerInfo selectOne(AuthDealerInfo authDealerInfo);
    AuthDealerInfo selectOneById(int dealerId);
    int countUsername(AuthDealerInfo authDealerInfo);
    int countSSN(AuthDealerInfo authDealerInfo);
    int countEmail(AuthDealerInfo authDealerInfo);
    void insertOne(AuthDealerInfo authDealerInfo);
    void updatePassword(AuthDealerInfo authDealerInfo);

}
