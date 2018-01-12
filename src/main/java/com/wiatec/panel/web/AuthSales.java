package com.wiatec.panel.web;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.service.auth.AuthSalesService;
import com.wiatec.panel.common.result.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * manage information after sales sign in
 * @author patrick
 */
@Controller
@RequestMapping(value = "/sales")
public class AuthSales {

    @Resource
    private AuthSalesService authSalesService;

    /**
     * home page
     * @param request HttpServletRequest
     * @param model model
     * @return sales home page
     */
    @GetMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authSalesService.home(request, model);
    }

    /**
     * user page
     * @param request HttpServletRequest
     * @param model model
     * @return sales users page
     */
    @GetMapping(value = "/users")
    public String users(HttpServletRequest request, Model model){
        return authSalesService.users(request, model);
    }

    /**
     * get user details
     * @param key    client key
     * @return       {@link AuthRentUserInfo}
     */
    @GetMapping(value = "/user/{key}")
    @ResponseBody
    public AuthRentUserInfo getUserByKey(@PathVariable String key) {
        return authSalesService.getUserByKey(key);
    }

    /**
     * go to create_user page to fill in information
     * @return sales create user page
     */
    @GetMapping(value = "/go")
    public String goCreate(){
        return "sales/create_user";
    }

    /**
     * create user, store user information in table of auth_rent_user
     * @param request HttpServletRequest
     * @param authRentUserInfo user information from form submit
     * @return ResultInfo
     */
    @PostMapping(value = "/create/{paymentMethod}")
    @ResponseBody
    public ResultInfo create(HttpServletRequest request, AuthRentUserInfo authRentUserInfo, @PathVariable int paymentMethod){
        return authSalesService.createUser(request, authRentUserInfo, paymentMethod);
    }

    /**
     * response year commission chart
     * @param year target year from ajax
     * @return ResultInfo
     */
    @GetMapping(value = "/commission/{year}")
    @ResponseBody
    public ResultInfo getCommissionByYear(HttpServletRequest request, @PathVariable int year){
        return authSalesService.getCommissionByYear(request, year);
    }

    /**
     * response month commission chart
     * @param year   target year from ajax
     * @param month  target month from ajax
     * @return ResultInfo
     */
    @GetMapping(value = "/commission/{year}/{month}")
    @ResponseBody
    public ResultInfo getCommissionByMonth(HttpServletRequest request,
                                                                      @PathVariable int year, @PathVariable int month){
        return authSalesService.getCommissionByMonth(request, year, month);
    }



}
