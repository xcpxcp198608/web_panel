package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthManagerInfo;
import com.wiatec.panel.oxm.pojo.AuthMexicoInfo;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface AuthMexicoDao {

    int countOne(AuthMexicoInfo authMexicoInfo);
    AuthMexicoInfo selectOne(AuthMexicoInfo authMexicoInfo);
    AuthMexicoInfo selectOneByUsername(String username);
}
