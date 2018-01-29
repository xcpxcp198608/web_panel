package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.SalesGoldCategoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface SalesGoldCategoryDao {

    /**
     * select all category
     * @return list of SalesGoldCategoryInfo
     */
    List<SalesGoldCategoryInfo> selectAll();

    /**
     * select SalesGoldCategoryInfo by special category
     * @param category category
     * @return  SalesGoldCategoryInfo
     */
    SalesGoldCategoryInfo selectOneByCategory(String category);
}
