package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.UpgradeDao;
import com.wiatec.panel.oxm.pojo.UpgradeInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UpgradeService {

    @Resource
    private UpgradeDao upgradeDao;

    public UpgradeInfo selectOne(){
        return upgradeDao.selectOne();
    }
}
