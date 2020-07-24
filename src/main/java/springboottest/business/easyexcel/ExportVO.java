package springboottest.business.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ExportVO implements Serializable {

    @ExcelProperty(value = "字段1", index = 0)
    private String column1;

    @ExcelProperty(value = "字段2", index = 1)
    private String column2;

    @ExcelProperty(value = "字段3", index = 2)
    private Integer column3;

    public ExportVO(String column1, String column2, Integer column3) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
    }
}
