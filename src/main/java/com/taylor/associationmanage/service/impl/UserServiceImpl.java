package com.taylor.associationmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.taylor.associationmanage.entity.User;
import com.taylor.associationmanage.enums.ResultEnum;
import com.taylor.associationmanage.mapper.UserMapper;
import com.taylor.associationmanage.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taylor.associationmanage.util.JwtUtil;
import com.taylor.associationmanage.util.MD5Util;
import com.taylor.associationmanage.util.RedisUtil;
import com.taylor.associationmanage.util.ResultUtil;
import com.taylor.associationmanage.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;


    @Override
    public ResultVO login(User user) {
        //处理比对密码
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User u = userMapper.selectOne(wrapper);
        if (u!=null){
            String dbPassword = u.getPassword();
            String md5Password = MD5Util.hashed(user.getPassword(), u.getSalt());
            if(md5Password.equals(dbPassword)) {
                //生成token给用户
                String token = getToken(user);
                return ResultUtil.success(token);
            }
        }
        return ResultUtil.error(ResultEnum.LOGIN_INFO_ERROR);
    }

    @Override
    public ResultVO userInfo(String token) {
        //根据id查找用户
        int userId = JwtUtil.parseTokenUid(token);
        User user = userMapper.selectById(userId);

        return null;
    }

    private String getToken(User user){
        // 生成token
        String token = JwtUtil.createToken(user);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.set(token, token, JwtUtil.getExpireTime());

        return token;
    }
}
