package com.jk.config;


import com.jk.model.UserModel;
import com.jk.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class MyShiroRealm extends AuthorizingRealm {


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从数据库获取当前用户所拥有的权限：权限表、角色权限关联表、用户角色关联表
        UserModel user=(UserModel) principalCollection.getPrimaryPrincipal();
        //通过工具类获取bookService
        UserService userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
        List<String>  list=userService.findPower(user.getUsid());
        SimpleAuthorizationInfo sai = new SimpleAuthorizationInfo();
        sai.addStringPermissions(list);
        return sai;
    }

    //认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取账号
        String usname = (String) authenticationToken.getPrincipal();
        //通过SpringBeanFactoryUtils工厂获取到userservice接口
        UserService userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
        //正常查询
        UserModel userModel = userService.queryUserByName(usname);
        if (userModel == null){
            return null;
        }
        //这里可以加一个判断账户锁定
        //通过这个方法来判断密码是否正确
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userModel,userModel.getUspass(),getName());
        return simpleAuthenticationInfo;
    }
}
