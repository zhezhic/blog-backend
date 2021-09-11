package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Blog;

/**
 * @author zhezhi
 * @className: BlogService
 * @description: blog service
 * @date 2021/9/4 下午1:42
 * @version：1.0
 */
public interface BlogService extends IService<Blog> {
    int release(Blog blog);
}
