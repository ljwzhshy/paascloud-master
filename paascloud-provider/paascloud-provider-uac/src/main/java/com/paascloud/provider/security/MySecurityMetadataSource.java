package com.paascloud.provider.security;

import com.paascloud.provider.model.dto.user.Perm;
import com.paascloud.provider.service.UacUserService;
import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.Resource;
import java.util.*;

/**
 *自定义
 *资源管理器
 *
 * @author luojiawei
 */
@Component("MySecurityMetadataSource")
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    private UacUserService uacUserService;
    private Map<String, Collection<ConfigAttribute>> map = null;
     //加载权限表中所有操作请求权限
    public void loadResourceDefine() {
        map = new HashMap<>(16);
        //拥有请求url权限的所有角色集合
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        // 获取启用的权限操作请求
        List<Perm> permissions = uacUserService.getAllPerms();
        for (Perm permission : permissions) {
            if (StrUtil.isNotBlank(permission.getResource()) && StrUtil.isNotBlank(permission.getPerm())) {
                cfg = new SecurityConfig("ROLE_"+permission.getRoleCode());
                //权限与角色是一对多关系
                if(map.containsKey(permission.getResource())){
                    configAttributes = map.get(permission.getResource());
                }else{
                    configAttributes = new ArrayList<>();
                }
                configAttributes.add(cfg);
                map.put(permission.getResource(), configAttributes);
            }
        }
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }
        //Object中包含用户请求request
        String url = ((FilterInvocation) object).getRequestUrl();
        PathMatcher pathMatcher = (PathMatcher) new AntPathMatcher();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String resURL = (String) iterator.next();
            if (StrUtil.isNotBlank(resURL) && pathMatcher.match(resURL, url)) {
                return map.get(resURL);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
