package springboottest.business.exportpdf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springboottest.mapper.PUserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/guest/users")
@Slf4j
public class PDFController {

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
