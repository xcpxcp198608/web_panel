package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import com.wiatec.panel.service.auth.AuthSalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * manage information after sales sign in
 */
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
    @GetMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authSalesService.home(request, model);
    }

    /**
     * user page
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/users")
    public String users(HttpServletRequest request, Model model){
        return authSalesService.users(request, model);
    }

    /**
     * go to create_user page to fill in information
     * @return
     */
    @GetMapping(value = "/go")
    public String goCreate(){
        return "sales/create_user";
    }

    /**
     * create user, store user information in table of auth_rent_user
     * @param request
     * @param authRentUserInfo user information from form submit
     * @return
     */
    @PostMapping(value = "/create")
    @ResponseBody
    public ResultInfo<String> create(HttpServletRequest request, AuthRentUserInfo authRentUserInfo){
        return authSalesService.createUser(request, authRentUserInfo);
    }

    /**
     * go to payment page, activate user after pay
     * @param request
     * @param model
     * @param clientKey user client key
     * @return
     */
    @GetMapping(value = "/activate/{clientKey}")
    public String activate(HttpServletRequest request, Model model,
                           @PathVariable(value = "clientKey") String clientKey){
        return authSalesService.activate(model, clientKey);
    }


    /**
     * response year commission chart
     * @param year target year from ajax
     * @return
     */
    @GetMapping(value = "/commission/{year}")
    @ResponseBody
    public ResultInfo<SalesCommissionOfMonthInfo> getCommissionByYear(HttpServletRequest request, @PathVariable int year){
        return authSalesService.getCommissionByYear(request, year);
    }

    /**
     * response month commission chart
     * @param year   target year from ajax
     * @param month  target month from ajax
     * @return
     */
    @GetMapping(value = "/commission/{year}/{month}")
    @ResponseBody
    public ResultInfo<SalesCommissionOfDaysInfo> getCommissionByMonth(HttpServletRequest request,
                                                                      @PathVariable int year, @PathVariable int month){
        return authSalesService.getCommissionByMonth(request, year, month);
    }



}
