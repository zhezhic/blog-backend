package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import xyz.zhezhi.common.ElasticSearchIndex;
import xyz.zhezhi.mapper.BlogMapper;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.vo.BlogVO;
import xyz.zhezhi.service.BlogService;
import xyz.zhezhi.utils.ElasticSearchUtils;

import java.util.List;

/**
 * @author zhezhi
 * @className: BlogServiceImpl
 * @description: blog implements
 * @date 2021/9/4 下午1:43
 * @version：1.0
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {
    final BlogMapper blogMapper;
    final RestHighLevelClient restHighLevelClient;
    public BlogServiceImpl(BlogMapper blogMapper, RestHighLevelClient restHighLevelClient) {
        this.blogMapper = blogMapper;
        this.restHighLevelClient = restHighLevelClient;
    }

    @Override
    public int release(Blog blog)  {
        int result = blogMapper.insert(blog);
        ElasticSearchUtils.IndexRequest(blog, ElasticSearchIndex.BLOG.getIndex(), String.valueOf(blog.getId()));
        return result;
    }

    @Override
    public int updateBlog(Blog blog) {
        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("id",blog.getId())
                .eq("author_id", blog.getAuthorId());
        int result = blogMapper.update(blog,wrapper);
        ElasticSearchUtils.updateRequestByBlog(blog,ElasticSearchIndex.BLOG.getIndex());
        return result;
    }

    @Override
    public int deleteBlogById(String blogId, String userId) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("id", blogId).eq("author_id", userId);
        int result = blogMapper.delete(wrapper);
        ElasticSearchUtils.deleteRequest(ElasticSearchIndex.BLOG.getIndex(),blogId);
        return result;
    }

    @Override
    public BlogVO selectPage(Integer current, Integer size) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("is_public",1)
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
    public List<Blog> queryBlogVOByIds(List<String> ids) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.in("id", ids);
        return blogMapper.selectList(wrapper);
    }

    @Override
    public Blog queryBlogById(Long id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("id",id)
                .eq("is_public",1)
        ;
        return blogMapper.selectOne(wrapper);
    }

    @Override
    public int updateBlogHot(String id) {
        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("id", id)
                .setSql("hot=hot+1")
        ;
        return blogMapper.update(null, wrapper);
    }

    @Override
    public int updateBlogCommentCount(String id) {
        UpdateWrapper<Blog> wrapper = new UpdateWrapper<>();
        wrapper
                .eq("id", id)
                .setSql("comment_count=comment_count+1")
        ;
        return blogMapper.update(null, wrapper);
    }

    @Override
    public List<Blog> queryBlogsByUserId(Long id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("author_id",id)
        ;
        return blogMapper.selectList(wrapper);
    }

    @Override
    public List<Blog> queryBlogsByOtherUserId(Long id) {
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper
                .eq("author_id",id)
                .eq("is_public",1)
        ;
        return blogMapper.selectList(wrapper);
    }


}
