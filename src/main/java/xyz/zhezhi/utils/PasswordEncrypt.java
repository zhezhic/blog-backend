package xyz.zhezhi.utils;

import cn.dev33.satoken.secure.SaSecureUtil;

public class PasswordEncrypt {
    private final static String KEY = "zhezhi";
    public static String encypt(String text) {
        return SaSecureUtil.md5BySalt(text, KEY);
    }
}
