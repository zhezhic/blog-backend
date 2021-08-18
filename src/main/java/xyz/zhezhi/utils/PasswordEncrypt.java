package xyz.zhezhi.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
/**
  * @author zhezhi
  * @className: PasswordEncrypt
  * @description: 密码md5加盐加密类
  * @date 2021/8/17 下午9:25
  * @version：1.0
  */
public class PasswordEncrypt {
    private final static String KEY = "zhezhi";
    public static String encrypt(String text) {
        return SaSecureUtil.md5BySalt(text, KEY);
    }
}
