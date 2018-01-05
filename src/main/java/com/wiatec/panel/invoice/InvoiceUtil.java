package com.wiatec.panel.invoice;


import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;
import com.wiatec.panel.common.utils.PathUtil;
import com.wiatec.panel.common.utils.TimeUtil;
import com.wiatec.panel.common.utils.UnitUtil;


public class InvoiceUtil {

    private static final float RATE_TAX = 0.08F;

    public static void main(String[] args) throws Exception {
        String pdf = createInvoice("patrickxu@wiatec.com", InvoiceInfo.B1Contracted());
        System.out.println(pdf);
    }

    private static String outPath = "/Users/xuchengpeng/IdeaProjects/panel/src/main/resources/invoice/";

    public static void setPath(String path){
        outPath = path;
    }

    /**
     * 创建PDF文档
     * @return
     * @throws Exception
     */
    public static String createInvoice(String email, List<InvoiceInfo> invoiceInfoList) throws Exception {
        FileOutputStream fileOutputStream = null;
        Document doc = null;
        try {
            Rectangle rect = new Rectangle(PageSize.A4);            //设置纸张
            doc = new Document(rect);            //创建文档实例
            BaseFont bfChinese = BaseFont.createFont("Helvetica", "Cp1252", BaseFont.NOT_EMBEDDED);
            //设置字体样式
            Font textFont = new Font(bfChinese, 10, Font.NORMAL); //正常
            Font redTextFont = new Font(bfChinese, 11, Font.NORMAL, Color.RED); //正常,红色
            Font boldFont = new Font(bfChinese, 11, Font.BOLD); //加粗
            Font bold12Font = new Font(bfChinese, 12, Font.BOLD); //加粗
            Font redBoldFont = new Font(bfChinese, 11, Font.BOLD, Color.RED); //加粗,红色
            Font firstTitleFont = new Font(bfChinese, 22, Font.BOLD); //一级标题
            Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); //二级标题
            Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); //下划线斜体
            Font underlineRedFont = new Font(bfChinese, 11, Font.UNDERLINE, Color.RED);

            //手指图片
            //Image hand = Image.getInstance("/Users/xuchengpeng/IdeaProjects/panel/src/main/webapp/Resource/img/btv.ico");

            //创建输出流
            String fileName = System.currentTimeMillis() + TimeUtil.getStrTime() + ".pdf";
            String path = outPath + fileName;
            File file = new File(path);
            fileOutputStream = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fileOutputStream);

            doc.open();
            doc.newPage();

            Paragraph p1 = new Paragraph();            //段落
            Phrase ph1 = new Phrase();            //短语
            Chunk c1 = new Chunk("Invoice", firstTitleFont);            //块
            ph1.add(c1);            //将块添加到短语
            p1.add(ph1);            //将短语添加到段落
            p1.setLeading(50);
            doc.add(p1);            //将段落添加到文档


            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c2 = new Chunk("LD Eufonico LLC", boldFont);
            ph1.add(c2);
            p1.add(ph1);
            p1.setLeading(40);
            doc.add(p1);

            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c22 = new Chunk("Phone: 949-988-9000", boldFont);
            ph1.add(c22);
            p1.setAlignment("Right");
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c222 = new Chunk("1221 E. Dyer Road Unit 110", boldFont);
            ph1.add(c222);
            p1.setAlignment("Right");
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            ph1 = new Phrase();
            Chunk c223 = new Chunk("Santa Ana, CA92705", boldFont);
            ph1.add(c223);
            p1.setAlignment("Right");
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(40);
            ph1 = new Phrase();
            Chunk c3 = new Chunk("TO: ", bold12Font);
            Chunk c33 = new Chunk(email, underlineRedFont);
            ph1.add(c3);
            ph1.add(c33);
            p1.add(ph1);
            doc.add(p1);

            p1 = new Paragraph();
            p1.setLeading(30);
            ph1 = new Phrase();
            Chunk c13 = new Chunk("  ", secondTitleFont);
            ph1.add(c13);
            p1.add(ph1);
            doc.add(p1);

            PdfPTable table = new PdfPTable(5); // 创建一个有5列的表格
            table.setTotalWidth(new float[]{110, 110, 110, 110, 110}); //设置列宽
            table.setLockedWidth(true); //锁定列宽

            PdfPCell cell = new PdfPCell(new Phrase("DIST. #", bold12Font));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("DATE", bold12Font));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("INVOICE #", bold12Font));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SHIP VIA", bold12Font));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("SALE TYPE", bold12Font));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("~", textFont));
            cell.setMinimumHeight(30); //设置单元格高度
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(TimeUtil.getStrDate(), textFont));
            cell.setMinimumHeight(30);
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase(" ~sku~ ", textFont));
            cell.setMinimumHeight(30);
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("~", textFont));
            cell.setMinimumHeight(30);
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("PCP", textFont));
            cell.setMinimumHeight(30);
            cell.setUseAscender(true); //设置可以居中
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中
            table.addCell(cell);
            doc.add(table);

            p1 = new Paragraph();
            p1.setLeading(30);
            ph1 = new Phrase();
            Chunk c18 = new Chunk("  ", boldFont);
            ph1.add(c18);
            p1.add(ph1);
            doc.add(p1);

            table = new PdfPTable(5);
            table.setTotalWidth(new float[]{80, 210, 60, 100, 100});
            table.setLockedWidth(true);
            cell = new PdfPCell(new Phrase("ITEM #", boldFont));
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
            cell = new PdfPCell(new Phrase("QTY", boldFont));
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

            for (InvoiceInfo invoiceInfo : invoiceInfoList) {
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
                cell = new PdfPCell(new Phrase("$" + invoiceInfo.getPrice(), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("$" + invoiceInfo.getAmount(), textFont));
                cell.setMinimumHeight(20);
                cell.setUseAscender(true);
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            int length = 15 - invoiceInfoList.size();
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
            }

            AmountInfo amountInfo = getTotalAmount(invoiceInfoList);
            cell = new PdfPCell(new Phrase("  ", textFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setColspan(3);
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
            cell.setColspan(3);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("TOTAL", boldFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("$" + amountInfo.getTotal(), redTextFont));
            cell.setMinimumHeight(20);
            cell.setUseAscender(true);
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(cell);

            doc.add(table);

            p1 = new Paragraph();
            p1.setLeading(30);
            ph1 = new Phrase();
            Chunk c181 = new Chunk("*On ~orderdate~ you agreed to all policies and procedures.", redBoldFont);
            ph1.add(c181);
            p1.add(ph1);
            doc.add(p1);


            Runtime.getRuntime().exec("chmod 755 " + outPath);
            return path;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }finally {
            if(doc != null){
                doc.close();
            }
            if(fileOutputStream != null){
                fileOutputStream.close();
            }
        }
    }

    private static class AmountInfo{
        private float tax;
        private float total;

        public float getTax() {
            return tax;
        }

        public void setTax(float tax) {
            this.tax = tax;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return "AmountInfo{" +
                    "tax=" + tax +
                    ", total=" + total +
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
            }
            amount += invoiceInfo.getAmount();
        }
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
        //添加中文字体
//        BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        BaseFont bfChinese=BaseFont.createFont("Helvetica", "Cp1252", BaseFont.NOT_EMBEDDED);
        Font font = new Font(bfChinese,11,Font.BOLD);

        for(int i = 0; i < row; i++){

            for(int j = 0; j < cols; j++){

                PdfPCell cell = new PdfPCell();

                if(i==0 && title!=null){//设置表头
                    cell = new PdfPCell(new Phrase(title[j], font)); //这样表头才能居中
                    if(table.getRows().size() == 0){
                        cell.setBorderWidthTop(3);
                    }
                }

                if(row==1 && cols==1){ //只有一行一列
                    cell.setBorderWidthTop(3);
                }

                if(j==0){//设置左边的边框宽度
                    cell.setBorderWidthLeft(3);
                }

                if(j==(cols-1)){//设置右边的边框宽度
                    cell.setBorderWidthRight(3);
                }

                if(i==(row-1)){//设置底部的边框宽度
                    cell.setBorderWidthBottom(3);
                }

                cell.setMinimumHeight(40); //设置单元格高度
                cell.setUseAscender(true); //设置可以居中
                cell.setHorizontalAlignment(Cell.ALIGN_CENTER); //设置水平居中
                cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); //设置垂直居中

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
//            BaseFont bfChinese=BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
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
            image.setAbsolutePosition(-100, 0);//坐标
            image.scaleAbsolute(800,1000);//自定义大小
            //image.setRotation(-20);//旋转 弧度
            //image.setRotationDegrees(-45);//旋转 角度
            //image.scalePercent(50);//依照比例缩放

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.2f);// 设置透明度为0.2


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
