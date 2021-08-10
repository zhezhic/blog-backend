package xyz.zhezhi.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.common.R;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R GlobalException(Exception e){
        return R.error().message("执行了全局异常");
    }
    @ExceptionHandler(CustomException.class)
    public R CustomException(CustomException e){
        return R.error().code(e.getCode()).message(e.getMessage());
    }

}
