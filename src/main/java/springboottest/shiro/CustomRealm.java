package springboottest.shiro;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import springboottest.pojo.PUser;
import springboottest.service.PUserService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Resource
    private PUserService pUserService;


    /**
     * 定义如何获取用户的角色和权限逻辑,给shiro做权限判断
     * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        PUser pUser = (PUser) getAvailablePrincipal(principalCollection);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        pUser = pUserService.findByUsername(pUser.getUsername());
        Set<String> roleSet = new HashSet<>();
        Set<String> permissionSet = new HashSet<>();

        roleSet.add(pUser.getRole().getRoleName());
        pUser.getPermissionList().forEach(pPermission -> permissionSet.add(pPermission.getPermissionCode()));

        authorizationInfo.setRoles(roleSet);
        authorizationInfo.setStringPermissions(permissionSet);

        return authorizationInfo;
    }

    /**
     * 定义如何获取用户信息的业务逻辑,给shiro做登录
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();

        log.info("用户名开始登陆: " + username);

        if (StringUtils.isEmpty(username))
            throw new AccountException("Null username are not allowed");
        PUser pUser = pUserService.findByUsername(username);
        if (pUser == null)
            throw new AccountException("No account for nickname " + username);

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(pUser, pUser.getPassword(), pUser.getUsername());

        log.info("用户登陆结束: " + JSONObject.toJSONString(authenticationInfo));
        return authenticationInfo;
    }
}
