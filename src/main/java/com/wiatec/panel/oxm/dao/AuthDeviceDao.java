package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthAdminInfo;
import com.wiatec.panel.oxm.pojo.AuthDeviceInfo;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthDeviceDao {

    int countOne(AuthDeviceInfo authDeviceInfo);
    AuthDeviceInfo selectOne(AuthDeviceInfo authDeviceInfo);
    AuthDeviceInfo selectOneByUsername(String username);

}
