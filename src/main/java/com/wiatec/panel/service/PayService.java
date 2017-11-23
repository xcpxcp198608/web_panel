package com.wiatec.panel.service;

import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.paypal.PayOrderInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

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
import java.util.Enumeration;

@Service
public class PayService {

    private final String EMAIL = "jw_seller@wiatec.com";

    @Resource
    private AuthOrderDao authOrderDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private PayOrderDao payOrderDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    @Transactional
    public String verifyPayment(HttpServletRequest request, Model model){
        Enumeration en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String paramName = (String) en.nextElement();
            String paramValue = request.getParameter(paramName);
            Logger.getLogger("").debug(paramName + ": "+ paramValue);
        }
        String result;
        String invoice = request.getParameter("invoice");
        String paymentStatus = request.getParameter("payment_status");
        String payerId = request.getParameter("payer_id");
        String payerEmail = request.getParameter("payer_email");
        String txFee = request.getParameter("mc_fee");
        String transactionId = request.getParameter("txn_id");
        String paymentDate = request.getParameter("payment_date");
        paymentDate = paymentDate.replace("T", " ");
        paymentDate = paymentDate.replace("Z", "");
        String receiverId = request.getParameter("receiver_id");
        String verifySign = request.getParameter("verify_sign");
        if("Completed".equals(paymentStatus)){
            PayOrderInfo payOrderInfo = new PayOrderInfo();
            payOrderInfo.setInvoice(invoice);
            payOrderInfo.setPaymentStatus(paymentStatus);
            payOrderInfo.setPayerId(payerId);
            payOrderInfo.setTxFee(Float.parseFloat(txFee));
            payOrderInfo.setPayerEmail(payerEmail);
            payOrderInfo.setPaymentDate(paymentDate);
            payOrderInfo.setReceiverId(receiverId);
            payOrderInfo.setVerifySign(verifySign);
            payOrderInfo.setTransactionId(transactionId);
            if(!"Completed".equals(payOrderDao.selectStatusByInvoice(invoice))){
                payOrderDao.updateByInvoice(payOrderInfo);
            }
            payOrderInfo = payOrderDao.selectOneByInvoice(invoice);
            AuthOrderInfo authOrderInfo = new AuthOrderInfo();
            authOrderInfo.setSalesId(payOrderInfo.getSalesId());
            authOrderInfo.setPayClientKey(payOrderInfo.getClientKey());
            authOrderInfo.setCategory(payOrderInfo.getCategory());
            authOrderInfo.setPaymentId(payOrderInfo.getInvoice());
            authOrderInfo.setPrice(payOrderInfo.getPrice());
            authOrderInfo.setTxFee(payOrderInfo.getTxFee());
            CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authOrderInfo.getCategory());
            authOrderInfo.setDeposit(commissionCategoryInfo.getDeposit());
            authOrderInfo.setLdCommission(commissionCategoryInfo.getLdCommission()  * commissionCategoryInfo.getExpires());
            authOrderInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission()  * commissionCategoryInfo.getExpires()) ;
            authOrderInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission()  * commissionCategoryInfo.getExpires());
            authOrderInfo.setStatus("approved");
            authOrderInfo.setDescription(payOrderInfo.getDescription());
            if(authOrderDao.countOneByPaymentId(authOrderInfo) != 1) {
                authOrderDao.insertOne(authOrderInfo);
                authRentUserDao.updateStatusToActivate(authOrderInfo.getPayClientKey());
            }
            result = "pay successfully";
        }else{
            result = "pay failure, " + paymentStatus;
        }
        model.addAttribute("result", result);
        return "sales/pay_result";
    }

    @Transactional
    public void verifyIPNIsFromPayPal(HttpServletRequest request, HttpServletResponse response) {
        URL url = null;
        URLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            Enumeration en = request.getParameterNames();
            String str = "cmd=_notify-validate";
            while (en.hasMoreElements()) {
                String paramName = (String) en.nextElement();
                String paramValue = request.getParameter(paramName);
                str = str + "&" + paramName + "=" + URLEncoder.encode(paramValue, "utf-8");
                Logger.getLogger("").debug(paramName + ": "+ paramValue);
            }
            Logger.getLogger("").debug(str);
            response.getWriter().print(200);
            url = new URL("https://ipnpb.sandbox.paypal.com/cgi-bin/webscr");
//            url = new URL("https://ipnpb.paypal.com/cgi-bin/webscr");
            urlConnection = url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            PrintWriter writer = new PrintWriter(urlConnection.getOutputStream());
            writer.println(str);
            writer.close();
            inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            String result = bufferedReader.readLine();
            Logger.getLogger("").debug(result);
            //获取 PayPal 对回发信息的回复信息，判断刚才的通知是否为 PayPal 发出的
            if (result.equals("VERIFIED")) {
                //付款明细变量可参考：
                //https://www.paypal.com/IntegrationCenter/ic_ipn-pdt-variable-reference.html
                String itemName = request.getParameter("item_name");
                String itemNumber = request.getParameter("item_number");
                String paymentStatus = request.getParameter("payment_status");
                String paymentDate = request.getParameter("payment_date");
                String price = request.getParameter("mc_gross");
                String txFee = request.getParameter("mc_fee");
                String paymentCurrency = request.getParameter("mc_currency");
                String txnId = request.getParameter("txn_id");
                String receiverId = request.getParameter("receiver_id");
                String receiverEmail = request.getParameter("business");
                String payerId = request.getParameter("payer_id");
                String payerEmail = request.getParameter("payer_email");
                String invoice = request.getParameter("invoice");
                String verifySign = request.getParameter("verify_sign");
                if(!"Completed".equals(paymentStatus)) {
                    return;
                }
                if(!EMAIL.equals(receiverEmail)){
                    return;
                }
                PayOrderInfo payOrderInfo = new PayOrderInfo();
                payOrderInfo.setInvoice(invoice);
                payOrderInfo.setPaymentStatus(paymentStatus);
                payOrderInfo.setPayerId(payerId);
                payOrderInfo.setPayerEmail(payerEmail);
                payOrderInfo.setTxFee(Float.parseFloat(txFee));
                payOrderInfo.setPaymentDate(paymentDate);
                payOrderInfo.setReceiverId(receiverId);
                payOrderInfo.setVerifySign(verifySign);
                payOrderInfo.setTransactionId(txnId);
                payOrderDao.updateByInvoice(payOrderInfo);
                payOrderInfo = payOrderDao.selectOneByInvoice(invoice);
                if(payOrderInfo.getPrice() != Float.parseFloat(price)){
                    return;
                }
                AuthOrderInfo authOrderInfo = new AuthOrderInfo();
                authOrderInfo.setSalesId(payOrderInfo.getSalesId());
                authOrderInfo.setPayClientKey(payOrderInfo.getClientKey());
                authOrderInfo.setCategory(payOrderInfo.getCategory());
                authOrderInfo.setPaymentId(payOrderInfo.getInvoice());
                authOrderInfo.setPrice(payOrderInfo.getPrice());
                authOrderInfo.setTxFee(payOrderInfo.getTxFee());
                CommissionCategoryInfo commissionCategoryInfo = commissionCategoryDao.selectOne(authOrderInfo.getCategory());
                authOrderInfo.setDeposit(commissionCategoryInfo.getDeposit());
                authOrderInfo.setLdCommission(commissionCategoryInfo.getLdCommission() * commissionCategoryInfo.getExpires());
                authOrderInfo.setDealerCommission(commissionCategoryInfo.getDealerCommission()  * commissionCategoryInfo.getExpires());
                authOrderInfo.setSalesCommission(commissionCategoryInfo.getSalesCommission()  * commissionCategoryInfo.getExpires());
                authOrderInfo.setStatus("approved");
                authOrderInfo.setDescription(payOrderInfo.getDescription());
                if(authOrderDao.countOneByPaymentId(authOrderInfo) ==1) {
                    authOrderDao.updateOneByPaymentId(authOrderInfo);
                }else{
                    authOrderDao.insertOne(authOrderInfo);
                }
                authRentUserDao.updateStatusToActivate(authOrderInfo.getPayClientKey());
            } else if (result.equals("INVALID")) {
                Logger.getLogger("").debug("IPN INVALID");
            } else {
                Logger.getLogger("").debug("other IPN error");
            }
        } catch (IOException e) {
            Logger.getLogger("").debug(e.getMessage());
        }finally {
            try {
                if(bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
