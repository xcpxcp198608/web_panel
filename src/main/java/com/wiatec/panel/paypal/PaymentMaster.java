package com.wiatec.panel.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.List;

public class PaymentMaster {

    private final String CLIENT_ID = "ATGpqAClp4njmG0pkFvpiu5HwNzwGq4BBLBAsRxWoSvmLj98qMBfP3_5yYbFSLPUVrenM4cyhHMXOdbL";
    private final String CLIENT_SECRET = "EKzQIQClNZvNRBc9vUhumzwyQzjjLLpenlprv-JsgcA5qoQbLrzQ2iXoDFObuHDBFXGChzXTZtrLWrVk";

    private APIContext context = new APIContext(CLIENT_ID, CLIENT_SECRET, "sandbox");

    public Payment create(){
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("100.00");

        Transaction transaction = new Transaction();
        transaction.setDescription("B1");
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/panel/pay/return");
        redirectUrls.setReturnUrl("http://localhost:8080/panel/pay/cancel");
        payment.setRedirectUrls(redirectUrls);
        return payment;
    }

    public void pay(){
        try {

            Payment createdPayment = create().create(context);
            for(Links links : createdPayment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    System.out.println(links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
    }

    public Payment execute(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(context, paymentExecute);
    }

}
