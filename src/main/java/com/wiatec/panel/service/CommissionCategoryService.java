package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionCategoryService {

    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    public List<CommissionCategoryInfo> selectAll(){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao.selectAll();
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        return commissionCategoryInfoList;
    }
}
