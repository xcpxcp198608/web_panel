package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesDaysCommissionInfo;
import com.wiatec.panel.oxm.pojo.chart.SalesMonthCommissionInfo;
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
    @RequestMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authSalesService.home(request, model);
    }

    /**
     * response month commission chart
     * @param request
     * @param year   target year from ajax
     * @param month  target month from ajax
     * @return
     */
    @PostMapping(value = "/commission/{year}/{month}")
    @ResponseBody
    public ResultInfo<SalesDaysCommissionInfo> getCommissionByMonth(HttpServletRequest request,
                                                                    @PathVariable(value = "year") int year,
                                                                    @PathVariable(value = "month") int month){
        return authSalesService.getCommissionByMonth(request, year, month);
    }

    /**
     * response year commission chart
     * @param request
     * @param year target year from ajax
     * @return
     */
    @PostMapping(value = "/commission/{year}")
    @ResponseBody
    public ResultInfo<SalesMonthCommissionInfo> getCommissionByYear(HttpServletRequest request,
                                                                    @PathVariable(value = "year") int year){
        return authSalesService.getCommissionByYear(request, year);
    }

    /**
     * user page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/users")
    public String users(HttpServletRequest request, Model model){
        return authSalesService.users(request, model);
    }

    /**
     * go to create_user page to fill in information
     * @return
     */
    @RequestMapping(value = "/go")
    public String goCreate(){
        return "sales/create_user";
    }

    /**
     * create user, store user information in table of auth_rent_user
     * @param request
     * @param authRentUserInfo user information from form submit
     * @return
     */
    @PutMapping(value = "/create")
    @ResponseBody
    public ResultInfo<String> create(HttpServletRequest request, @RequestBody AuthRentUserInfo authRentUserInfo){
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
        return authSalesService.activate(request, model, clientKey);
    }

}
