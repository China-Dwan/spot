package springboottest.business.exportpdf;

import com.itextpdf.text.RectangleReadOnly;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @ClassName ExcelConvertPDF
 * @Author laixiaoxing
 * @Date 2019/2/19 上午12:26
 * @Description excel转pdf工具类
 * @Version 1.0
 */
public class ExcelConvertPDF {

    /**
     * excel转为pdf并导出
     *目前不支持多sheet  如果有多个sheet建议分成多个xls文档，可以导出到一个pdf文件里面
     * @param
     * @param out
     * @throws IOException
     */
    public static void ExcelConvertPDF(List<Workbook> workbook, OutputStream out, RectangleReadOnly pageSize) throws Exception {
        List<ExcelObject> objects = new ArrayList<>();
        for (Workbook wb : workbook) {
            objects.add(new ExcelObject(null, wb));
        }
        Excel2Pdf pdf = new Excel2Pdf(objects, out, Boolean.TRUE, pageSize);
        pdf.convert();
    }
}
