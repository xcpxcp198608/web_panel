package com.wiatec.panel.web;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.sales.SalesCommissionOfMonthInfo;
import com.wiatec.panel.service.auth.AuthSalesService;
import com.wiatec.panel.common.result.ResultInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * go to create_user page
     * @return sales create user page
     */
    @GetMapping(value = "/create")
    public String createUser(Model model){
        return authSalesService.createUsers(model);
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
     * get every day sales volume in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        SalesVolumeInDayOfMonthInfo list
     */
    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public List<SalesVolumeInDayOfMonthInfo> getSalesVolumeEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authSalesService.selectSalesVolumeEveryDayInMonth(request, year, month);
    }


    /**
     * get every day dealer commission in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        DealerCommissionOfDaysInfo list
     */
    @GetMapping(value = "/chart/commission/{year}/{month}")
    @ResponseBody
    public List<SalesCommissionOfDaysInfo> getSalesCommissionEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authSalesService.selectSalesCommissionEveryDayInMonth(request, year, month);
    }

    /**
     * get every month dealer commission in specify year and month
     * @param year    specify year
     * @return        DealerCommissionOfMonthInfo list
     */
    @GetMapping(value = "/chart/commission/{year}")
    @ResponseBody
    public List<SalesCommissionOfMonthInfo> getSalesCommissionEveryMonthInYear(HttpServletRequest request, @PathVariable int year){
        return authSalesService.selectSalesCommissionEveryMonthInYear(request, year);
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
