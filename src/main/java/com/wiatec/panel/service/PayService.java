package com.wiatec.panel.service;

import com.wiatec.panel.aop.Session;
import com.wiatec.panel.oxm.dao.*;
import com.wiatec.panel.oxm.pojo.AuthOrderInfo;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.paypal.PayOrderInfo;
import com.wiatec.panel.xutils.TextUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class PayService {

    @Resource
    private AuthOrderDao authOrderDao;
    @Resource
    private AuthRentUserDao authRentUserDao;
    @Resource
    private PayOrderDao payOrderDao;
    @Resource
    private AuthSalesDao authSalesDao;
    @Resource
    private CommissionCategoryDao commissionCategoryDao;

    @Transactional
    public String verifyPayment(HttpServletRequest request, Model model){
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
            payOrderDao.updateByInvoice(payOrderInfo);
            payOrderInfo = payOrderDao.selectOneByInvoice(invoice);
            AuthOrderInfo authOrderInfo = new AuthOrderInfo();
            authOrderInfo.setSalesId(getSalesId(request));
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
            if(authOrderDao.countOneByPaymentId(authOrderInfo) ==1) {
                authOrderDao.updateOneByPaymentId(authOrderInfo);
            }else{
                authOrderDao.insertOne(authOrderInfo);
            }
            authRentUserDao.updateStatusToActivate(authOrderInfo.getPayClientKey());
            result = "pay successfully";
        }else{
            result = "pay failure, " + paymentStatus;
        }
        model.addAttribute("result", result);
        return "sales/pay_result";
    }


    @Transactional
    public void notifyPayment(HttpServletRequest request, HttpServletResponse response){
        String business = request.getParameter("business");
        String invoice = request.getParameter("invoice");
        String paymentStatus = request.getParameter("payment_status");
        String payerId = request.getParameter("payer_id");
        String payerEmail = request.getParameter("payer_email");
        String transactionId = request.getParameter("txn_id");
        String price = request.getParameter("mc_gross");
        String txFee = request.getParameter("mc_fee");
        String paymentDate = request.getParameter("payment_date");
        paymentDate = paymentDate.replace("T", " ");
        paymentDate = paymentDate.replace("Z", "");
        String receiverId = request.getParameter("receiver_id");
        String verifySign = request.getParameter("verify_sign");
        if("Completed".equals(paymentStatus)) {
            PayOrderInfo payOrderInfo = new PayOrderInfo();
            payOrderInfo.setInvoice(invoice);
            payOrderInfo.setPaymentStatus(paymentStatus);
            payOrderInfo.setPayerId(payerId);
            payOrderInfo.setPayerEmail(payerEmail);
            payOrderInfo.setTxFee(Float.parseFloat(txFee));
            payOrderInfo.setPaymentDate(paymentDate);
            payOrderInfo.setReceiverId(receiverId);
            payOrderInfo.setVerifySign(verifySign);
            payOrderInfo.setTransactionId(transactionId);
            payOrderDao.updateByInvoice(payOrderInfo);
            payOrderInfo = payOrderDao.selectOneByInvoice(invoice);
            if(payOrderInfo.getPrice() != Float.parseFloat(price)){
                return;
            }
            AuthOrderInfo authOrderInfo = new AuthOrderInfo();
            authOrderInfo.setSalesId(getSalesId(request));
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
        }
    }



    private int getSalesId(HttpServletRequest request){
        String username = Session.getUserName(request);
        if(TextUtil.isEmpty(username)){
            throw new RuntimeException("sign info error");
        }
        AuthSalesInfo authSalesInfo = authSalesDao.selectOne(new AuthSalesInfo(username));
        return authSalesInfo.getId();
    }
}
