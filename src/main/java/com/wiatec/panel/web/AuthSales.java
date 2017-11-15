package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesDaysCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesMonthCommissionInfo;
import com.wiatec.panel.paypal.PayInfo;
import com.wiatec.panel.service.auth.AuthSalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/sales")
public class AuthSales {

    @Resource
    private AuthSalesService authSalesService;

    /**
     * home page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authSalesService.home(request, model);
    }

    @PostMapping(value = "/commission/{year}/{month}")
    @ResponseBody
    public ResultInfo<SalesDaysCommissionInfo> getCommissionByMonth(HttpServletRequest request,
                                                                    @PathVariable(value = "year") int year,
                                                                    @PathVariable(value = "month") int month){
        return authSalesService.getCommissionByMonth(request, year, month);
    }

    @PostMapping(value = "/commission/{year}")
    @ResponseBody
    public ResultInfo<SalesMonthCommissionInfo> getCommissionByYear(HttpServletRequest request,
                                                                    @PathVariable(value = "year") int year){
        return authSalesService.getCommissionByYear(request, year);
    }


    @RequestMapping(value = "/users")
    public String users(HttpServletRequest request, Model model){
        return authSalesService.users(request, model);
    }

    @RequestMapping(value = "/go")
    public String goCreate(){
        return "sales/create_user";
    }

    @PutMapping(value = "/create")
    @ResponseBody
    public ResultInfo<PayInfo> create(HttpServletRequest request, @RequestBody AuthRentUserInfo authRentUserInfo){
        return authSalesService.createUser(request, authRentUserInfo);
    }

    @RequestMapping(value = "/pay")
    public String pay(PayInfo payInfo, Model model){
        return "sales/payment";
    }

}
