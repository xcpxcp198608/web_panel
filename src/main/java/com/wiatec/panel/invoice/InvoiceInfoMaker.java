package com.wiatec.panel.invoice;

import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesGoldCategoryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author patrick
 */
public class InvoiceInfoMaker {

    private Logger logger = LoggerFactory.getLogger(InvoiceInfoMaker.class);

    public static List<InvoiceInfo> rentalContracted(CommissionCategoryInfo commissionCategoryInfo){
        return realCreate(commissionCategoryInfo, true, 1);
    }

    private static InvoiceInfo createDeposit(CommissionCategoryInfo commissionCategoryInfo){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("SD001");
        invoiceInfo.setDescription("Security Deposit");
        invoiceInfo.setQty(1);
        invoiceInfo.setTax(true);
        invoiceInfo.setPrice(commissionCategoryInfo.getDeposit());
        invoiceInfo.setAmount(commissionCategoryInfo.getDeposit());
        return invoiceInfo;
    }

    private static InvoiceInfo createActivate(CommissionCategoryInfo commissionCategoryInfo){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("Activate");
        invoiceInfo.setDescription("Activate device");
        invoiceInfo.setQty(1);
        invoiceInfo.setTax(true);
        invoiceInfo.setPrice(commissionCategoryInfo.getActivatePay());
        invoiceInfo.setAmount(commissionCategoryInfo.getActivatePay());
        return invoiceInfo;
    }


    private static List<InvoiceInfo> realCreate(CommissionCategoryInfo commissionCategoryInfo, boolean isContract,
                                                int currentMonth){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        if(isContract) {
            invoiceInfoList.add(createDeposit(commissionCategoryInfo));
            invoiceInfoList.add(createActivate(commissionCategoryInfo));
        }
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(commissionCategoryInfo.getMonthPay());
        invoiceInfo.setAmount(commissionCategoryInfo.getMonthPay());
        invoiceInfo.setTax(true);
        invoiceInfo.setCurrentMonth(currentMonth);
        invoiceInfo.setTotalMonth(commissionCategoryInfo.getExpires());
        invoiceInfo.setItem("BV" + commissionCategoryInfo.getCategory());
        invoiceInfo.setDescription(commissionCategoryInfo.getDescription());
        invoiceInfoList.add(invoiceInfo);
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> rentalMonthly(AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo, int currentMonth, int totalMonth){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(authorizeTransactionRentalInfo.getPrice());
        invoiceInfo.setAmount(authorizeTransactionRentalInfo.getPrice());
        invoiceInfo.setTax(true);
        invoiceInfo.setCurrentMonth(currentMonth);
        invoiceInfo.setTotalMonth(totalMonth);
        invoiceInfo.setItem("BV" + authorizeTransactionRentalInfo.getCategory());
        invoiceInfo.setDescription("monthly");
        invoiceInfoList.add(invoiceInfo);
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> salesActivateNormal(SalesActivateCategoryInfo salesActivateCategoryInfo){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createSalesActivate(salesActivateCategoryInfo));
        return invoiceInfoList;
    }

    private static InvoiceInfo createSalesActivate(SalesActivateCategoryInfo salesActivateCategoryInfo){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem(salesActivateCategoryInfo.getCategory());
        invoiceInfo.setDescription(salesActivateCategoryInfo.getDescription());
        invoiceInfo.setQty(1);
        invoiceInfo.setTax(true);
        invoiceInfo.setPrice(salesActivateCategoryInfo.getPrice());
        invoiceInfo.setAmount(salesActivateCategoryInfo.getPrice());
        return invoiceInfo;
    }


}
