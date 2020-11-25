package com.business.exportimport.word;

/**
 * 官方文档  http://deepoove.com/poi-tl/
 */

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.deepoove.poi.util.BytePictureUtils;

import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;

/**
 * <dependency>
 *             <groupId>org.apache.poi</groupId>
 *             <artifactId>poi-ooxml</artifactId>
 *             <version>4.1.0</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>org.apache.poi</groupId>
 *             <artifactId>poi</artifactId>
 *             <version>4.1.2</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>com.deepoove</groupId>
 *             <artifactId>poi-tl</artifactId>
 *             <version>1.6.0-beta1</version>
 *         </dependency>
 */
public class ExportWord {
    public static void main(String[] args) throws Exception {
        XWPFTemplate template = XWPFTemplate.compile("D:\\template.docx").
                render(new HashMap<String, Object>() {{

                    /* 文字 */
                    put("title", "我是标题啊");

                    /* 图片 */
                    put("image", new PictureRenderData(100, 100, ".png", BytePictureUtils.getUrlBufferedImage("https://img-blog.csdnimg.cn/20190627130806508.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM3MTI4MDQ5,size_16,color_FFFFFF,t_70")));

                    /* 表格 */
                    RowRenderData header = RowRenderData.build(new TextRenderData("00CC33", "姓名"), new TextRenderData("00CC33", "学历"));
                    RowRenderData row0 = RowRenderData.build("张三", "研究生");
                    RowRenderData row1 = RowRenderData.build("李四", "博士");
                    RowRenderData row2 = RowRenderData.build("王五", "博士后");
                    put("table", new MiniTableRenderData(header, Arrays.asList(row0, row1, row2)));
                }});

        FileOutputStream out = new FileOutputStream("d:\\out_template.docx");
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
}
