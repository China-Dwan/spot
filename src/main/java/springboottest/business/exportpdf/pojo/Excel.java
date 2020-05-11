package springboottest.business.exportpdf.pojo;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @Author laixiaoxing
 * @Description excel数据实体
 * @Date 下午11:47 2019/2/18
 */
public class Excel {

    protected Workbook wb;
    protected Sheet sheet;

    public Excel(Workbook wb ) {
        try {
            this.wb = wb;
            this.sheet = wb.getSheetAt(wb.getActiveSheetIndex());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public Sheet getSheet() {
        return sheet;
    }

    public Workbook getWorkbook(){
        return wb;
    }
}
