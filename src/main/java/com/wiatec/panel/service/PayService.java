package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.AuthOrderDao;
import com.wiatec.panel.oxm.dao.PayOrderDao;
import com.wiatec.panel.paypal.PayOrderInfo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class PayService {

    @Resource
    private AuthOrderDao authOrderDao;

    @Resource
    private PayOrderDao payOrderDao;

    public String verifyPayment(HttpServletRequest request, Model model){
        String result;
        String invoice = request.getParameter("invoice");
        String paymentStatus = request.getParameter("payment_status");
        String payerId = request.getParameter("payer_id");
        String payerEmail = request.getParameter("payer_email");
        String paymentDate = request.getParameter("payment_date");
        String receiverId = request.getParameter("receiver_id");
        String verifySign = request.getParameter("verify_sign");
        if("Completed".equals(paymentStatus)){
            result = "pay successfully";
            PayOrderInfo payOrderInfo = new PayOrderInfo();
            payOrderInfo.setInvoice(invoice);
            payOrderInfo.setPaymentStatus(paymentStatus);
            payOrderInfo.setPayerId(payerId);
            payOrderInfo.setPayerEmail(payerEmail);
            payOrderInfo.setPaymentDate(paymentDate);
            payOrderInfo.setReceiverId(receiverId);
            payOrderInfo.setVerifySign(verifySign);
            payOrderDao.updateByInvoice(payOrderInfo);
            payOrderInfo = payOrderDao.selectOneByInvoice(invoice);

        }else{
            result = "pay failure, " + paymentStatus;
        }
        model.addAttribute("result", result);
        return "sales/pay_result";
    }
}
