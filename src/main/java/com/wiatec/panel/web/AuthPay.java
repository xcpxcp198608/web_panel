package com.wiatec.panel.web;

import com.wiatec.panel.oxm.dao.NotifyDao;
import com.wiatec.panel.oxm.dao.PayOrderDao;
import com.wiatec.panel.paypal.NotifyInfo;
import com.wiatec.panel.paypal.PayOrderInfo;
import com.wiatec.panel.service.PayService;
import com.wiatec.panel.xutils.LoggerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            LoggerUtil.d(name + ": " +request.getParameter(name));
        }
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
        Enumeration<String> parameterNames = request.getParameterNames();
        List<NotifyInfo> notifyInfoList = new ArrayList<>();
        PayOrderInfo payOrderInfo = new PayOrderInfo();
        while (parameterNames.hasMoreElements()){
            String name = parameterNames.nextElement();
            String value = request.getParameter(name);
            LoggerUtil.d(name + ": " + value);
            notifyInfoList.add(new NotifyInfo(name, value));
//            if("business".equals(name)){
//                if(!"paotwo@gmail.com".equals(value)){
//                    return"";
//                }
//            }
            if("payer_email".equals(name)){
                payOrderInfo.setPayerEmail(value);
            }
            if("payer_id".equals(name)){
                payOrderInfo.setPayerId(value);
            }
            if("payment_status".equals(name)){
                payOrderInfo.setPaymentStatus(value);
            }
            if("receiver_id".equals(name)){
                payOrderInfo.setReceiverId(value);
            }
            if("verify_sign".equals(name)){
                payOrderInfo.setVerifySign(value);
            }
            if("mc_gross".equals(name)){
                payOrderInfo.setPrice(Float.parseFloat(value));
            }
            if("txn_id".equals(name)){
                payOrderInfo.setTransactionId(value);
            }
            if("invoice".equals(name)){
                payOrderInfo.setInvoice(value);
            }
        }
        notifyDao.insertBath(notifyInfoList);
        payService.notifyPayment(request, response);
    }

}
