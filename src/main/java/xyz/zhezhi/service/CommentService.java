package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Comment;
import xyz.zhezhi.module.vo.CommentVO;

import java.util.List;

/**
 * @author zhezhi
 * @className: CommentService
 * @description: Comment Service
 * @date 2021/9/28 下午7:03
 * @version：1.0
 */
public interface CommentService extends IService<Comment> {
    int addComment(Comment comment);
    List<CommentVO> queryCommentsByBlogId(Long id);
}
