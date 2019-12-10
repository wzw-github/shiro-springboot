package com.wzw.shirospringboot.config;

import com.wzw.shirospringboot.realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 1、注入一个userRealm
     * @return
     */
    @Bean
    public UserRealm getUserRealm(){
        return new UserRealm();
    }

    /**
     * 创建ShiroFilterFactoryBean过滤器
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        //创建ShiroFilterFactoryBean对象
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //设置安全会话管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        /**
         * Shiro内置过滤器，可以实现权限相关的拦截器
         * 常用的过滤器
         *      anon: 无需认证（登录）就可以访问
         *      authc:  必须认证才可以访问
         *      user:   如果使用rememberMe的功能可以直接访问
         *      perms:  该资源必须得到资源权限才可以访问
         *      roles:  该资源必须得到角色权限才可以访问
         */
        //创建Map集合，保存各种需要处理的请求
        Map<String,String> filterChainDefinitionMap=new LinkedHashMap<String,String>();
        /******************设置放行anno请求，无需认证登录就可以访问********************/
        filterChainDefinitionMap.put("/users/tologin","anon");//去到登录页面的请求
        filterChainDefinitionMap.put("/users/login","anon");//登录请求
        filterChainDefinitionMap.put("/index.html","anon");//去首页
        /******************设置身份验证authc请求，如果认证不通过就去往指定页面********************/
        filterChainDefinitionMap.put("/users/add","authc");//添加操作
        filterChainDefinitionMap.put("/users/update","authc");//修改操作

        /******************设置角色验证roles********************/
        //必须是角色为admin的才能访问
        filterChainDefinitionMap.put("/users/add","roles[admin]");
//        filterChainDefinitionMap.put("/users/update","roles[update]");
        //多个角色或者权限用逗号隔开，逗号后面的会覆盖逗号前面的
//        filterChainDefinitionMap.put("/users/update","roles[update],roles[admin]");
        //多个角色或者权限在方括号中用逗号隔开，是并且关系，必须同时满足
//        filterChainDefinitionMap.put("/users/update","roles[update,admin]");

        /******************设置权限验证perms********************/
        //必须满足权限才能通过，否则权限不足
        filterChainDefinitionMap.put("/users/update","perms[user:update]");

        //此配置放到最后
        filterChainDefinitionMap.put("/**","authc");//所有请求




        //设置过滤器链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //设置认证不通过去往的地址，设置登录页面的请求（不设置的话就默认去到login.jsp）
        shiroFilterFactoryBean.setLoginUrl("/users/tologin");
        //设置没有权限去往的地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/users/noAuthc");

        //返回shiroFilterFactoryBean
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager对象,关联realm
     * @param realm
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm realm){
        //创建DefaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        //关联realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
}
