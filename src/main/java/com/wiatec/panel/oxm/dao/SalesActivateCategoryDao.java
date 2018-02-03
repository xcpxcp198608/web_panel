package com.wiatec.panel.oxm.dao;

import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesGoldCategoryInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author patrick
 */
@Repository
public interface SalesActivateCategoryDao {

    /**
     * select all category
     * @return list of SalesActivateCategoryInfo
     */
    List<SalesActivateCategoryInfo> selectAllWithLimit(int limit);

    /**
     * select SalesActivateCategoryInfo by special category
     * @param category category
     * @return  SalesActivateCategoryInfo
     */
    SalesActivateCategoryInfo selectOneByCategory(String category);
}
