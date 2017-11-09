package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.UpgradeInfo;
import org.springframework.stereotype.Repository;

/**
 * data operation interface of update table
 */
@Repository
public interface UpgradeDao {
    //query the first row from the table of update
    UpgradeInfo selectOne();
}
