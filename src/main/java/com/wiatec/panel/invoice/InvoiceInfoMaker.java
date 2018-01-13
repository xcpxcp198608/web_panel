package com.wiatec.panel.invoice;

import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick
 */
public class InvoiceInfoMaker {

    private Logger logger = LoggerFactory.getLogger(InvoiceInfoMaker.class);

    public static List<InvoiceInfo> contracted(CommissionCategoryInfo commissionCategoryInfo){
        return realCreate(commissionCategoryInfo, true);
    }

    public static List<InvoiceInfo> monthly(CommissionCategoryInfo commissionCategoryInfo){
        return realCreate(commissionCategoryInfo, false);
    }

    private static InvoiceInfo createDeposit(CommissionCategoryInfo commissionCategoryInfo){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("SD001");
        invoiceInfo.setDescription("Security Deposit");
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(commissionCategoryInfo.getDeposit());
        invoiceInfo.setAmount(commissionCategoryInfo.getDeposit());
        return invoiceInfo;
    }


    private static List<InvoiceInfo> realCreate(CommissionCategoryInfo commissionCategoryInfo, boolean isContract){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        if(isContract) {
            invoiceInfoList.add(createDeposit(commissionCategoryInfo));
        }
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(commissionCategoryInfo.getMonthPay());
        invoiceInfo.setAmount(commissionCategoryInfo.getMonthPay());
        invoiceInfo.setTax(true);
        switch (commissionCategoryInfo.getCategory()){
            case CommissionCategoryInfo.CATEGORY_B1:
                invoiceInfo.setItem("BVB1");
                invoiceInfo.setDescription("BVision Basic 24 months");
                break;
            case CommissionCategoryInfo.CATEGORY_P1:
                invoiceInfo.setItem("BVP1");
                invoiceInfo.setDescription("BVision VIP 12 months");
                break;
            case CommissionCategoryInfo.CATEGORY_P2:
                invoiceInfo.setItem("BVP2");
                invoiceInfo.setDescription("BVision VIP 24 months");
                break;
            default:
                invoiceInfo.setItem("");
                invoiceInfo.setDescription("");
                break;
        }
        invoiceInfoList.add(invoiceInfo);
        return invoiceInfoList;
    }

}
