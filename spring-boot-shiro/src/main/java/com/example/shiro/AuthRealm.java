package com.example.shiro;

import com.example.dao.PermissionRepository;
import com.example.dao.RoleRepository;
import com.example.dao.UserRepository;
import com.example.entity.Permission;
import com.example.entity.Role;
import com.example.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从session获取用户
        //User user = (User) principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        // List<String> permissonList = new ArrayList<>();
//        User user = TokenUtils.getUser(principalCollection.toString());
//        if (user == null) {
//            throw new AuthenticationException("登录信息已过期，请重新登录");
//        }
        String username = principalCollection.toString();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(!optionalUser.isPresent()){
            throw new AuthorizationException("用户不存在");
        }
        User user = optionalUser.get();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取角色
        Role role = roleRepository.findById(user.getId()).orElseGet(Role::new);
        authorizationInfo.addRole(role.getName());
        List<Permission> permissionList = permissionRepository.findByRoleId(role.getId());
        //获取权限
        for (Permission permission : permissionList) {
            authorizationInfo.addStringPermission(permission.getPermissionCode());
        }
        return authorizationInfo;
    }

    //认证登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //String token = authenticationToken.getCredentials().toString();
        String username = authenticationToken.getPrincipal().toString();
        //User user = TokenUtils.getUser(token);
        /*if (user == null) {
            throw new AuthenticationException("登录信息已过期，请重新登录");
        }*/
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new AuthenticationException("用户不存在！");
        }
        return new SimpleAuthenticationInfo(username, optionalUser.get().getPassword(), this.getClass().getName());
    }
}
