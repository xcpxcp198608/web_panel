package com.wiatec.panel.web;

import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.common.result.ResultInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesAmountInfo;
import com.wiatec.panel.oxm.pojo.chart.admin.SalesVolumeInDayOfMonthInfo;
import com.wiatec.panel.service.auth.AuthMexicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author patrick
 */
@Controller
@RequestMapping(value = "/mexico")
public class AuthMexico {

    @Resource
    private AuthMexicoService authMexicoService;

    /**
     * show mexico panel sign in page
     * @return jsp -> mexico -> index.jsp
     */
    @RequestMapping(value = "/")
    public String show(){
        return "mexico/index";
    }

    /**
     * show home page
     * @return jsp -> mexico -> home.jsp
     */
    @RequestMapping(value = "/home")
    public String home(){
        return "mexico/home";
    }

    /**
     * show all customers page
     * @param model model
     * @return jsp -> mexico -> customers.jsp
     */
    @RequestMapping(value = "/customers")
    public String customers(Model model){
        List<AuthRentUserInfo> authRentUserInfoList = authMexicoService.getCustomers();
        model.addAttribute("authRentUserInfoList", authRentUserInfoList);
        return "mexico/customers";
    }

    /**
     * update customer status
     * @param status status(activate,deactivate,cancel)
     * @param clientKey client key
     * @return ResultInfo
     */
    @RequestMapping(value = "/customer/{status}/{key}")
    @ResponseBody
    public ResultInfo updateStatusByClientKey(@PathVariable("status") String status,
                                              @PathVariable("key") String clientKey){
        return authMexicoService.updateCustomerStatus(status, clientKey);
    }

    /**
     * activate customer after get the cash
     * @param request HttpServletRequest
     * @param key client key
     * @param password auth mexico password
     * @return ResultInfo
     */
    @RequestMapping(value = "/customer/cash_activate")
    @ResponseBody
    public ResultInfo customerCashActivate(HttpServletRequest request, String key, String password){
        return authMexicoService.customerCashActivate(request, key, password);
    }

    /**
     * get customer detail information
     * @param clientKey client key
     * @return ResultInfo
     */
    @RequestMapping(value = "/customer/{key}")
    @ResponseBody
    public AuthRentUserInfo showCustomerDetails(@PathVariable("key") String clientKey){
        return authMexicoService.getUserByClientKey(clientKey);
    }

    /**
     * show customer create page
     * @param model model
     * @return jsp -> mexico -> create_user.jsp
     */
    @RequestMapping(value = "/customers/create")
    public String create(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = authMexicoService.getCommissionCategory();
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "mexico/create_user";
    }

    /**
     * create a new rental customer
     * @param request HttpServletRequest
     * @param authRentUserInfo AuthRentUserInfo
     * @param paymentMethod paymentMethod
     * @return ResultInfo
     */
    @RequestMapping(value = "/customer/create/{paymentMethod}")
    @ResponseBody
    public ResultInfo createCustomer(HttpServletRequest request, AuthRentUserInfo authRentUserInfo,
                                     @PathVariable("paymentMethod") int paymentMethod){
        return authMexicoService.createCustomer(request, authRentUserInfo, paymentMethod);
    }

    @RequestMapping(value = "/commission")
    public String commission(Model model){
        List<CommissionCategoryInfo> commissionCategoryInfoList = authMexicoService.getCommissionCategory();
        model.addAttribute("commissionCategoryInfoList", commissionCategoryInfoList);
        return "mexico/commission";
    }

    @RequestMapping(value = "/transactions")
    public String transactions(Model model){
        List<AuthorizeTransactionRentalInfo> authorizeTransactionRentalInfoList = authMexicoService.getTransactions();
        model.addAttribute("authorizeTransactionRentalInfoList", authorizeTransactionRentalInfoList);
        return "mexico/transactions";
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
        return authMexicoService.countSaleVolumeEveryDayInMonth(year, month);
    }

    /**
     * get every month sales amount in specify year
     * @param year   specify year
     * @return       SalesAmountInfo list
     */
    @GetMapping(value = "/chart/amount/{year}")
    @ResponseBody
    public List<SalesAmountInfo> getSalesAmountEveryMonthInYear(@PathVariable int year){
        return authMexicoService.selectSaleAmountEveryMonthInYear(year);
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
        return authMexicoService.selectSaleAmountEveryDayInMonth(year, month);
    }
}
