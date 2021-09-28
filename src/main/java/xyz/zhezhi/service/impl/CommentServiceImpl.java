package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.mapper.CommentMapper;
import xyz.zhezhi.module.entity.Comment;
import xyz.zhezhi.service.CommentService;

/**
 * @author zhezhi
 * @className: CommentServiceImpl
 * @description: Comment Service Impl
 * @date 2021/9/28 下午7:04
 * @version：1.0
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    CommentMapper commentMapper;

    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Override
    public int addComment(Comment comment) {
        return commentMapper.insert(comment);
    }
}
