package com.wiatec.panel.web;

import com.wiatec.panel.oxm.dao.NotifyDao;
import com.wiatec.panel.oxm.dao.PayOrderDao;
import com.wiatec.panel.paypal.NotifyInfo;
import com.wiatec.panel.paypal.PayOrderInfo;
import com.wiatec.panel.service.PayService;
import com.wiatec.panel.xutils.LoggerUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Controller
@RequestMapping(value = "/pay")
public class AuthPay {

    @Resource
    private PayService payService;

    @Resource
    private NotifyDao notifyDao;


    /**
     * pay result page after paypal pay completed
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/return")
    public String result(HttpServletRequest request, Model model){
        return payService.verifyPayment(request, model);
    }

    /**
     * pay result page after cancel paypal pay
     * @param model
     * @return
     */
    @RequestMapping(value = "/cancel")
    public String cancel(Model model){
        model.addAttribute("result", "pay cancel");
        return "sales/pay_result";
    }

    /**
     * paypal IPN listener
     * IPN simulator: https://ipnpb.sandbox.paypal.com/cgi-bin/webscr
     * IPN live: https://ipnpb.paypal.com/cgi-bin/webscr
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify")
    public void notify(HttpServletRequest request, HttpServletResponse response){
        payService.verifyIPNIsFromPayPal(request, response);
    }


}
