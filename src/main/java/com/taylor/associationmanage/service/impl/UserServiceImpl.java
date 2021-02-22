package com.taylor.associationmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taylor.associationmanage.entity.Role;
import com.taylor.associationmanage.entity.User;
import com.taylor.associationmanage.enums.ResultEnum;
import com.taylor.associationmanage.mapper.RoleMapper;
import com.taylor.associationmanage.mapper.UserMapper;
import com.taylor.associationmanage.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taylor.associationmanage.util.JwtUtil;
import com.taylor.associationmanage.util.MD5Util;
import com.taylor.associationmanage.util.RedisUtil;
import com.taylor.associationmanage.util.ResultUtil;
import com.taylor.associationmanage.vo.ResultVO;
import com.taylor.associationmanage.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    public ResultVO login(User user) {
        //处理比对密码
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User u = userMapper.selectOne(wrapper);
        if (u != null) {
            String dbPassword = u.getPassword();
            String md5Password = MD5Util.hashed(user.getPassword(), u.getSalt());
            if (md5Password.equals(dbPassword)) {
                //生成token给用户
                String token = getToken(u);
                return ResultUtil.success(token);
            }
        }
        return ResultUtil.error(ResultEnum.LOGIN_INFO_ERROR);
    }

    @Override
    public ResultVO userInfo(String token) {
        //根据id查找用户
        log.info("======token:"+token);
        long userId = JwtUtil.parseTokenUid(token);
        User user = userMapper.selectById(userId);
        Set<String> roleSet = null;
        if (user != null) {
            // 用户拥有的角色，比如“admin/user”
//            String role = userMapper.getRoleByRoleid(user.getRoleid());
            roleSet = new HashSet<>();
            if (user.getRoleId() == 1) {//系统管理员
                roleSet.add("root");
            } else if (user.getRoleId() == 2) {//社团管理员
                QueryWrapper<Role> wrapper = new QueryWrapper<>();
                wrapper.eq("user_id", user.getUserId()).select("DISTINCT role");
                List<Role> roles = roleMapper.selectList(wrapper);
                for (Role r : roles) {
                    //admin:admin社长
                    //admin:activity活动组织部部长
                    //admin:notice宣传部部长
                    //admin:finance财务管理部部长
                    roleSet.add(r.getRole());
                }
            } else if (user.getRoleId() == 3) {
                roleSet.add("member");
            } else {
                roleSet.add("user");
            }

            log.info("RoleId为：" + user.getRoleId());
            // 用户拥有的权限集合，比如“role:add,user:add”
//            Set<String> permissions = sysService.getPermissionsByRoleid(user.getRoleid());
//            simpleAuthorizationInfo.addStringPermissions(permissions);
//            log.info("权限有："+permissions.toString());
            UserInfoVO userInfoVO = new UserInfoVO();
            BeanUtils.copyProperties(user, userInfoVO);
            userInfoVO.setRoles(roleSet);
            log.info("userInfoVO:"+userInfoVO);
            return ResultUtil.success(userInfoVO);
        }
        return ResultUtil.error(ResultEnum.USER_NOT_EXISTS);
    }

    private String getToken(User user) {
        // 生成token
        String token = JwtUtil.createToken(user);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.set(token, token, JwtUtil.getExpireTime());

        return token;
    }
}
