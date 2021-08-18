package xyz.zhezhi.common;
/**
  * @author zhezhi
  * @className: ReturnCode
  * @description: 返回码
  * @date 2021/8/17 下午9:19
  * @version：1.0
  */
public interface ReturnCode {
    Integer OK = 200;
    Integer Bad_Request = 400;
    Integer Not_Found = 404;
    Integer Unauthorized = 401;
}
