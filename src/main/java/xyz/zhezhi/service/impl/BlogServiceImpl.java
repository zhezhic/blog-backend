package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.mapper.BlogMapper;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.vo.BlogVO;
import xyz.zhezhi.service.BlogService;

/**
 * @author zhezhi
 * @className: BlogServiceImpl
 * @description: blog implements
 * @date 2021/9/4 下午1:43
 * @version：1.0
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public int release(Blog blog) {
        return blogMapper.insert(blog);
    }

    @Override
    public BlogVO selectPage(Integer current, Integer size) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("is_public",0)
                .select(Blog.class,info -> !info.getColumn().equals("content"))
        ;
        IPage<Blog> page = blogMapper.selectPage(new Page<>(current, size), wrapper);
        BlogVO blogVO = new BlogVO();
        blogVO.setCurrent(current);
        blogVO.setSize(size);
        blogVO.setTotal(page.getTotal());
        blogVO.setBlogList(page.getRecords());
        return blogVO;
    }

    @Override
    public Blog queryBlogById(Long id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("id",id)
                .eq("is_public",0)
        ;
        return blogMapper.selectOne(wrapper);
    }

}
