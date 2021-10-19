package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.Map;

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

    @Override
    public List<Map<String, Object>> searchByKeyword(String keyword, int current, int size,String index,String properties) {
        List<Map<String, Object>> list = ElasticSearchUtils.searchRequest(keyword, current, size,index,properties);
        return list;
    }

}
