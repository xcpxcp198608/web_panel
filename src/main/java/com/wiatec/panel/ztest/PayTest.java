package com.wiatec.panel.ztest;


import com.wiatec.panel.paypal.PaymentMaster;

public class PayTest {

    public static void main (String [] args){

        PaymentMaster paymentMaster = new PaymentMaster();
        paymentMaster.pay();
    }

}
