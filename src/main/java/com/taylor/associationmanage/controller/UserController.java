package com.taylor.associationmanage.controller;


import com.taylor.associationmanage.entity.User;
import com.taylor.associationmanage.service.UserService;
import com.taylor.associationmanage.util.ResultUtil;
import com.taylor.associationmanage.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.taylor.associationmanage.enums.ResultEnum.TOKEN_INVALID;

/**
 * <p>
 * 用户信息 前端控制器
 * </p>
 *
 * @author taylor
 * @since 2021-02-19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登录(用户名，密码)
     * @param user
     * @return
     */
    @PostMapping(value = "/login",consumes = "application/json")
    public ResultVO login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping(value = "/info")
    public ResultVO getInfo(HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        return userService.userInfo(token);
    }
    /**
     * 处理非法请求
     */
    @GetMapping("/unauthorized")
    public ResultVO unauthorized() {
        return ResultUtil.error(TOKEN_INVALID);
    }

}

