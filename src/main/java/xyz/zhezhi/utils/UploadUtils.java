package xyz.zhezhi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadUtils {
    private static String avatar;
    @Value("${myconfig.upload.avatar}")
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public static String getAvatarPath(){
        return avatar;
    }

}
