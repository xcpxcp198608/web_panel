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
        return authDealerService.users(request, model);
    }


}
