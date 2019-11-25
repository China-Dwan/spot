package springboottest.business.importexcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImportExcelUtil {

    public static synchronized void importExcel(HttpServletRequest request) {
        //校验文件
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("文件有错误！");
        }

        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile files = multipartRequest.getFile("file");
//        readExcelTitle(files.getInputStream(),new VO());
    }



    /**
     * 根据Excel表头读取内容
     * 使用要求: 表头列属性与定义的JavaBean属性顺序一致
     * 所有的内容单元格格式均需设置为文本,JavaBean属性格式均为String
     * */
    public static <T> List<T> readExcelTitle(InputStream in, T importVO) throws Exception {
        //流转换为excel
        POIFSFileSystem fs = new POIFSFileSystem(in);
        HSSFWorkbook workbook = new HSSFWorkbook(fs);//excel
        HSSFSheet sheet = workbook.getSheetAt(0);//sheet页
        HSSFRow titleRow = sheet.getRow(0);//标题行

        //需要读取的实体类
        Class<?> clazz = Class.forName(importVO.getClass().getCanonicalName());
        Field[] fields = clazz.getDeclaredFields();

        //校验对象属性数量与excel表格列数量比较
        int titleNum = titleRow.getPhysicalNumberOfCells();
        if (fields.length != titleNum) {
            return null;
        }

        //读取的导入信息
        List<T> importList = new ArrayList<>();

        //格式化数字
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        //格式化时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //读取数据
        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            HSSFRow row = sheet.getRow(i + 1);

            for (int j = 0; j < titleNum; j++) {
                fields[j].setAccessible(true);

                //当前读取属性字段
                HSSFCell cell = row.getCell(j);

                try {
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_STRING:
                            String string = cell.getRichStringCellValue().getString();
                            fields[j].set(importVO,string);
                            break;
                    }
                } catch (Exception e) {
                    fields[j].set(importVO,null);
                    log.error("读取excel表格内容{}行{}列发生错误",i+2,j+1);
                }

                //添加进结果集
                importList.add(importVO);
                importVO = (T) importVO.getClass().newInstance();
            }
        }

        return importList;
    }
}
