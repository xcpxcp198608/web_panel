package com.wiatec.panel.web;

import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.service.auth.AuthAdminService;
import com.wiatec.panel.service.auth.AuthDealerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    public String home(){
        return authDealerService.home();
    }

    @GetMapping(value = "/sales")
    public String getSales(HttpServletRequest request, Model model){
        return authDealerService.sales(request, model);
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
     * get every day sales volume in specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        SalesVolumeInDayOfMonthInfo list
     */
    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public List<SalesVolumeInDayOfMonthInfo> getSaleVolumeEveryDayInMonth(HttpServletRequest request, @PathVariable int year, @PathVariable int month){
        return authDealerService.countSaleVolumeEveryDayInMonth(request, year, month);
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
