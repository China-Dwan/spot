package springboottest.business.importexcel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ImportExcelUtil {

    private final static String EXCEL_VERSION_03 = "xls";
    private final static String EXCEL_VERSION_07 = "xlsx";


    public static synchronized void importExcel() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void multipartFile(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        readExcelTitle(workbook, new VO());
    }

    public static void httpFile(HttpServletRequest request) throws Exception {
        //校验文件请求
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("文件有错误！");
        }
        //获取文件
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        MultipartFile multipartFile = multipartRequest.getFile("file");

        //读取文件内容
        File file = FileUtils.convertMultFileToFile(multipartFile);
        Workbook workbook = getWorkBook(file);
        readExcelTitle(workbook, new VO());
    }

    /**
     * 获取工作簿
     * @param file
     * @return
     * @throws Exception
     */
    public static Workbook getWorkBook(File file) throws Exception {
        if (Objects.isNull(file)) {
            throw new Exception("导入文件为空");
        }

        String fileName = file.getName();
        if (!fileName.endsWith(EXCEL_VERSION_03) && !fileName.endsWith(EXCEL_VERSION_07)) {
            throw new Exception("不是EXCEL文件");
        }


        InputStream in = new FileInputStream(file);
        Workbook workbook = null;
        if (fileName.endsWith(EXCEL_VERSION_03)) {
            workbook = new HSSFWorkbook(in);
        } else if (fileName.endsWith(EXCEL_VERSION_07)) {
            workbook = new XSSFWorkbook(in);
        }
        return workbook;
    }

    /**
     * 根据Excel表头读取内容
     * 使用要求: 表头列属性与定义的JavaBean属性顺序一致
     * 所有的内容单元格格式均需设置为文本,JavaBean属性格式均为String
     * */
    public static <T> List<T> readExcelTitle(Workbook workbook, T importVO) throws Exception {
        //sheet页
        Sheet sheet = workbook.getSheetAt(0);
        //标题行
        Row titleRow = sheet.getRow(0);

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

        //读取行数据
        for (int i=0; i<sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i+1);

            //封装VO
            for (int j=0; j<titleNum; j++) {
                fields[j].setAccessible(true);
                //当前读取属性字段
                Cell cell = row.getCell(j);
                try {
                    String property = "";
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            property = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                Date date = cell.getDateCellValue();
                                property = dateFormat.format(date);
                            } else {
                                property = decimalFormat.format(cell.getNumericCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                    fields[j].set(importVO,property);
                } catch (Exception e) {
                    fields[j].set(importVO,null);
                    log.error("读取excel表格内容{}行{}列发生错误",i+2,j+1);
                }
            }

            //收集结果
            importList.add(importVO);
            importVO = (T) importVO.getClass().newInstance();
        }

        return importList;
    }
}
