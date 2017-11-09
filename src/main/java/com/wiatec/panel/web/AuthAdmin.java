package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.chart.DaysInfo;
import com.wiatec.panel.service.auth.AuthAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/admin")
public class AuthAdmin {

    @Resource
    private AuthAdminService authAdminService;

    /**
     * home page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String home(HttpServletRequest request, Model model){


        return authAdminService.home(request, model);
    }

    @PostMapping(value = "/orders/{year}/{month}/{days}")
    @ResponseBody
    public ResultInfo<DaysInfo> getAllOrdersByDays(HttpServletRequest request,
                                                   @PathVariable(value = "year") int year,
                                                   @PathVariable(value = "month") int month,
                                                   @PathVariable(value = "days") int days){
        return authAdminService.getAllOrders(request, year, month, days);
    }

    /**
     * sales page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/sales")
    public String getSales(HttpServletRequest request, Model model){
        return authAdminService.sales(request, model);
    }



    /**
     * users page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/users")
    public String getUsers(HttpServletRequest request, Model model){
        return authAdminService.users(request, model, 0);
    }

    /**
     * get users under sales by sales id
     * @param request
     * @param model
     * @param salesId
     * @return
     */
    @RequestMapping(value = "/users/{salesId}")
    public String getUsersBySale(HttpServletRequest request, Model model,
                        @PathVariable(value="salesId")int salesId){
        return authAdminService.users(request, model, salesId);
    }

    @PostMapping(value = "/user/{key}")
    @ResponseBody
    public AuthRentUserInfo getUserByKey(HttpServletRequest request, @PathVariable(value="key")String key){
        return authAdminService.getUserByKey(request, key);
    }

    /**
     * commission page
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/commission")
    public String getCommission(HttpServletRequest request, Model model){
        return authAdminService.commission(request, model);
    }

    /**
     * chart data on commission page
     * @param request
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/commission/{year}/{month}")
    @ResponseBody
    public ResultInfo<AuthOrderInfo> getCommissionOrders(HttpServletRequest request, @PathVariable(value = "year") int year,
                                                         @PathVariable(value = "month") int month){
        return authAdminService.getCommissionOrders(request, year, month);
    }

    @PostMapping(value = "/orders/{year}")
    @ResponseBody
    public ResultInfo<AuthOrderInfo> getOrdersByYear(HttpServletRequest request,
                                                     @PathVariable(value = "year") String year){
        return authAdminService.getOrdersByYear(request, year);
    }

    /**
     * sign out
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/signout")
    public String signOut(HttpServletRequest request, Model model){
        return authAdminService.signOut(request);
    }

}
