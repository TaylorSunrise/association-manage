package com.taylor.associationmanage.util;


import com.taylor.associationmanage.enums.ResultEnum;
import com.taylor.associationmanage.vo.ResultVO;

public class ResultUtil {

    public static ResultVO success(Object object){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setMessage(ResultEnum.SUCCESS.getMessage());
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO error(ResultEnum resultEnum){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMessage(resultEnum.getMessage());
        resultVO.setData(null);
        return resultVO;
    }

    public static ResultVO error(Integer code, String message) {
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(code);
        resultVO.setMessage(message);
        resultVO.setData(null);
        return resultVO;
    }

    public static ResultVO validError(String message){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(ResultEnum.FORM_VALID_ERROR.getCode());
        resultVO.setMessage(message);
        resultVO.setData(null);
        return resultVO;
    }
}
