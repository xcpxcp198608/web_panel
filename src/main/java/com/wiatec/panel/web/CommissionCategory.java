package com.wiatec.panel.web;

import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.service.auth.CommissionCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CommissionCategory {

    @Resource
    private CommissionCategoryService commissionCategoryService;

    @GetMapping(value = "/")
    @ResponseBody
    public List<CommissionCategoryInfo> get(){
        return commissionCategoryService.selectAll();
    }

    @PostMapping(value = "/{category}")
    @ResponseBody
    public CommissionCategoryInfo getOne(@PathVariable(value = "category") String category){
        return commissionCategoryService.selectOne(category);
    }

}
