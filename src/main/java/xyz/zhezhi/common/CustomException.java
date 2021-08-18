package xyz.zhezhi.common;

import lombok.*;

/**
  * @author zhezhi
  * @className: CustomException
  * @description: 返回异常信息类
  * @date 2021/8/17 下午9:17
  * @version：1.0
  */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{
    private Integer code;
    private String message;
}
