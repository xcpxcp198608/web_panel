package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.commission.CommissionCategoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface CommissionCategoryDao {

    List<CommissionCategoryInfo> selectAllByDistributor(int distributor);
    CommissionCategoryInfo selectOne(String category);
}
