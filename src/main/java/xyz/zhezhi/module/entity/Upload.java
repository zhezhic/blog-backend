package xyz.zhezhi.module.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhezhi
 * @className: File
 * @description: yaml配置类
 * @date 2021/8/18 下午2:23
 * @version：1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "myconfig.upload")
public class Upload {
    private String root;
    private String avatar;
    private String blogImage;
}
