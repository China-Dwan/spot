package springboottest.middleware.mybatisplusgen;

import cn.hutool.core.io.IoUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.service.gen.GenService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/guest/gen")
public class GenController {
    @Resource
    private GenService genService;

    @RequestMapping("/g")
    @SneakyThrows
    public void gen(HttpServletResponse response, String tableName) {
        byte[] data = genService.gen(tableName);
        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s.zip", tableName));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }


    public static void main(String[] args) {
        char[] array = "123456789".toCharArray();
        for (char c : array) {
            System.out.println(c);
        }
    }
}
