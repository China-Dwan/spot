package springboottest.business.poi;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ExportDataVO implements Serializable {
    /**
     * 导出数据集合
     */
    private List<?> list;
    /**
     * 导出sheet页表头
     */
    private String[] header;
    /**
     * 导出sheet页名称
     */
    private String sheetName;

    public ExportDataVO(List<?> list, String[] header, String sheetName) {
        this.list = list;
        this.header = header;
        this.sheetName = sheetName;
    }
}
