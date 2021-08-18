package xyz.zhezhi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.zhezhi.utils.Initialization;

/**
 * @author zhezhi
 * @className: AppConfig
 * @description: springboot的配置类
 * @date 2021/8/18 下午4:16
 * @version：1.0
 */
@Configuration
public class AppConfig {
    @Bean(initMethod="init")
    public Initialization initialization() {
        return new Initialization();
    }
}
