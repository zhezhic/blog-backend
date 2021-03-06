package xyz.zhezhi.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
/**
  * @author zhezhi
  * @className: R
  * @description: 统一返回类
  * @date 2021/8/17 下午9:18
  * @version：1.0
  */
@Data
public class R {
    //是否成功
    private Boolean success;

    //返回码
    private Integer code;

    //返回消息
    private String message;
    //返回数据
    private Map<String, Object> data = new HashMap<>();

    public R() {

    }

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ReturnCode.OK);
        r.setMessage("success");
        return r;
    }
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ReturnCode.Bad_Request);
        r.setMessage("error");
        return r;
    }
    public static R unauthorized(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ReturnCode.Unauthorized);
        r.setMessage("unauthorized");
        return r;
    }
    public static R internal_error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ReturnCode.Internal_Server_Error);
        r.setMessage("Internal Server Error");
        return r;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }
    public R message(String message) {
        this.setMessage(message);
        return this;
    }
    public R data(Map<String,Object> data) {
        this.setData(data);
        return this;
    }
    public R data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }
}
