package com.wiatec.panel.web;

import com.wiatec.panel.listener.SessionListener;
import com.wiatec.panel.oxm.pojo.AuthDealerInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.DevicePCPInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.*;
import com.wiatec.panel.oxm.pojo.commission.DealerMonthlyCommission;
import com.wiatec.panel.oxm.pojo.commission.SalesMonthlyCommission;
import com.wiatec.panel.service.auth.AuthAdminService;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.common.result.XException;
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
@RequestMapping(value = "/admin")
public class AuthAdmin {

    @Resource
    private AuthAdminService authAdminService;

    /**
     * home page
     * @return        home page
     */
    @GetMapping(value = "/")
    public String home(){
        return authAdminService.home();
    }


    /**
     * dealer page
     * @param model   mvc Model
     * @return        dealer page
     */
    @GetMapping(value = "/dealer")
    public String getDealer(Model model){
        return authAdminService.dealer(model);
    }

    /**
     * create user
     * @param authDealerInfo  {@link AuthDealerInfo}
     * @return               {@link ResultInfo}
     */
    @PostMapping(value = "/dealer/create")
    @ResponseBody
    public ResultInfo createDealer(HttpServletRequest request, @Valid AuthDealerInfo authDealerInfo,
                                   BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            throw new XException(3001, bindingResult.getFieldError().getDefaultMessage());
        }
        return authAdminService.createDealer(request, authDealerInfo);
    }

    /**
     * update sales password
     * @param authDealerInfo   {@link AuthDealerInfo}
     * @return                {@link ResultInfo}
     */
    @PutMapping(value = "/dealer/update")
    @ResponseBody
    public ResultInfo updateDealer(@RequestBody AuthDealerInfo authDealerInfo){
        return authAdminService.updateDealerPassword(authDealerInfo);
    }


    /**
     * show dealer detail page
     * @param dealerId dealerId
     * @return dealer detail page
     */
    @GetMapping(value = "/dealer/detail/{dealerId}/{year}/{month}")
    public String dealerDetail(@PathVariable int dealerId, @PathVariable int year, @PathVariable int month, Model model){
        return authAdminService.showDealerDetail(dealerId, year, month, model);
    }


    /**
     * check device when returned the sales deposit
     * @return         ResultInfo
     */
    @PutMapping(value = "/dealer/commission/check")
    @ResponseBody
    public ResultInfo dealerCommissionChecked(HttpServletRequest request,
                                        @RequestParam(value = "ids[]") String [] ids,
                                        int dealerId, String checkNumber){
        return authAdminService.dealerCommissionChecked(request, ids, dealerId, checkNumber);
    }


    /**
     * sales page
     * @param model   mvc Model
     * @return        sales page
     */
    @GetMapping(value = "/sales")
    public String getSales(HttpServletRequest request, Model model){
        return authAdminService.sales(request, model);
    }

    /**
     * create user
     * @param authSalesInfo  {@link AuthSalesInfo}
     *        required: dealer id, username, password, ssn, first name, last name, email, phone,
     *        bank info, credit card number, expiration date, security key, activate category,
     *        gold category
     * @return               {@link ResultInfo}
     */
    @PostMapping(value = "/sale/create")
    @ResponseBody
    public ResultInfo createSales(HttpServletRequest request, @Valid AuthSalesInfo authSalesInfo,
                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            throw new XException(3001, bindingResult.getFieldError().getDefaultMessage());
        }
        return authAdminService.createSales(request, authSalesInfo);
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
     * show sales detail page
     * @param salesId salesId
     * @return sales detail page
     */
    @GetMapping(value = "/sales/detail/{salesId}/{year}/{month}")
    public String salesDetail(@PathVariable int salesId, @PathVariable int year, @PathVariable int month, Model model){
        return authAdminService.showSalesDetail(salesId, year, month, model);
    }



    /**
     * check device when returned the sales deposit
     * @return         ResultInfo
     */
    @PutMapping(value = "/sales/commission/check")
    @ResponseBody
    public ResultInfo salesCommissionChecked(HttpServletRequest request,
                                        @RequestParam(value = "ids[]") String [] ids,
                                        int salesId, String checkNumber){
        return authAdminService.salesCommissionChecked(request, ids, salesId, checkNumber);
    }

    /**
     * users page
     * @param model   mvc model
     * @return        users page
     */
    @GetMapping(value = "/users")
    public String getUsers(Model model){
        return authAdminService.users( model, 0, 0);
    }

    /**
     * get users under specify sales by sales id
     * @param model      mvc model
     * @param key        0 -> all 1 -> select by dealer  2-> select by sales
     * @param value      dealer id or sales id
     * @return           users page
     */
    @GetMapping(value = "/users/{key}/{value}")
    public String getUsersBySale(Model model, @PathVariable int key, @PathVariable int value){
        return authAdminService.users(model, key , value);
    }

    /**
     * get user details
     * @param key    client key
     * @return       {@link AuthRentUserInfo}
     */
    @GetMapping(value = "/user/{key}")
    @ResponseBody
    public AuthRentUserInfo getUserByKey(@PathVariable String key){
        return authAdminService.getUserByKey(key);
    }

    /**
     * update rental user status [activate, limited, canceled]
     * @param status   target status
     * @param key      client key
     * @return         {@link AuthRentUserInfo}
     */
    @PutMapping(value = "/user/{status}/{key}")
    @ResponseBody
    public ResultInfo updateUserStatus(@PathVariable String status, @PathVariable String key){
        return authAdminService.updateUserStatus(status, key);
    }

    /**
     * update rental user status to activate after admin got cash
     * @param password   admin password
     * @param key      client key
     * @return         {@link AuthRentUserInfo}
     */
    @PutMapping(value = "/user/cash_activate")
    @ResponseBody
    public ResultInfo updateUserActivateWithCash(HttpServletRequest request, String key, String password){
        return authAdminService.updateUserActivateWithCash(request, key, password);
    }

    @PutMapping(value = "/user/category/{key}/{category}")
    @ResponseBody
    public ResultInfo updateUserCategory(HttpServletRequest request, @PathVariable String key, @PathVariable String category){
        return authAdminService.updateUserCategory(request, key, category);
    }

    @PutMapping(value = "/activate/{key}")
    @ResponseBody
    public ResultInfo activateUser(@PathVariable String key){
        return authAdminService.updateUserStatus(AuthRentUserInfo.STATUS_ACTIVATE, key);
    }


    /**
     * commission page
     * @param model    mvc model
     * @return         commission page
     */
    @GetMapping(value = "/distribution")
    public String distribution(Model model){
        return authAdminService.distribution(model);
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
     * device page
     * @param model    mvc model
     * @return         device page
     */
    @GetMapping(value = "/devices")
    public String devices(Model model){
        return authAdminService.devices(model);
    }

    /**
     * save a device rent info
     * @param request  HttpServletRequest
     * @param devicePCPInfo  DevicePCPInfo required: mac, dealerId
     * @return         ResultInfo
     */
    @PostMapping(value = "/devices/save")
    @ResponseBody
    public ResultInfo saveDevice(HttpServletRequest request, DevicePCPInfo devicePCPInfo){
        return authAdminService.saveDevice(request, devicePCPInfo);
    }

    /**
     * update a device to special sales
     * @return         ResultInfo
     */
    @PutMapping(value = "/devices/update")
    @ResponseBody
    public ResultInfo updateDeviceToSpecialSales(HttpServletRequest request,
                                                 @RequestParam(value = "macs[]") String [] macs,
                                                 int salesId, String password, boolean free){
        return authAdminService.bathUpdateDeviceToSpecialSales(request, macs, salesId, password, free);
    }

    /**
     * check device when returned the sales deposit
     * @return         ResultInfo
     */
    @PutMapping(value = "/devices/check")
    @ResponseBody
    public ResultInfo checkReturned(HttpServletRequest request,
                                    @RequestParam(value = "macs[]") String [] macs,
                                    int salesId, String checkNumber){
        return authAdminService.checkReturned(request, macs, salesId, checkNumber);
    }

    /**
     * transactions page
     * @param model    mvc model
     * @return         transactions page
     */
    @GetMapping(value = "/transactions")
    public String transactions(Model model){
        return authAdminService.transactions(model);
    }

    @GetMapping(value = "/chart/distribution")
    @ResponseBody
    public List<VolumeDistributionInfo> getDistributionData(){
        return authAdminService.getDistributionData();
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
     * @return        SalesVolumeInDayOfMonthInfo list
     */
    @GetMapping(value = "/chart/volume/{year}/{month}")
    @ResponseBody
    public List<SalesVolumeInDayOfMonthInfo> getSaleVolumeEveryDayInMonth(@PathVariable int year, @PathVariable int month){
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
     * get all dealer commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllDealerMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/dealer/commission/{year}/{month}")
    @ResponseBody
    public List<AllDealerMonthCommissionInfo> getDealerCommissionByMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllDealerCommissionByMonth(year, month);
    }

    /**
     * get all dealer total commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllDealerMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/dealer/commission/total/{year}/{month}")
    @ResponseBody
    public List<DealerMonthlyCommission> getDealerTotalCommissionByMonth(@PathVariable int year, @PathVariable int month){
        System.out.println("dfdf");
        return authAdminService.getAllDealerTotalCommissionByMonth(year, month);
    }

    /**
     * get all dealer activation commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllDealerMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/dealer/commission/activation/{year}/{month}")
    @ResponseBody
    public List<AllDealerMonthCommissionInfo> getDealerActivationCommByMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllDealerActivationCommByMonth(year, month);
    }

    /**
     * get all sales commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllSalesMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/sales/commission/{year}/{month}")
    @ResponseBody
    public List<AllSalesMonthCommissionInfo> getSalesCommissionByMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllSalesCommissionByMonth(year, month);
    }

    /**
     * get all sales total commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllSalesMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/sales/commission/total/{year}/{month}")
    @ResponseBody
    public List<SalesMonthlyCommission> getSalesTotalCommissionByMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllSalesTotalCommissionByMonth(year, month);
    }


    /**
     * get all sales activation commission by specify year and month
     * @param year    specify year
     * @param month   specify month
     * @return        AllSalesMonthCommissionInfo list
     */
    @GetMapping(value = "/chart/sales/commission/activation/{year}/{month}")
    @ResponseBody
    public List<AllSalesMonthCommissionInfo> getSalesActivationCommByMonth(@PathVariable int year, @PathVariable int month){
        return authAdminService.getAllSalesActivationCommByMonth(year, month);
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
