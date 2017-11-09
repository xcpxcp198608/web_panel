package com.wiatec.panel.controller;

import com.wiatec.panel.oxm.pojo.UpgradeInfo;
import com.wiatec.panel.service.UpgradeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Upgrade {

    @Resource
    private UpgradeService upgradeService;

    @RequestMapping(value = "/upgrade")
    public @ResponseBody UpgradeInfo get(){
        return upgradeService.selectOne();
    }

}
