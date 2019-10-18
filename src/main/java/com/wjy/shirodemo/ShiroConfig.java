package com.wjy.shirodemo;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new HashMap<String, String>();
        //登录时访问的地址，即没有登录时访问任何页面跳转的地址
        shiroFilterFactoryBean.setLoginUrl("/login");
        //认证未通过访问的地址，即经过认证但是没有相应的权限时跳转的地址
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthc");
        //设置认证成功之后转向的地址
        shiroFilterFactoryBean.setSuccessUrl("/authc/index");

        // /* anon表示不拦截  允许任何人访问
        filterChainDefinitionMap.put("/*", "anon");
        // /authc/index 必须登录才能访问
        filterChainDefinitionMap.put("/authc/index", "authc");
        // /authc/admin  需要有admin角色才能访问
        filterChainDefinitionMap.put("/authc/admin", "roles[admin]");
        // /authc/renewable 需要有Create,Update权限
        filterChainDefinitionMap.put("/authc/renewable", "perms[Create,Update]");
        // /authc/removable需要有Delete权限
        filterChainDefinitionMap.put("/authc/removable", "perms[Delete]");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);
        // 散列次数
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }

    @Bean
    public EnceladusShiroRealm shiroRealm() {
        EnceladusShiroRealm shiroRealm = new EnceladusShiroRealm();
        // 原来在这里
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        return new PasswordHelper();
    }
}