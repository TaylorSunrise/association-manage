package com.taylor.associationmanage.service;

import com.taylor.associationmanage.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taylor.associationmanage.vo.ResultVO;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
public interface UserService extends IService<User> {

    public ResultVO login(User user);

    ResultVO userInfo(String token);
}
