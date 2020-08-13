package com.middleware.es;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.service.es.ForESService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/guest/fores")
public class ForESController {

    @Resource
    private ForESService forESService;

    /**
     * 将数据放进es
     */
    @RequestMapping("/in")
    public void in() {
        forESService.in();
    }

    /**
     * 查询es
     */
    @RequestMapping("/select")
    public void select() {
        forESService.select();
    }

    @RequestMapping("/multselect")
    public void multSelect() {
        forESService.multSelect();
    }

    /**
     * 删除es
     */
    @RequestMapping("/delete")
    public void delete() {
        forESService.delete();
    }

    @RequestMapping("/ss")
    public void ss() {
        String[] s = {"aa", "bb", "cc"};
        String ss = JSON.toJSONString(s);
        System.out.println(ss);
        List<String> list = JSONArray.parseArray(ss, String.class);
        System.out.println(list);
    }
}
