package com.wiatec.panel.service.auth;

import com.wiatec.panel.oxm.dao.CommissionCategoryDao;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Service
public class CommissionCategoryService {

    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    public List<CommissionCategoryInfo> selectAllByDistributor(int distributor){
        List<CommissionCategoryInfo> commissionCategoryInfoList = commissionCategoryDao
                .selectAllByDistributor(distributor);
        for(CommissionCategoryInfo commissionCategoryInfo: commissionCategoryInfoList){
            commissionCategoryInfo.setPrice();
        }
        return commissionCategoryInfoList;
    }

    public CommissionCategoryInfo selectOne(String category){
        CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(category);
        commissionCategoryInfo.setPrice();
        commissionCategoryInfo.setFirstPay();
        return commissionCategoryInfo;
    }
}
