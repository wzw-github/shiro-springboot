package com.wzw.shirospringboot.realm;

import com.wzw.shirospringboot.entity.User;
import com.wzw.shirospringboot.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 为当前用户授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1、获取当前登录的用户信息
        String userName = (String) principals.getPrimaryPrincipal();
        //2、创建授权验证对象
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        try {
            //3、授予角色
            authorizationInfo.setRoles(userService.getRoles(userName));
            //4、授予权限
            authorizationInfo.setStringPermissions(userService.getPermissions(userName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //5、返回授权信息
        return authorizationInfo;
    }

    /**
     *为当前登录用户身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1、获取用户名
        String userName = (String) token.getPrincipal();
        //2、根据用户名判断是否存在该用户
        User user=userService.getByUserName(userName);
        //3、如果不为空，表示该用户存在,就进行身份验证
        if(user!=null){
            //4、身份验证
            AuthenticationInfo authenticationInfo=
                    new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"xxx");
            return  authenticationInfo;
        }
        //如果User是空，表示没有该用户
        return null;
    }
}
