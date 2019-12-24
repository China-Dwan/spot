package springboottest.business.exportpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayOutputStream;

public class ExportPDFUtil {

    public static void test(String[] titles, ServletOutputStream outputStream) {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        PdfWriter pdfWriter;
        try {
            pdfWriter = PdfWriter.getInstance(document, ba);
            document.open();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.setMargins(5,5,5,5);

        try {
            //空行
            Paragraph blankRow1 = new Paragraph(18f, " ");
            blankRow1.setAlignment(Element.ALIGN_CENTER);

            //创建一个表格,2为一行有几栏
            PdfPTable table1 = new PdfPTable(titles.length);
            //每栏的宽度
            int width1[] = {55,50,70};
            //设置宽度
            table1.setWidths(width1);

            //首行
            for(int i=0;i<titles.length;i++){
                PdfPCell cell1 = new PdfPCell(new Paragraph(titles[i]));
                table1.addCell(cell1);
            }

            //将表格加入到document中
            document.add(table1);
            document.add(blankRow1);

            document.close();
            ba.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();

            ba.close(); // 导出pdf注解
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
