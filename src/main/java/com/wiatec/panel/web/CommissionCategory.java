package com.wiatec.panel.web;

import com.wiatec.panel.oxm.pojo.commission.CommissionCategoryInfo;
import com.wiatec.panel.service.auth.CommissionCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/category")
public class CommissionCategory {

    @Resource
    private CommissionCategoryService commissionCategoryService;

    @GetMapping(value = "/")
    @ResponseBody
    public List<CommissionCategoryInfo> get(int distributor){
        return commissionCategoryService.selectAllByDistributor(distributor);
    }

    @GetMapping(value = "/{category}")
    @ResponseBody
    public CommissionCategoryInfo getOne(@PathVariable String category){
        return commissionCategoryService.selectOne(category);
    }

}
