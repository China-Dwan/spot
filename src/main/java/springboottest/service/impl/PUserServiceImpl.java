package springboottest.service.impl;

import org.springframework.stereotype.Service;
import springboottest.mapper.PUserMapper;
import springboottest.pojo.PUser;
import springboottest.service.PUserService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PUserServiceImpl implements PUserService {

    @Resource
    private PUserMapper pUserMapper;


    @Override
    public PUser findByUsername(String username) {
        return pUserMapper.findByUsername(username);
    }

    @Override
    public List getUserAnnotation() {
        ArrayList<String> list = new ArrayList<>();

        return list;
    }
}
