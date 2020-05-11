package springboottest.business.exportpdf.pojo;

import com.itextpdf.text.Document;

import java.io.OutputStream;

/**
 * @Author laixiaoxing
 * @Description pdf工具基础类
 * @Date 下午11:51 2019/2/18
 */
public class PdfTool {
    //
    protected Document document;
    //
    protected OutputStream os;

    public Document getDocument() {
        if (document == null) {
            document = new Document();
        }
        return document;
    }
}