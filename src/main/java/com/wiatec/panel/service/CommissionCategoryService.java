package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommissionCategoryService {

    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    @Transactional(readOnly = true)
    public List<CommissionCategoryInfo> selectAll(){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        return commissionCategoryInfoList;
    }

    @Transactional(readOnly = true)
    public CommissionCategoryInfo selectOne(String category){
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(category);
        commissionCategoryInfo.setPrice();
        return commissionCategoryInfo;
    }
}
