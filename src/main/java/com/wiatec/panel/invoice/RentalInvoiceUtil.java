package com.wiatec.panel.invoice;


import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.wiatec.panel.authorize.AuthorizeTransactionRentalInfo;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.common.utils.UnitUtil;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.commission.CommissionCategoryInfo;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


/**
 * @author patrick
 */
public class RentalInvoiceUtil {

    private static final Logger logger = LoggerFactory.getLogger(RentalInvoiceUtil.class);

    private static final float RATE_TAX = 0.08F;
    private static final float SPACE = 8;

    public static void main(String[] args) throws Exception {
        //contracted
//        CommissionCategoryInfo commissionCategoryInfo = new CommissionCategoryInfo();
//        commissionCategoryInfo.setCategory(CommissionCategoryInfo.CATEGORY_P1);
//        commissionCategoryInfo.setDeposit(0F);
//        commissionCategoryInfo.setMonthPay(29.99F);
//        commissionCategoryInfo.setExpires(24);
//        commissionCategoryInfo.setActivatePay(0);
//        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
//        authRentUserInfo.setFirstName("Clotilde");
//        authRentUserInfo.setLastName("Zamora");
//        authRentUserInfo.setSalesName("LaLeona");
//        authRentUserInfo.setDealerName("LDE");
//        authRentUserInfo.setCardNumber("4815880007897392");
//        authRentUserInfo.setEmail("ummami3181@yahoo.com");
//        authRentUserInfo.setExpress(false);
//        authRentUserInfo.setPostAddress("1214 W Cubbon St Santa Ana CA 92703");
//        String pdf = createInvoice(authRentUserInfo,"40585089966",
//                InvoiceInfoMaker.rentalContracted(commissionCategoryInfo));
//        copyInvoice(pdf);
//        System.out.println(pdf);

        //monthly
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setCategory(CommissionCategoryInfo.CATEGORY_P2);
        authorizeTransactionRentalInfo.setPrice(29.99F);
        AuthRentUserInfo authRentUserInfo = new AuthRentUserInfo();
        authRentUserInfo.setFirstName("Clotilde");
        authRentUserInfo.setLastName("Zamora");
        authRentUserInfo.setSalesName("LaLeona");
        authRentUserInfo.setDealerName("LDE");
        authRentUserInfo.setCardNumber("4815880007897392");
        authRentUserInfo.setEmail("ummami3181@yahoo.com");
        authRentUserInfo.setExpress(false);
        authRentUserInfo.setPostAddress("1214 W Cubbon St Santa Ana CA 92703");
        String pdf = createInvoice(authRentUserInfo,"40585089966",
                InvoiceInfoMaker.rentalMonthly(authorizeTransactionRentalInfo, 2, 24));
        copyInvoice(pdf);
        System.out.println(pdf);
//
//        EmailMaster emailMaster = new EmailMaster(EmailMaster.SEND_FROM_LDE);
//        emailMaster.setInvoiceContent("Clotilde");
//        emailMaster.addAttachment(pdf);
//        emailMaster.sendMessage("ummami3181@yahoo.com");
    }

    //本地测试用
//    private static String outPath = "/Users/xuchengpeng/IdeaProjects/panel/src/main/resources/invoice/";
    private static String outPath = "/home/java_app/panel/web/invoice/";

    public static void setPath(String path){
        outPath = path;
    }

    /**
     * create invoice
     * @param authRentUserInfo    email, first name, last name, address
     * @param transactionId
     * @param invoiceInfoList
     * @return
     * @throws Exception
     */
    public static String createInvoice(AuthRentUserInfo authRentUserInfo, String transactionId, List<InvoiceInfo> invoiceInfoList){
        FileOutputStream fileOutputStream = null;
        Document doc = null;
        try {
            Rectangle rect = new Rectangle(PageSize.A4);
            doc = new Document(rect);
            BaseFont bfChinese = BaseFont.createFont("Helvetica", "Cp1252", BaseFont.NOT_EMBEDDED);
            Font littleTextFont = new Font(bfChinese, 7, Font.NORMAL, Color.GRAY);
            Font littleTextFont1 = new Font(bfChinese, 8, Font.NORMAL, Color.GRAY);
            Font textFont = new Font(bfChinese, 10, Font.NORMAL);
            Font whiteTextFont = new Font(bfChinese, 10, Font.NORMAL, Color.white);
            Font redTextFont = new Font(bfChinese, 11, Font.NORMAL, Color.RED);
            Font boldFont = new Font(bfChinese, 11, Font.BOLD);
            Font bold12Font = new Font(bfChinese, 12, Font.BOLD);
            Font redBoldFont = new Font(bfChinese, 8, Font.BOLD, Color.RED);
            Font firstTitleFont = new Font(bfChinese, 22, Font.BOLD);
            Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD);
            Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE);
            Font underlineRedFont = new Font(bfChinese, 11, Font.UNDERLINE, Color.RED);
            Font BlueFont = new Font(bfChinese, 11, Font.NORMAL, Color.BLUE);

            String fileName = "ld_invoice_" + transactionId + ".pdf";
            String path = outPath + fileName;
            File file = new File(path);
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fileOutputStream);

            doc.open();
            doc.newPage();

            Paragraph p1 = new Paragraph();
            Phrase ph1 = new Phrase();
            Chunk c1 = new Chunk("Invoice", firstTitleFont);
            ph1.add(c1);
            p1.add(ph1);
            p1.setAlignment("Right");
            p1.setLeading(30);
            doc.add(p1);


            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c2 = new Chunk("LD Eufonico LLC", boldFont);
            Chunk c2322 = new Chunk("                                                                            " +
                    "                                 Phone: 949-988-9000", boldFont);
            ph1.add(c2);
            ph1.add(c2322);
            p1.add(ph1);
            p1.setLeading(30);
            doc.add(p1);

            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c222 = new Chunk("1221 E. Dyer Road", boldFont);
            Chunk c232 = new Chunk("                                                                            " +
                    "                                  Fax: 949-396-1030", boldFont);
            ph1.add(c222);
            ph1.add(c232);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c223 = new Chunk("Santa Ana, CA 92705", boldFont);
            ph1.add(c223);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(20);
            ph1 = new Phrase();
            Chunk c1353 = new Chunk("  ", secondTitleFont);
            ph1.add(c1353);
            p1.add(ph1);
            doc.add(p1);

            /**
             * table of ship
             */
            PdfPTable table = new PdfPTable(2);
            table.setTotalWidth(new float[]{262, 263});
            table.setLockedWidth(true);
            PdfPCell cell = new PdfPCell(new Phrase("Bill To:", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Ship To:", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthBottom(0);
            table.addCell(cell);


            cell = new PdfPCell(new Phrase(authRentUserInfo.getFirstName() + " " +
                    authRentUserInfo.getLastName() + "\n" +
                    authRentUserInfo.getPostAddress(), textFont));
            cell.setMinimumHeight(40);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            if(authRentUserInfo.isExpress()) {
                cell = new PdfPCell(new Phrase(authRentUserInfo.getFirstName() + " " +
                        authRentUserInfo.getLastName() + "\n" +
                        authRentUserInfo.getPostAddress(), textFont));
            }else{

                cell = new PdfPCell(new Phrase("~", textFont));
            }
            cell.setMinimumHeight(40);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            doc.add(table);


            p1 = new Paragraph();
            p1.setLeading(10);
            ph1 = new Phrase();
            Chunk c13 = new Chunk("  ", secondTitleFont);
            ph1.add(c13);
            p1.add(ph1);
            doc.add(p1);


            /**
             * table of dist, date
             */
            table = new PdfPTable(5);
            table.setTotalWidth(new float[]{105, 105, 105, 105, 105});
            table.setLockedWidth(true);
            cell = new PdfPCell(new Phrase("DIST. #", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.gray);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("DATE", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.gray);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("INVOICE #", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.gray);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SHIP VIA", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.gray);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SALES", bold12Font));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.gray);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(authRentUserInfo.getDealerName(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(TimeUtil.getStrDate(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(transactionId, textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            if(authRentUserInfo.isExpress()) {
                cell = new PdfPCell(new Phrase("USPS", textFont));
            }else{
                cell = new PdfPCell(new Phrase("~", textFont));
            }
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(authRentUserInfo.getSalesName(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBorderWidthTop(0);
            table.addCell(cell);
            doc.add(table);


            p1 = new Paragraph();
            p1.setLeading(20);
            ph1 = new Phrase();
            Chunk c136 = new Chunk("  ", secondTitleFont);
            ph1.add(c136);
            p1.add(ph1);
            doc.add(p1);


            AmountInfo amountInfo = getTotalAmount(invoiceInfoList);
            /**
             * table of detail
             */
            table = new PdfPTable(6);
            table.setTotalWidth(new float[]{35, 70, 185, 75, 80, 80});
            table.setLockedWidth(true);
            cell = new PdfPCell(new Phrase("QTY", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("ITEM", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Description", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Unit Volume", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Unit Price", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Amount", boldFont));
            cell.setMinimumHeight(25);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            for (int i = 0; i < invoiceInfoList.size(); i ++) {
                InvoiceInfo invoiceInfo = invoiceInfoList.get(i);
                cell = new PdfPCell(new Phrase((i+1)+"", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(invoiceInfo.getItem(), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase(invoiceInfo.getDescription(), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("" + invoiceInfo.getQty(), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("$" + UnitUtil.round(invoiceInfo.getPrice()), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("$" + UnitUtil.round(invoiceInfo.getAmount()), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            int length = 12 - invoiceInfoList.size();
            for (int i = 0; i < length; i++) {
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("  ", textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("PAYMENTS", whiteTextFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(2);
            cell.setBackgroundColor(Color.BLACK);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Credit card " + authRentUserInfo.getCardNumber() + " for " + amountInfo.getTotal(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Date: " + TimeUtil.getStrDate(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(2);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);



            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SUBTOTAL", boldFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + amountInfo.getSubTotal(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TAX", boldFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + amountInfo.getTax(), textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SHIPPING", boldFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("$0.00", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(4);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TOTAL", whiteTextFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.BLACK);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + amountInfo.getTotal(), redTextFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            doc.add(table);



            p1 = new Paragraph();
            p1.setLeading(20);
            ph1 = new Phrase();
            Chunk c143 = new Chunk("Thank you for your payment for the Preferred Customer Program in the " +
                    "amount of $"+ amountInfo.getTotal() + ".", littleTextFont1);
            ph1.add(c143);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(10);
            ph1 = new Phrase();
            Chunk c144 = new Chunk("Payment "+amountInfo.getCurrentMonth()+" of "+amountInfo.getTotalMonth()+" has been applied to " +
                    "your account.", littleTextFont1);
            ph1.add(c144);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(10);
            ph1 = new Phrase();
            Chunk c145 = new Chunk("Your next payment of "+amountInfo.getNextPay()+" is due on " + amountInfo.getNextPayDate()+ ".",
                    littleTextFont1);
            ph1.add(c145);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(10);
            ph1 = new Phrase();
            Chunk c146 = new Chunk("Please call (866) 935-4855, Ext. 104 if you have any questions or concerns.",
                    littleTextFont1);
            ph1.add(c146);
            p1.add(ph1);
            doc.add(p1);




            p1 = new Paragraph();
            p1.setLeading(20);
            ph1 = new Phrase();
            Chunk c181 = new Chunk("*On "+TimeUtil.getStrDate()+" you agreed to all policies and procedures.", redBoldFont);
            ph1.add(c181);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(10);
            ph1 = new Phrase();
            Chunk c183 = new Chunk("1.  Cancel:", littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Leasee has 30 days from the date of account activation to cancel the PC Program. " +
                    "The $100 security deposit shall be refunded upon timely delivery of the Box.  The Box must be " +
                    "in good condition.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("2. Failure to pay: ",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("The leases will be given one 7-day grace period for failure to pay.  " +
                    "The leasee will be considered in breech of contract on the 8th day or any consecutive " +
                    "failures to pay thereafter.  ",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Breach of contract:  Leasee forfeits the security deposit.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("3. Box return:  The Box must be returned within ___ days at the end of the contract " +
                    "period. If we do not receive the Box, Leasee forfeits the right of return of the security deposit.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("4.  Damages: ",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("The Box must be returned in good condition.  We have the right to withhold all, " +
                    "or a  portion thereof, to cover the cost of damages.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Good condition is as follows:",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("a. The Box must be able to power on.  The  Box home screen must appear when turned on.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("b. The unit must be able to connect to an HDMI cable to display onto TV/Monitor.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("c. The bluetooth must be working.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("d. The wi fi must be working.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("e. The ethernet must be working.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("f. The blue/red LED light must be working.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("g.  The Box shall be free of dents and scratches.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("5.  Warranty:  ",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("During the contract period the Box is under warranty.  We will repair the box free " +
                    "of charge or Leasee can exchange the Box.  If exchanged, the Box must be in good condition and " +
                    "damage free.  Leases must pay the shipping costs.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("6.  Upgrade:  ",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Leasee has the right to return the Box for an upgrade.  Leasee will not be " +
                    "charged the extra cost of the upgrade.  However, leasee must sign a new contract.  " +
                    "The exchanged Box must be in good condition and free of damages.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("7. Term and conditions:",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Please refer to website www.ldeufonico.com for details.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(SPACE);
            ph1 = new Phrase();
            c183 = new Chunk("Terms and conditions can be changed at anytime without prior notice.",
                    littleTextFont);
            ph1.add(c183);
            p1.add(ph1);
            doc.add(p1);

            Runtime.getRuntime().exec("chmod 777 " + path);

            return path;
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            return "";
        }finally {
            try {
                if (doc != null) {
                    doc.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            }catch (Exception e){
                logger.error("exception", e);
            }
        }
    }

    public static void copyInvoice(String filePath){
        // copy invoice file
        try{
            File file = new File(filePath);
                String copyFilePath = "/home/static/panel/invoice/";
            FileUtils.copyFileToDirectory(file, new File(copyFilePath));
            Runtime.getRuntime().exec("chmod -R 777 " + copyFilePath);
        }catch (Exception e){
            logger.error("Exception: ", e);
        }
    }

    private static class AmountInfo{
        private String subTotal;
        private String tax;
        private String total;
        private String nextPay;
        private int currentMonth;
        private int totalMonth;
        private String nextPayDate;

        public String getSubTotal() {
            return subTotal;
        }

        public void setSubTotal(String subTotal) {
            this.subTotal = subTotal;
        }

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getNextPay() {
            return nextPay;
        }

        public void setNextPay(String nextPay) {
            this.nextPay = nextPay;
        }

        public int getCurrentMonth() {
            return currentMonth;
        }

        public void setCurrentMonth(int currentMonth) {
            this.currentMonth = currentMonth;
        }

        public int getTotalMonth() {
            return totalMonth;
        }

        public void setTotalMonth(int totalMonth) {
            this.totalMonth = totalMonth;
        }

        public String getNextPayDate() {
            return nextPayDate;
        }

        public void setNextPayDate(String nextPayDate) {
            this.nextPayDate = nextPayDate;
        }

        @Override
        public String toString() {
            return "AmountInfo{" +
                    "subTotal='" + subTotal + '\'' +
                    ", tax='" + tax + '\'' +
                    ", total='" + total + '\'' +
                    ", nextPay='" + nextPay + '\'' +
                    ", currentMonth=" + currentMonth +
                    ", totalMonth=" + totalMonth +
                    ", nextPayDate='" + nextPayDate + '\'' +
                    '}';
        }
    }

    private static AmountInfo getTotalAmount(List<InvoiceInfo> invoiceInfoList) {
        AmountInfo amountInfo = new AmountInfo();
        float tax = 0F;
        float amount = 0F;
        for (InvoiceInfo invoiceInfo : invoiceInfoList) {
            if (invoiceInfo.isTax()) {
                tax += invoiceInfo.getAmount() * RATE_TAX;
                amountInfo.setCurrentMonth(invoiceInfo.getCurrentMonth());
                amountInfo.setTotalMonth(invoiceInfo.getTotalMonth());
                amountInfo.setNextPayDate(TimeUtil.getExpiresDate(TimeUtil.getStrTime(), 1));
                amountInfo.setNextPay(UnitUtil.round(invoiceInfo.getAmount()));
            }
            amount += invoiceInfo.getAmount();
        }
        amountInfo.setSubTotal(UnitUtil.round(amount));
        amountInfo.setTax(UnitUtil.round(tax));
        amountInfo.setTotal(UnitUtil.round(amount + tax));
        return amountInfo;
    }

    /**
     * 创建单元格
     * @param table
     * @param row
     * @param cols
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    private static PdfPTable createCell(PdfPTable table, String[] title, int row, int cols) throws DocumentException, IOException{
        BaseFont bfChinese=BaseFont.createFont("Helvetica", "Cp1252", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese,11,Font.BOLD);

        for(int i = 0; i < row; i++){

            for(int j = 0; j < cols; j++){

                PdfPCell cell = new PdfPCell();
                //设置表头
                if(i==0 && title!=null){
                    //表头居中
                    cell = new PdfPCell(new Phrase(title[j], font));
                    if(table.getRows().size() == 0){
                        cell.setBorderWidthTop(3);
                    }
                }
                //只有一行一列
                if(row==1 && cols==1){
                    cell.setBorderWidthTop(3);
                }
                //设置左边的边框宽度
                if(j==0){
                    cell.setBorderWidthLeft(3);
                }
                //设置右边的边框宽度
                if(j==(cols-1)){
                    cell.setBorderWidthRight(3);
                }
                //设置底部的边框宽度
                if(i==(row-1)){
                    cell.setBorderWidthBottom(3);
                }
                //设置单元格高度
                cell.setMinimumHeight(40);
                //设置可以居中
                cell.setUseAscender(true);
                //设置水平居中
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                //设置垂直居中
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);

                table.addCell(cell);
            }
        }

        return table;
    }

    /**
     * 加水印（字符串）
     * @param inputFile 需要加水印的PDF路径
     * @param waterMarkName 水印字符
     */
    public static void stringWaterMark(String inputFile, String waterMarkName) {
        try {
            String[] spe = DataUtil.separatePath(inputFile);
            String outputFile = spe[0] + "_WM." + spe[1];

            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));
            //添加中文字体
            BaseFont bfChinese=BaseFont.createFont("Helvetica", "Cp1252", BaseFont.NOT_EMBEDDED);
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;
            int j = waterMarkName.length();
            char c = 0;
            int rise = 0;
            //给每一页加水印
            for (int i = 1; i < total; i++) {
                rise = 400;
                under = stamper.getUnderContent(i);
                under.beginText();
                under.setFontAndSize(bfChinese, 30);
                under.setTextMatrix(200, 120);
                for (int k = 0; k < j; k++) {
                    under.setTextRise(rise);
                    c = waterMarkName.charAt(k);
                    under.showText(c + "");
                }
                // 添加水印文字
                under.endText();
            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加水印（图片）
     * @param inputFile 需要加水印的PDF路径
     * @param imageFile 水印图片路径
     */
    public static void imageWaterMark(String inputFile, String imageFile) {
        try {
            String[] spe = DataUtil.separatePath(inputFile);
            String outputFile = spe[0] + "_WM." + spe[1];

            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

            int total = reader.getNumberOfPages() + 1;

            Image image = Image.getInstance(imageFile);
            //坐标
            image.setAbsolutePosition(-100, 0);
            //自定义大小
            image.scaleAbsolute(800,1000);
            //image.setRotation(-20);//旋转 弧度
            //image.setRotationDegrees(-45);//旋转 角度
            //image.scalePercent(50);//依照比例缩放

            PdfGState gs = new PdfGState();
            // 设置透明度为0.2
            gs.setFillOpacity(0.2f);

            PdfContentByte under;
            //给每一页加水印
            for (int i = 1; i < total; i++) {
                under = stamper.getUnderContent(i);
                under.beginText();
                // 添加水印图片
                under.addImage(image);
                under.setGState(gs);
            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置左边距
     * @param str
     * @param i
     * @return
     */
    public static String leftPad(String str, int i) {
        int addSpaceNo = i-str.length();
        String space = "";
        for (int k=0; k<addSpaceNo; k++){
            space= " "+space;
        };
        String result =space + str ;
        return result;
    }

    /**
     * 设置模拟数据
     * @param list
     * @param num
     */
    public static void add(List<String> list,int num){
        for(int i=0;i<num;i++){
            list.add("test"+i);
        }
    }

    /**
     * 设置间距
     * @param tmp
     * @return
     */
    public static String printBlank(int tmp){
        String space="";
        for(int m=0;m<tmp;m++){
            space=space+" ";
        }
        return space;
    }


}
