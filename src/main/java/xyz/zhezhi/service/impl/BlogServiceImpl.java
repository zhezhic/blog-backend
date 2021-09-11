package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.mapper.BlogMapper;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.service.BlogService;

/**
 * @author zhezhi
 * @className: BlogServiceImpl
 * @description: blog implements
 * @date 2021/9/4 下午1:43
 * @version：1.0
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper,Blog> implements BlogService {
    BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public int release(Blog blog) {
        return blogMapper.insert(blog);
    }
}
