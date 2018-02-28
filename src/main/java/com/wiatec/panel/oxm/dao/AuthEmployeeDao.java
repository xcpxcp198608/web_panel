package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.AuthEmployeeInfo;
import org.springframework.stereotype.Repository;

/**
 * @author patrick
 */
@Repository
public interface AuthEmployeeDao {

    AuthEmployeeInfo selectOneByCode(String code);

}
