package com.jeebud.core.shiro;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * <p>Description: 为了实现不同类型用户权限认证（如管理员、会员）</p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
public class JeebudModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        //断言配置
        assertRealmsConfigured();
        //获取到所有realm的名称
        Set<String> realmNames = principals.getRealmNames();
        for (String realmName : realmNames) {
            //遍历所有的realm
            for (Realm realm : realms) {
                if (!(realm instanceof Authorizer)) {
                    continue;
                }
                //如果名称匹配上了，就走这个realm权限验证
                if (realm.getName().contains(realmName)) {
                    return ((Authorizer) realm).isPermitted(principals, permission);
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        //断言配置
        assertRealmsConfigured();
        //获取到所有realm的名称
        Set<String> realmNames = principals.getRealmNames();
        for (String realmName : realmNames) {
            //遍历所有的realm
            for (Realm realm : realms) {
                if (!(realm instanceof Authorizer)) {
                    continue;
                }
                //如果名称匹配上了，就走这个realm权限验证
                if (realm.getName().contains(realmName)) {
                    return ((Authorizer) realm).hasRole(principals, roleIdentifier);
                }
            }
        }
        return false;
    }
}
