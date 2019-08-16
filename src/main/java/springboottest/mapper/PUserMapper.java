package springboottest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import springboottest.pojo.PPermission;
import springboottest.pojo.PRole;
import springboottest.pojo.PUser;

import java.util.List;
import java.util.Map;


@Mapper
public interface PUserMapper {

    PUser findByUsername(@Param(value = "username")String username);
    PPermission findPermissionsPyRoleId(@Param(value = "p_role_id")String p_role_id);
    PRole findRoleById(@Param(value = "p_role_id")String p_role_id);

    List<Map> myA();
}
