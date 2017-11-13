package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.oxm.pojo.UpgradeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommissionCategoryDao {

    List<CommissionCategoryInfo> selectAll();
    CommissionCategoryInfo selectOne(String category);
}
