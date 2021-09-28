package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.vo.BlogVO;

/**
 * @author zhezhi
 * @className: BlogService
 * @description: blog service
 * @date 2021/9/4 下午1:42
 * @version：1.0
 */
public interface BlogService extends IService<Blog> {
    int release(Blog blog);
    BlogVO selectPage(Integer current, Integer size);
    Blog queryBlogById(Long id);
}
