package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.vo.BlogVO;

import java.util.List;

/**
 * @author zhezhi
 * @className: BlogService
 * @description: blog service
 * @date 2021/9/4 下午1:42
 * @version：1.0
 */
public interface BlogService extends IService<Blog> {
    int release(Blog blog);
    int updateBlog(Blog blog);
    int deleteBlogById(String blogId,String userId);
    BlogVO selectPage(Integer current, Integer size);
    List<Blog> queryBlogVOByIds(List<String> ids);
    Blog queryBlogById(Long id);
    int updateBlogHot(String id);
    int updateBlogCommentCount(String id);
    List<Blog> queryBlogsByUserId(Long id);
    List<Blog> queryBlogsByOtherUserId(Long id);
}
