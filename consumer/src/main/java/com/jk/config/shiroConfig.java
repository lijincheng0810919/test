package com.jk.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration  //注册 @Bean
public class shiroConfig {


    // @Bean 作用:   将ShiroFilterFactoryBean类注入spring
    //ShiroFilterFactoryBean : 安全过滤工厂实体
    // ShiroFilterFactoryBean 处理拦截资源文件问题
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置信息管理securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //配置拦截规则
        //链表   有序
        Map<String, String> filterLinkedHashMap = new LinkedHashMap<>();
        //放置规则  到 map中
        //配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，
        // 这里不能直接使用 ("/static/**", "anon")来配置匿名访问，
        // 必须配置到每个静态目录
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterLinkedHashMap.put("/css/**","anon");
        filterLinkedHashMap.put("/jquery-easyui-1.5/**","anon");
        filterLinkedHashMap.put("/html/**","anon");
        filterLinkedHashMap.put("/img/**","anon");
        filterLinkedHashMap.put("/fonts/**","anon");
        filterLinkedHashMap.put("/user/login","anon");
        filterLinkedHashMap.put("/page/toLogin","anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro自己搞定
        filterLinkedHashMap.put("/logout","logout");
        filterLinkedHashMap.put("/verificationCode","anon");
        filterLinkedHashMap.put("/**","authc");

        shiroFilterFactoryBean.setLoginUrl("/page/toLogin");
        //未授权的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/page/403");
        //将规则放到shiro安全过滤工厂
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterLinkedHashMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /** * 凭证匹配器 *
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了 * ）
     * * @return
     * */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     *  aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
        mappings.setProperty("UnauthorizedException","/page/403");
        r.setExceptionMappings(mappings);  // None by default
        //r.setDefaultErrorView("/page/error");    // No default
        r.setExceptionAttribute("exception");     // Default is "exception"
        r.setWarnLogCategory("example.MvcLogger");     // No default
        return r;
    }


}
