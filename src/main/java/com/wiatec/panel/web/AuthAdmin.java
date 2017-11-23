package com.wiatec.panel.web;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.service.auth.AuthAdminService;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AuthAdmin {

    @Resource
    private AuthAdminService authAdminService;

    /**
     * home page
     * @param model   mvc Model
     * @return        home page
     */
    @GetMapping(value = "/")
    public String home(Model model){
        return authAdminService.home(model);
    }


    /**
     * sales page
     * @param model   mvc Model
     * @return        sales page
     */
    @GetMapping(value = "/sales")
    public String getSales(Model model){
        return authAdminService.sales(model);
    }



    /**
     * create user
     * @param authSalesInfo  {@link AuthSalesInfo}
     * @return               {@link ResultInfo}
     */
    @PostMapping(value = "/sale/create")
    @ResponseBody
    public ResultInfo createSales(@Valid AuthSalesInfo authSalesInfo,
                                  BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            throw new XException(3001, bindingResult.getFieldError().getDefaultMessage());
        }
        return authAdminService.createSales(authSalesInfo);
    }

    /**
     * update sales password
     * @param authSalesInfo   {@link AuthSalesInfo}
     * @return                {@link ResultInfo}
     */
    @PutMapping(value = "/sale/update")
    @ResponseBody
    public ResultInfo updateSales(@RequestBody AuthSalesInfo authSalesInfo){
        return authAdminService.updateSalesPassword(authSalesInfo);
    }

    /**
     * users page
     * @param model   mvc model
     * @return        users page
     */
    @GetMapping(value = "/users")
    public String getUsers(Model model){
        return authAdminService.users( model, 0);
    }

    /**
     * get users under specify sales by sales id
     * @param model      mvc model
     * @param salesId    sales id
     * @return           users page
     */
    @GetMapping(value = "/users/{salesId}")
    public String getUsersBySale(Model model, @PathVariable int salesId){
        return authAdminService.users(model, salesId);
    }

    /**
     * user details
     * @param key    client key
     * @return       {@link AuthRentUserInfo}
     */
    @GetMapping(value = "/user/{key}")
    @ResponseBody
    public AuthRentUserInfo getUserByKey(@PathVariable String key){
        return authAdminService.getUserByKey(key);
    }

    @PutMapping(value = "/activate/{key}")
    @ResponseBody
    public ResultInfo activateUser(@PathVariable String key){
        return authAdminService.activateUser(key);
    }

    /**
     * commission page
     * @param model    mvc model
     * @return         commission page
     */
    @GetMapping(value = "/commission")
    public String getCommission(Model model){
        return authAdminService.commission(model);
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
