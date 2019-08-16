package springboottest.service;

import springboottest.pojo.PUser;

import java.util.List;

public interface PUserService {
    PUser findByUsername(String username);
    List getUserAnnotation();

}
