package springboottest.business.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dzh
 */
public class EasyExcelExportUtil {

    public static void main(String[] args) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        List<ExportVO> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list.add(new ExportVO(String.valueOf(i), String.valueOf(i), String.valueOf(i)));
        }
        map.put("list1", list);
        map.put("list2", list);
        map.put("title", "ss");
        InputStream in = new ClassPathResource("templates/template.xlsx").getInputStream();
        templateExport(map, in, "d:\\hello.xlsx");

//        localExport(list);
    }

    /**
     * 本地导出
     */
    public static void localExport(List<ExportVO> list) throws Exception {
        File file = new File("d:\\easy.xlsx");
        FileOutputStream out = new FileOutputStream(file);
        ExcelWriter excelWriter = EasyExcel.write(out).build();

        WriteSheet sheetWriter = EasyExcel.writerSheet(0, "sheet1页名称").head(ExportVO.class).build();
        excelWriter.write(list, sheetWriter);

        sheetWriter = EasyExcel.writerSheet(1, "sheet2页名称").head(ExportVO.class).build();
        excelWriter.write(list, sheetWriter);

        // 关闭流
        excelWriter.finish();
    }

    /**
     * web 导出
     */
    public static void webExport(HttpServletResponse response, List<ExportVO> list) throws Exception {
        String fileName = URLEncoder.encode("导出文件名", "UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

        WriteSheet sheetWriter = EasyExcel.writerSheet(0, "sheet1页名称").head(ExportVO.class).build();
        excelWriter.write(list, sheetWriter);

        sheetWriter = EasyExcel.writerSheet(1, "sheet2页名称").head(ExportVO.class).build();
        excelWriter.write(list, sheetWriter);

        // 关闭流
        excelWriter.finish();
    }

    public static void templateExport(Map<String, Object> map, InputStream in, String outPath) {
        try {
            FileOutputStream out = new FileOutputStream(outPath);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            ExcelWriter excelWriter = EasyExcel.write(bos).withTemplate(in).build();

            WriteSheet sheetWriter = EasyExcel.writerSheet(0).build();
            excelWriter.fill(map.get("data"), sheetWriter);
            excelWriter.fill(map.get("list1"), sheetWriter);

            sheetWriter = EasyExcel.writerSheet(1).build();
            excelWriter.fill(map.get("data"), sheetWriter);
            excelWriter.fill(map.get("list2"), sheetWriter);

            // 关闭流
            excelWriter.finish();
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
