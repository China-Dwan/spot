package springboottest.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springboottest.aop.Sout;
import springboottest.mapper.PUserMapper;
import springboottest.pojo.PUser;
import springboottest.service.PUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/guest/users")
@Slf4j
public class PUserController {

    @Resource
    private PUserService pUserService;
    @Resource
    private PUserMapper mapper;


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public JSONObject login(@RequestParam(name = "username") String username,@RequestParam(name = "password") String password) {
        log.info("查找用户开始");
        JSONObject result = new JSONObject();

        PUser user = pUserService.findByUsername(username);


        return result;
    }

    @RequestMapping("/getUser2")
    public JSONObject getUser2(HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();


        return null;
    }

    @RequestMapping("/{appId}/getParam")
    public String getParam(@PathVariable(name = "appId") String appId) {
        List map = mapper.myA();
        return appId;
    }

    @RequestMapping("/getAnnotation")
    public Object getAnnotation() {
        List result = pUserService.getUserAnnotation();
        return result;
    }


    @RequestMapping("/getCut")
    @Sout(s1 = "a",s2 = "aa")
    public void testCut() {
        System.out.println("执行方法");
    }

}
