package com.taylor.associationmanage.controller;

import com.taylor.associationmanage.exception.UnauthorizedException;
import com.taylor.associationmanage.util.ResultUtil;
import com.taylor.associationmanage.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;

/**
 * Description
 *
 * @author : Charles
 * @date : 2020/1/17
 */
@Slf4j
@RestControllerAdvice
public class ExceptionApi {
    /** 捕捉 shiro 的异常 */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultVO handle401(ShiroException e) {
        return ResultUtil.error(401, e.getMessage());
    }

    /** 捕捉UnauthorizedException */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultVO handle401() {
        return ResultUtil.error(401, "Unauthorized");
    }

    /** 捕捉其他所有异常 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO globalException(HttpServletRequest request, Throwable ex) {
        log.info("其他所有异常:"+ex.getMessage());
        return ResultUtil.error(getStatus(request).value(), ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
