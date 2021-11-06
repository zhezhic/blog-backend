package xyz.zhezhi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhezhi
 * @className: WebMvcConfig
 * @description:
 * @date 2021/11/6 下午1:15
 * @version：1.0
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${my-config.file.upload.avatar}")
    private String avatar;
    @Value("${my-config.file.mapper.avatar}")
    private String avatarMapping;

    @Value("${my-config.file.upload.blog-image}")
    private String blogImage;
    @Value("${my-config.file.mapper.blog-image}")
    private String blogImageMapping;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(avatarMapping).addResourceLocations("file:" + avatar);
        registry.addResourceHandler(blogImageMapping).addResourceLocations("file:" + blogImage);
    }
}
