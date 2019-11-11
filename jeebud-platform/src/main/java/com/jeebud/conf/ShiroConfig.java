package com.jeebud.conf;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.jeebud.common.constant.LoginTypeEnum;
import com.jeebud.core.shiro.JeebudModularRealmAuthenticator;
import com.jeebud.core.shiro.JeebudModularRealmAuthorizer;
import com.jeebud.core.shiro.LoginFormFilter;
import com.jeebud.core.shiro.ShiroSessionManager;
import com.jeebud.shiro.AdminRealm;
import com.jeebud.shiro.MemberRealm;
import lombok.Data;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.time.Duration;
import java.util.*;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class ShiroConfig {
    /**
     * 服务端前缀
     */
    @Value("${jeebud.sys.serverCtx}")
    private String serverCtx;
    @Value("#{'${jeebud.sys.ignoreAuthenticationUrl}'.split(',')}")
    private List<String> ignoreAuthenticationUrl;
    private String host = "localhost";
    private int port = 6379;
    private String password;
    private Duration timeout;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 过滤器链定义映射
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置忽略的url路径
        for (String url : ignoreAuthenticationUrl) {
            filterChainDefinitionMap.put(url, "anon");
        }
        // 所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put(serverCtx + "/**", "authc");
        filterChainDefinitionMap.put(serverCtx + "/logout", "logout");

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        // 配器shirot认登录累面地址，前后端分离中登录累面跳转应由前端路由控制，后台仅返回json数据, 对应LoginController中unauth请求
        shiroFilterFactoryBean.setLoginUrl(serverCtx + "/login");

        // 登录成功后要跳转的链接, 此项目是前后端分离，故此行注释掉，登录成功之后返回用户基本信息及token给前端
        Map<String, Filter> filters = new HashMap<>(1);
        Filter loginFilter = new LoginFormFilter();
        //此处使用自定义的拦截器,autho默认使用FormAuthenticationFilter拦截器
        filters.put("authc", loginFilter);
        shiroFilterFactoryBean.setFilters(filters);
        // 未授权界面, 对应LoginController中 unauthorized 请求
        shiroFilterFactoryBean.setUnauthorizedUrl("/err/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }

    /**
     * 管理员验证realm
     * @return
     */
    @Bean
    public AdminRealm adminRealm() {
        AdminRealm adminRealm = new AdminRealm();
        //指定类型 标志为admin登陆
        adminRealm.setName(LoginTypeEnum.ADMIN_LOGIN.getType());
        adminRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return adminRealm;
    }

    /**
     * 管理员验证realm
     * @return
     */
    @Bean
    public MemberRealm memberRealm() {
        MemberRealm memberRealm = new MemberRealm();
        //指定类型 标志为member登陆
        memberRealm.setName(LoginTypeEnum.MEMBER_LOGIN.getType());
        memberRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return memberRealm;
    }

    /**
     * shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setExpire(1800);
        return redisSessionDAO;
    }

    /**
     * sessionId 生成器
     *
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }


    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager sessionManager = new ShiroSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setSessionIdCookie(cookie());
        return sessionManager;
    }

    /**
     * shiro-redis开源插件
     *
     * @return
     */
    private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setTimeout((int) timeout.toMillis());
        redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * shiro-redis开源插件
     *
     * @return
     */
    @Bean
    public RedisCacheManager shiroCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("username");
        return redisCacheManager;
    }


    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(shiroCacheManager());
        //可以指定特定Realm处理特定类型
        securityManager.setAuthenticator(new JeebudModularRealmAuthenticator());
        securityManager.setAuthorizer(new JeebudModularRealmAuthorizer());
        List<Realm> realmList = new ArrayList<>();
        realmList.add(adminRealm());
        realmList.add(memberRealm());
        //这个放到后面，可以自动把realms赋值给Authenticator
        securityManager.setRealms(realmList);
        return securityManager;
    }

    /**
     * 开启注解生效
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public SimpleCookie cookie() {
        // cookie的name,对应的默认是 JSESSIONID
        SimpleCookie cookie = new SimpleCookie("JSESSIONID");
        cookie.setHttpOnly(true);
        //  path为 / 用于多个系统共享 JSESSIONID
        cookie.setPath("/");
        return cookie;
    }

    /**
     * thymeleaf shiro标签扩展
     *
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
