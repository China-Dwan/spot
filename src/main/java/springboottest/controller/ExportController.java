package springboottest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.mapper.ExportMapper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Resource
    private ExportMapper exportMapper;

    @RequestMapping("/data")
    public void export() throws Exception {
        HashMap<String, Object> map = new HashMap<>();

        List<Map<String, String>> list = exportMapper.exportBySQL();
        map.put("list",list);


//        ExportExcelUtil.exportExcelByTemplate("d:\\保额消费明细模板.xls",map,"d:\\");
    }


}
