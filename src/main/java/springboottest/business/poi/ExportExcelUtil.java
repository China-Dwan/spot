package springboottest.business.poi;

import lombok.extern.slf4j.Slf4j;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Deprecated
public class ExportExcelUtil {

    /**
     *
     * @param url    保存文件的url  "d://"    "/temp/123/"
     * @param exportList    导出数据集合
     * @param <T>    泛型
     */
    public static <T> String exportExcelByAnnotation(String url,List<T> exportList) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();

        if (CollectionUtils.isEmpty(exportList)) {
            workbook = createExcelFile(exportList);
        }

        return exportFile(url,workbook);
    }

    /**
     * excel文件保存至服务器
     * */
    public static String exportFile(String url, HSSFWorkbook workbook) {
        Long times = System.currentTimeMillis();//定义时间戳

        //将文件存到指定位置
        File file = null;
        try {
            file = new File(url);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }

            String tempStr = url + times + ".xls";
            FileOutputStream fout = new FileOutputStream(tempStr);
            workbook.write(fout);
            fout.close();

        } catch (Exception e) {
            log.error("导出文件失败", e);
        }
        return file.getName();
    }

    /**
     * 创建excel文件,并生成数据
     * */
    private static <T> HSSFWorkbook createExcelFile(List<T> exportList) throws Exception {
        if (CollectionUtils.isEmpty(exportList)) {
            return null;
        }

        T firstExportVO = exportList.get(0);
        Class<?> clazz = Class.forName(firstExportVO.getClass().getCanonicalName());
        Field[] fields = clazz.getDeclaredFields();

        List<Export> exportField = new ArrayList<>();
        Map<String, Field> fieldHashMap = new HashMap<>();
        for (Field field : fields) {
            Export annotation = field.getAnnotation(Export.class);
            if (annotation!=null && annotation.isExport()) {
                exportField.add(annotation);
                fieldHashMap.put(annotation.showHeader(),field);
            }
        }
        //导出的字段名称
        List<String> header = exportField.stream().sorted(Comparator.comparing(Export::index)).map(Export::showHeader).collect(Collectors.toList());


        HSSFWorkbook workbook = new HSSFWorkbook();

        //sheet页
        HSSFSheet sheet = workbook.createSheet(firstExportVO.getClass().getSimpleName() + "表");

        //表头创建,并设置居中格式
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleRowStyle = workbook.createCellStyle();
        titleRowStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        if (CollectionUtils.isNotEmpty(header)) {
            int i = 0;
            for (String title : header) {
                HSSFCell cell = titleRow.createCell(i);
                cell.setCellValue(title);
                cell.setCellStyle(titleRowStyle);
                i++;
            }
        } else {
            return null;
        }

        //导出数据
        int dataRowNum = 1;
        for (int i = 1; i <= exportList.size(); i++) {
            T exportVO = exportList.get(i-1);

            HSSFRow dataRow = sheet.createRow(dataRowNum);

            for (int j = 0; j < header.size(); j++) {
                //获取私有字段
                String title = header.get(j);
                Field field = fieldHashMap.get(title);

                //访问私有字段
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), clazz);
                Method readMethod = propertyDescriptor.getReadMethod();

                //设置单元格
                if (readMethod.invoke(exportVO) instanceof Integer) {
                    Integer numberData = (Integer) readMethod.invoke(exportVO);
                    dataRow.createCell(j).setCellValue(numberData);

                } else if (readMethod.invoke(exportVO) instanceof String) {
                    String stringData = (String) readMethod.invoke(exportVO);
                    dataRow.createCell(j).setCellValue(stringData);

                } else if (readMethod.invoke(exportVO) instanceof BigDecimal) {
                    BigDecimal bigDecimalData = (BigDecimal) readMethod.invoke(exportVO);
                    bigDecimalData.setScale(2, BigDecimal.ROUND_HALF_UP);
                    dataRow.createCell(j).setCellValue(bigDecimalData.doubleValue());

                }
            }
        }


        return workbook;
    }




    /**
     * 快捷导出
     * 多台服务器时,使用时需要拼接导出模板文件路径
     */
    public static void exportExcelByTemplate(Map<String, Object> exportData,String templateFilePathWithName,String exportFilePath) throws Exception {
        //创建XLSTransformer对象
        XLSTransformer transformer = new XLSTransformer();

        File file = new File(exportFilePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        //生成Excel文件
        transformer.transformXLS(templateFilePathWithName,exportData,exportFilePath + System.currentTimeMillis() + ".xls");
    }
}
