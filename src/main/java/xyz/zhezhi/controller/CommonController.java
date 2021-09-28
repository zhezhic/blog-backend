package xyz.zhezhi.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhezhi.service.BlogService;

/**
 * @author zhezhi
 * @className: CommonController
 * @description: 通用controllerlei
 * @date 2021/9/21 下午11:25
 * @version：1.0
 */
@Api(tags = "通用接口")
@RestController
@RequestMapping("/common/")
public class CommonController {
    BlogService blogService;

    public CommonController(BlogService blogService) {
        this.blogService = blogService;
    }

}
