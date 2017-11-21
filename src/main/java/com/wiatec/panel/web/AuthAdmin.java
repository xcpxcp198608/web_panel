package com.wiatec.panel.web;

import com.wiatec.panel.entity.ResultInfo;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
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
    @GetMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authAdminService.home(request, model);
    }


    /**
     * sales page
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/sales")
    public String getSales(HttpServletRequest request, Model model){
        return authAdminService.sales(request, model);
    }



    /**
     * create user
     * @param request
     * @param authSalesInfo
     * @return
     */
    @PostMapping(value = "/sale/create")
    @ResponseBody
    public ResultInfo<AuthSalesInfo> getSales(HttpServletRequest request, @RequestBody AuthSalesInfo authSalesInfo){
        return authAdminService.createSales(request, authSalesInfo);
    }

    /**
     * update sales password
     * @param request
     * @param authSalesInfo
     * @return
     */
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
    @GetMapping(value = "/users")
    public String getUsers(HttpServletRequest request, Model model){
        return authAdminService.users(request, model, 0);
    }

    /**
     * get users under specify sales by sales id
     * @param request
     * @param model
     * @param salesId
     * @return
     */
    @GetMapping(value = "/users/{salesId}")
    public String getUsersBySale(HttpServletRequest request, Model model, @PathVariable int salesId){
        return authAdminService.users(request, model, salesId);
    }

    /**
     * user details
     * @param request
     * @param key
     * @return
     */
    @GetMapping(value = "/user/{key}")
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
    @GetMapping(value = "/commission")
    public String getCommission(HttpServletRequest request, Model model){
        return authAdminService.commission(request, model);
    }

    /**
     * get real time online number
     * @return  current online number
     */
    @GetMapping(value = "/chart/online")
    @ResponseBody
    public int getCurrentOnline(){
        return SessionListener.sessionMap.size();
    }

    /**
     * get every day sales volume in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        SalesDayVolumeInMonthInfo list
     */
    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public List<SalesDayVolumeInMonthInfo> getSaleVolumeEveryDayInMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.countSaleVolumeEveryDayInMonth(year, month);
    }

    /**
     * the top {?} volume
     * @param top   specify number
     * @return      TopVolumeInfo list
     */
    @GetMapping(value = "/chart/sales/volume/{top}")
    @ResponseBody
    public List<TopVolumeInfo> getTopVolume(@PathVariable int top){
        return authAdminService.getTopVolume(top);
    }

    /**
     * the top {?} amount
     * @param top  specify number
     * @return     TopVolumeInfo list
     */
    @GetMapping(value = "/chart/sales/amount/{top}")
    @ResponseBody
    public List<TopAmountInfo> getTopAmount(@PathVariable int top){
        return authAdminService.getTopAmount(top);
    }

    /**
     * get all sales commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllSalesMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/sales/commission/{year}/{month}")
    @ResponseBody
    public List<AllSalesMonthCommissionInfo> getSales(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllSalesCommissionByMonth(year, month);
    }

    /**
     * get every month sales amount in specify year
     * @param year   specify year
     * @return       SalesAmountInfo list
     */
    @GetMapping(value = "/chart/amount/{year}")
    @ResponseBody
    public List<SalesAmountInfo> getSalesAmountEveryMonthInYear(@PathVariable int year){
        return authAdminService.selectSaleAmountEveryMonthInYear(year);
    }

    /**
     * get every days sales amount in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        SalesAmountInfo list
     */
    @GetMapping(value = "/chart/amount/{year}/{month}")
    @ResponseBody
    public List<SalesAmountInfo> getSalesAmountEveryDayInMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.selectSaleAmountEveryDayInMonth(year, month);
    }


}
