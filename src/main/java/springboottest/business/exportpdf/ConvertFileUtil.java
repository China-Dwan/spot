package springboottest.business.exportpdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertFileUtil {

    public static void convert() {
        //填充创建pdf
        PdfReader reader = null;
        PdfStamper stamp = null;
        try {
            reader = new PdfReader("d:/11.pdf");
            SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
            String times = simp.format(new Date()).trim();
            //创建生成报告名称
            String root = "d:/";
//            String root = ServletActionContext.getRequest().getRealPath("/upload") + File.separator;
            if (!new File(root).exists()) {
                new File(root).mkdirs();
            }
            File deskFile = new File(root, times + ".pdf");
            stamp = new PdfStamper(reader, new FileOutputStream(deskFile));
            //取出报表模板中的所有字段
            AcroFields form = stamp.getAcroFields();
            // 填充数据
            form.setField("name", "zhangsan");
            form.setField("sex", "男");
            form.setField("age", "15");

            //报告生成日期
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
            String generationdate = dateformat.format(new Date());
            form.setField("generationdate", generationdate);
            stamp.setFormFlattening(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stamp != null) {
                    stamp.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
