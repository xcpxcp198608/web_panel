package com.wiatec.panel.web;

import com.wiatec.panel.oxm.dao.NotifyDao;
import com.wiatec.panel.paypal.NotifyInfo;
import com.wiatec.panel.service.PayService;
import com.wiatec.panel.xutils.LoggerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Controller
@RequestMapping(value = "/pay")
public class AuthPay {

    @Resource
    private PayService payService;

    @Resource
    private NotifyDao notifyDao;

    @RequestMapping(value = "/notify")
    public String notify(HttpServletRequest request, Model model){
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            LoggerUtil.d(name + ": " +request.getParameter(name));
            notifyDao.insertOne(new NotifyInfo(name, request.getParameter(name)));
        }
        return "sales/pay_result";
    }

    @RequestMapping(value = "/return")
    public String result(HttpServletRequest request, Model model){
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            LoggerUtil.d(name + ": " +request.getParameter(name));
        }
        return payService.verifyPayment(request, model);
    }

    @RequestMapping(value = "/cancel")
    public String cancel(Model model){
        model.addAttribute("result", "pay cancel");
        return "sales/pay_result";
    }

}
