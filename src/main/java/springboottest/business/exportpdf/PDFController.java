package springboottest.business.exportpdf;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springboottest.business.aop.Sout;
import springboottest.business.aop.Sout2;
import springboottest.mapper.PUserMapper;
import springboottest.pojo.PUser;
import springboottest.service.PUserService;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/guest/users")
@Slf4j
public class PDFController {

    @Resource
    private PUserService pUserService;
    @Resource
    private PUserMapper mapper;



    @RequestMapping("/pdf")
    public void ss(HttpServletResponse response) {

        try {
//            response.setHeader("Content-disposition", "attachment; filename=aa.pdf");
//            ServletOutputStream outputStream = response.getOutputStream();
//            String[] titles = {"11","22","33"};
//            ExportPDFUtil.test(titles, outputStream);
            ConvertFileUtil.convert();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
