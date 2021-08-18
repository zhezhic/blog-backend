package xyz.zhezhi.handler;

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

    /**
     * @Description: 全局异常
     * @param e:
     * @return xyz.zhezhi.common.R
     * @author zhezhi
     * @date 2021/8/18 下午10:37
     */
    @ExceptionHandler(Exception.class)
    public R GlobalException(Exception e) {
        log.error(e.getMessage());
        return R.error().message(e.getMessage());
    }
    /**
     * @Description: 自定义异常
     * @param e:
     * @return xyz.zhezhi.common.R
     * @author zhezhi
     * @date 2021/8/18 下午10:37
     */
    @ExceptionHandler(CustomException.class)
    public R CustomException(CustomException e) {
        log.error(e.getMessage());
        return R.error().code(e.getCode()).message(e.getMessage());
    }
    /**
     * @Description: 字段验证错误异常
     * @param e:
     * @return xyz.zhezhi.common.R
     * @author zhezhi
     * @date 2021/8/18 下午10:39
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R ValidationException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return R.error().message(e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
}
