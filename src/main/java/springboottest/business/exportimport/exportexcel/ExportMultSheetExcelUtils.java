package springboottest.business.exportimport.exportexcel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 支持  单sheet页  多sheet页  导出
 * 导出结果为07版  .xlsx
 * @author dzh
 */
@Slf4j
public class ExportMultSheetExcelUtils {
    @Data
    private static class PassData {
        /**
         * 工作簿
         */
        private XSSFWorkbook wb;
        /**
         * 导出文件全路径名称
         */
        private String fileUrl;

        public PassData(XSSFWorkbook wb, String fileUrl) {
            this.wb = wb;
            this.fileUrl = fileUrl;
        }
    }

    private static final String FILE_NAME_REGEX = "@([\\S\\s]*.?)@";
    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("@([\\S\\s]*.?)@");


    /**
     * 根据自定义表头导出数据
     * @param url 参数组成: {导出excel路径 + 文件名称}  ,  常见入参格式: { /xx/xx/xx/@fileName:文件名@ , /xx/xx/xx/}
     */
    public static void export(List<ExportDataVO> exportData, String url) throws Exception {
        PassData workBook = getWorkBook(exportData, url);
        SXSSFWorkbook wb = new SXSSFWorkbook(workBook.getWb(), 1000);
        if (CollectionUtils.isNotEmpty(exportData)) {
            String fileUrl = "";
            //填充工作簿数据
            for (int i = 0; i < exportData.size(); i++) {
                ExportDataVO data = exportData.get(i);
                wb = exportCellByHeader(wb, i, data.getList(), data.getHeader(), workBook.getFileUrl());
                fileUrl = workBook.getFileUrl();
            }

            //写出工作簿
            writeOut(wb, fileUrl);
        }
    }

    /**
     * 获取工作簿,并写出工作簿,包括sheet页  表头
     */
    private static PassData getWorkBook(List<ExportDataVO> exportData, String url) {
        XSSFWorkbook workbook =  null;
        BufferedOutputStream outputStream = null;
        //导出文件全路径名称
        String fileUrlName = "";
        try {
            //导出文件名称
            String fileName = "";
            Matcher m = FILE_NAME_PATTERN.matcher(url);
            if(m.find()){
                fileName = m.group().replaceAll("@","").replaceAll("fileName:","");
                //导出文件上层文件夹路径
                url = url.replaceAll(FILE_NAME_REGEX,"");
            }

            //如果文件夹不存在则创建
            File file = new File(url);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            //定义时间戳
            Long times = System.currentTimeMillis();
            //拼接导出文件全路径名称
            fileUrlName = url + (StringUtils.isNotEmpty(fileName)?fileName:times) + ".xlsx";
            //创建文件
            File exportFile = new File(fileUrlName);
            outputStream = new BufferedOutputStream(new FileOutputStream(exportFile));
            workbook = new XSSFWorkbook();
            //工作簿创建sheet页和表头
            if (CollectionUtils.isNotEmpty(exportData)) {
                for (int i = 0; i < exportData.size(); i++) {
                    ExportDataVO data = exportData.get(i);
                    workbook = exportHeader(workbook, i, data.getHeader(), data.getSheetName());
                }
            }
            //写出工作簿
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        PassData passData = new PassData(workbook, fileUrlName);
        return passData;
    }

    /**
     * 导出表头
     */
    private static XSSFWorkbook exportHeader(XSSFWorkbook wb, int sheetIndex, String[] header, String sheetName) {
        //第一步，在webbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = wb.createSheet();
        wb.setSheetName(sheetIndex, sheetName);
        //第二步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        XSSFRow row = sheet.createRow((int) 0);
        //第三步，创建单元格，并设置值表头 设置表头居中
        XSSFCellStyle style = wb.createCellStyle();
        //创建一个居中格式
        //style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //判断表头是否为空
        if (header != null) {
            for (int i = 0; i < header.length; i++) {
                XSSFCell cell = row.createCell((short) i);
                cell.setCellValue(header[i]);
                cell.setCellStyle(style);
            }
        }
        return wb;
    }

    /**
     * 导出数据
     */
    private static SXSSFWorkbook exportCellByHeader(SXSSFWorkbook wb, int sheetIndex, List<?> list, String[] header, String fileUrl) throws Exception {
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (null == list || list.size() <= 0) {
            return wb;
        }
        //list中对象
        Object obj;
        //获取list中的对象所有属性的集合
        Field[] fields;
        //行
        Row row;
        BufferedOutputStream outputStream = null;
        try {
            for (int i = 0; i < list.size(); i++) {
                //拿到当前的对象
                obj = list.get(i);
                //拿到全类名,通过反射获取这个类
                Class<?> clazz = Class.forName(obj.getClass().getCanonicalName());
                //得到所有属性的集合
                fields = clazz.getDeclaredFields();
                //收集需要导出的字段
                HashMap<String, Field> fieldMap = new HashMap<>();
                for (Field field : fields) {
                    Export annotation = field.getAnnotation(Export.class);
                    if (annotation!=null && annotation.isExport()) {
                        fieldMap.put(annotation.showHeader(), field);
                    }
                }
                //创建行
                row = sheet.createRow( i+1);
                //创建单元格，并设置值
                for (int j = 0; j < header.length; j++) {
                    //获取当前表头列对应的字段
                    Field field = fieldMap.get(header[j]);
                    if (field == null) {
                        continue;
                    }
                    //填充单元格
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    Method rm = pd.getReadMethod();
                    if (rm.invoke(obj) instanceof Integer) {
                        Integer number = (Integer) rm.invoke(obj);
                        row.createCell((short) j).setCellValue(number);
                    } else if (rm.invoke(obj) instanceof String) {
                        String str = (String) rm.invoke(obj);
                        row.createCell((short) j).setCellValue(str);
                    } else if (rm.invoke(obj) instanceof BigDecimal) {
                        BigDecimal bd = (BigDecimal) rm.invoke(obj);
                        bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                        row.createCell(j).setCellValue(bd.doubleValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

    private static void writeOut(SXSSFWorkbook wb, String fileUrl) {
        BufferedOutputStream outputStream = null;

        try {
            //写出
            outputStream = new BufferedOutputStream(new FileOutputStream(fileUrl));
            wb.write(outputStream);
            outputStream.flush();
            wb.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
