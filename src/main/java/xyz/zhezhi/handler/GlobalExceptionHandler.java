package xyz.zhezhi.handler;

import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.common.R;

/**
 * @author zhezhi
 * @className: GlobalExceptionHandler
 * @description: 全局异常捕获返回类
 * @date 2021/8/17 下午9:21
 * @version：1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotLoginException.class)
    public R requestException(NotLoginException e) {
        log.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "::" + e.getMessage());
        return R.unauthorized().message("请重新登陆");
    }
    /**
     * @param e:
     * @return xyz.zhezhi.common.R
     * @Description: 自定义异常
     * @author zhezhi
     * @date 2021/8/18 下午10:37
     */
    @ExceptionHandler(CustomException.class)
    public R customException(CustomException e) {
        log.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "::" + e.getMessage());
        return R.error().code(e.getCode()).message(e.getMessage());
    }

    /**
     * @param e:
     * @return xyz.zhezhi.common.R
     * @Description: 字段验证错误异常
     * @author zhezhi
     * @date 2021/8/18 下午10:39
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R validationException(MethodArgumentNotValidException e) {
        log.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "::" + e.getMessage());

        return R.error().message(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    /**
     * @param e:
     * @return xyz.zhezhi.common.R
     * @Description: 全局异常
     * @author zhezhi
     * @date 2021/8/18 下午10:37
     */
    @ExceptionHandler(Exception.class)
    public R globalException(Exception e) {
        log.warn(Thread.currentThread().getStackTrace()[1].getMethodName() + "::" + e.getMessage());
        return R.error().message(e.getMessage());
    }
}
