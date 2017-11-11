package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.TopAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.TopVolumeInfo;
import com.wiatec.panel.service.auth.AuthAdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    @PutMapping(value = "/sale/create")
    @ResponseBody
    public ResultInfo<AuthSalesInfo> getSales(HttpServletRequest request, @RequestBody AuthSalesInfo authSalesInfo){
        return authAdminService.createSales(request, authSalesInfo);
    }

    @PutMapping(value = "/sale/update")
    @ResponseBody
    public ResultInfo updateSales(HttpServletRequest request, @RequestBody AuthSalesInfo authSalesInfo){
        return authAdminService.updateSalesPassword(request, authSalesInfo);
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
     * order data by month
     * @param request
     * @param year
     * @param month
     * @return
     */
    @RequestMapping(value = "/orders/{year}/{month}")
    @ResponseBody
    public ResultInfo<AuthOrderInfo> getOrdersByMonth(HttpServletRequest request, @PathVariable(value = "year") int year,
                                                         @PathVariable(value = "month") int month){
        return authAdminService.getOrdersByMonth(request, year, month);
    }

    /**
     * order data by year
     * @param request
     * @param year
     * @return
     */
    @PostMapping(value = "/orders/{year}")
    @ResponseBody
    public ResultInfo<AuthOrderInfo> getOrdersByYear(HttpServletRequest request,
                                                     @PathVariable(value = "year") String year){
        return authAdminService.getOrdersByYear(request, year);
    }

    @PostMapping(value = "/orders/volume/{top}")
    @ResponseBody
    public List<TopVolumeInfo> getTopVolume(HttpServletRequest request, @PathVariable(value = "top") int top){
        return authAdminService.getTopVolume(request, top);
    }

    @PostMapping(value = "/orders/amount/{top}")
    @ResponseBody
    public List<TopAmountInfo> getTopAmount(HttpServletRequest request, @PathVariable(value = "top") int top){
        return authAdminService.getTopAmount(request, top);
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
