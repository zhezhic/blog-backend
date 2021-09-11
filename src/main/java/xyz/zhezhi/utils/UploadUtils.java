package xyz.zhezhi.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploadUtils {
    private static String avatar;
    private static String blogImage;
    @Value("${myconfig.upload.avatar}")
    public void setAvatar(String avatar) {
        UploadUtils.avatar = avatar;
    }
    @Value("${myconfig.upload.blog-image}")
    public void blogImage(String blogImage) {
        UploadUtils.blogImage = blogImage;
    }
    public static String getAvatarPath(){
        return avatar;
    }
    public static String getBlogImage(){
        return blogImage;
    }
}
