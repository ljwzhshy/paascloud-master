package com.paascloud.security.core.authorize;

import com.xiaoleilu.hutool.util.StrUtil;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.security.Permission;
import java.util.*;

/**
 *自定义
 *资源管理器
 *
 * @author luojiawei
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    private UacUserService uacUserService;
    private Map<String, Collection<ConfigAttribute>> map = null;
     //加载权限表中所有操作请求权限
    public void loadResourceDefine() {
        map = new HashMap<>(16);
        Collection<ConfigAttribute> configAttributes;
        ConfigAttribute cfg;
        // 获取启用的权限操作请求
        List<Permission> permissions = uacUserService.(CommonConstant.PERMISSION_OPERATION, CommonConstant.STATUS_NORMAL);
        for (Permission permission : permissions) {
            if (StrUtil.isNotBlank(permission.getTitle()) && StrUtil.isNotBlank(permission.getPath())) {
                configAttributes = new ArrayList<>();
                cfg = new SecurityConfig(permission.getTitle());
                //作为MyAccessDecisionManager类的decide的第三个参数
                configAttributes.add(cfg);
                //用权限的path作为map的key，用ConfigAttribute的集合作为value
                map.put(permission.getPath(), configAttributes);
            }
        }
    }
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
