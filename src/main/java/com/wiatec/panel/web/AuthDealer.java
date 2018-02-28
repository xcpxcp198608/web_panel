package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfDaysInfo;
import com.wiatec.panel.oxm.pojo.chart.dealer.DealerCommissionOfMonthInfo;
import com.wiatec.panel.service.auth.AuthDealerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/dealer")
public class AuthDealer {

    @Resource
    private AuthDealerService authDealerService;

    /**
     * home page
     * @return        home page
     */
    @GetMapping(value = "/")
    public String home(HttpServletRequest request, Model model){
        return authDealerService.home(request, model);
    }

    @GetMapping(value = "/sales")
    public String getSales(HttpServletRequest request, Model model){
        return authDealerService.sales(request, model);
    }

    /**
     * create user
     * @param authSalesInfo  {@link AuthSalesInfo}
     * @return               {@link ResultInfo}
     */
    @PostMapping(value = "/sale/create")
    @ResponseBody
    public ResultInfo createSales(HttpServletRequest request, @Valid AuthSalesInfo authSalesInfo,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new XException(3001, bindingResult.getFieldError().getDefaultMessage());
        }
        return authDealerService.createSales(request, authSalesInfo);
    }

    /**
     * update sales password
     * @param authSalesInfo   {@link AuthSalesInfo}
     * @return                {@link ResultInfo}
     */
    @PutMapping(value = "/sale/update")
    @ResponseBody
    public ResultInfo updateSales(@RequestBody AuthSalesInfo authSalesInfo){
        return authDealerService.updateSalesPassword(authSalesInfo);
    }

    @GetMapping(value = "/users")
    public String getUsers(HttpServletRequest request, Model model){
        return authDealerService.users(request, model, 1, 0);
    }

    /**
     * get users under specify sales by sales id
     * @param model      mvc model
     * @param key        0 -> all 1 -> select by dealer  2-> select by sales
     * @param value      dealer id or sales id
     * @return           users page
     */
    @GetMapping(value = "/users/{key}/{value}")
    public String getUsersBySale(HttpServletRequest request, Model model, @PathVariable int key, @PathVariable int value){
        return authDealerService.users(request, model, key , value);
    }

    /**
     * get user details
     * @param key    client key
     * @return       {@link AuthRentUserInfo}
     */
    @GetMapping(value = "/user/{key}")
    @ResponseBody
    public AuthRentUserInfo getUserByKey(@PathVariable String key) {
        return authDealerService.getUserByKey(key);
    }

    /**
     * update rental user status [active, limited, canceled]
     * @param status   target status
     * @param key      client key
     * @return         {@link AuthRentUserInfo}
     */
    @PutMapping(value = "/user/{status}/{key}")
    @ResponseBody
    public ResultInfo updateUserStatus(@PathVariable String status, @PathVariable String key){
        return authDealerService.updateUserStatus(status, key);
    }


    /**
     * get every day sales volume in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        SalesVolumeInDayOfMonthInfo list
     */
    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public List<SalesVolumeInDayOfMonthInfo> getDealerVolumeEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authDealerService.selectDealerVolumeEveryDayInMonth(request, year, month);
    }


    /**
     * get every day dealer commission in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        DealerCommissionOfDaysInfo list
     */
    @GetMapping(value = "/chart/commission/{year}/{month}")
    @ResponseBody
    public List<DealerCommissionOfDaysInfo> getDealerCommissionEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authDealerService.selectDealerCommissionEveryDayInMonth(request, year, month);
    }
    /**
     * get every day dealer activation commission in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        DealerCommissionOfDaysInfo list
     */
    @GetMapping(value = "/chart/commission/activation/{year}/{month}")
    @ResponseBody
    public List<DealerCommissionOfDaysInfo> getDealerActivationCommissionEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authDealerService.selectDealerActivationCommissionEveryDayInMonth(request, year, month);
    }

    /**
     * get every month dealer commission in specify year and month
     * @param year    specify year
     * @return        DealerCommissionOfMonthInfo list
     */
    @GetMapping(value = "/chart/commission/{year}")
    @ResponseBody
    public List<DealerCommissionOfMonthInfo> getDealerCommissionEveryMonthInYear(HttpServletRequest request, @PathVariable int year){
        return authDealerService.selectDealerCommissionEveryMonthInYear(request, year);
    }



    /**
     * the top {?} volume
     * @param top   specify number
     * @return      TopVolumeInfo list
     */
    @GetMapping(value = "/chart/sales/volume/{top}")
    @ResponseBody
    public List<TopVolumeInfo> getTopVolume(HttpServletRequest request, @PathVariable int top){
        return authDealerService.getTopVolume(request, top);
    }

    /**
     * the top {?} amount
     * @param top  specify number
     * @return     TopVolumeInfo list
     */
    @GetMapping(value = "/chart/sales/amount/{top}")
    @ResponseBody
    public List<TopAmountInfo> getTopAmount(HttpServletRequest request, @PathVariable int top){
        return authDealerService.getTopAmount(request, top);
    }

    /**
     * get all sales commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllSalesMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/sales/commission/{year}/{month}")
    @ResponseBody
    public List<AllSalesMonthCommissionInfo> getSalesCommission(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authDealerService.getSalesCommissionByMonth(request, year, month);
    }

}
