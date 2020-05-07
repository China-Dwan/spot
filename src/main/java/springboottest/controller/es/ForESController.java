package springboottest.controller.es;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springboottest.service.es.ForESService;

import javax.annotation.Resource;

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
}
