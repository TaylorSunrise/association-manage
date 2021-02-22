package com.taylor.associationmanage.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taylor.associationmanage.entity.Role;
import com.taylor.associationmanage.entity.User;
import com.taylor.associationmanage.mapper.RoleMapper;
import com.taylor.associationmanage.mapper.UserMapper;
import com.taylor.associationmanage.util.JwtUtil;
import com.taylor.associationmanage.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Realm
 *
 * @author : Charles
 * @date : 2020/1/12
 */
@Slf4j
@Component("MyRealm")
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用来进行身份认证，也就是说验证用户输入的账号和密码是否正确，
     * 获取身份验证信息，错误抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        log.info("————————身份认证——————————");
        String token = (String) auth.getCredentials();
        if (null == token) {
            throw new AuthenticationException("token为空!");
        }
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.parseTokenAud(token);
        log.info("username:"+username);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (null == user) {
            throw new AuthenticationException("用户不存在!");
        }
        // 校验token是否过期
        if (!tokenRefresh(token, user)) {
            throw new AuthenticationException("Token已过期!");
        }
        return new SimpleAuthenticationInfo(user, token,"MyRealm");

    }

    /**
     * 获取用户权限信息，包括角色以及权限。
     * 只有当触发检测用户权限时才会调用此方法，例如checkRole,checkPermissionJwtToken
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("————权限认证 [ roles、permissions]————");
        User user = null;
        if (principals != null) {
            user = (User) principals.getPrimaryPrincipal();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (user != null) {
            // 用户拥有的角色，比如“admin/user”
//            String role = userMapper.getRoleByRoleid(user.getRoleid());
            Set<String> roleSet = new HashSet<>();
            String role = "";
            if (user.getRoleId()==1){//系统管理员
                roleSet.add("root");
            }else if (user.getRoleId()==2){//社团管理员
                QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id",user.getUserId()).select("DISTINCT role");
                List<Role> roles = roleMapper.selectList(queryWrapper);
                for (Role r:roles) {
                    //admin:admin社长
                    //admin:activity活动组织部部长
                    //admin:notice宣传部部长
                    //admin:finance财务管理部部长
                    roleSet.add(r.getRole());
                }
            }else if (user.getRoleId()==3){
                roleSet.add("member");
            }else {
                roleSet.add("user");
            }
            simpleAuthorizationInfo.addRoles(roleSet);
            log.info("RoleId为："+user.getRoleId());
            // 用户拥有的权限集合，比如“role:add,user:add”
//            Set<String> permissions = sysService.getPermissionsByRoleid(user.getRoleid());
//            simpleAuthorizationInfo.addStringPermissions(permissions);
//            log.info("权限有："+permissions.toString());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * JWT Token续签：
     * 业务逻辑：登录成功后，用户在未过期时间内继续操作，续签token。
     *         登录成功后，空闲超过过期时间，返回token已失效，重新登录。
     * 实现逻辑：
     *    1.登录成功后将token存储到redis里面(这时候k、v值一样都为token)，并设置过期时间为token过期时间
     *    2.当用户请求时token值还未过期，则重新设置redis里token的过期时间。
     *    3.当用户请求时token值已过期，但redis中还在，则JWT重新生成token并覆盖v值(这时候k、v值不一样了)，然后设置redis过期时间。
     *    4.当用户请求时token值已过期，并且redis中也不存在，则用户空闲超时，返回token已失效，重新登录。
     */
    public boolean tokenRefresh(String token, User user) {
        String cacheToken = String.valueOf(redisUtil.get(token));
        // 过期后会得到"null"值，所以需判断字符串"null"
        if (cacheToken != null && cacheToken.length() != 0 && !"null".equals(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.isVerify(cacheToken)) {
                log.info("token无效，生成token存入redis,并设置超时时间:"+JwtUtil.getExpireTime());
                // 生成token
                String newToken = JwtUtil.createToken(user);
                // 将token存入redis,并设置超时时间
                redisUtil.set(token, newToken, JwtUtil.getExpireTime());
            } else {
                log.info("重新设置超时时间:"+JwtUtil.getExpireTime());
                // 重新设置超时时间
                redisUtil.expire(token, JwtUtil.getExpireTime());
            }
            return true;
        }
        log.info("存入redis的过期时间："+redisUtil.getExpire(token));
        return false;
    }
}
