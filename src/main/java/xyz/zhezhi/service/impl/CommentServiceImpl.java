package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.mapper.CommentMapper;
import xyz.zhezhi.module.entity.Comment;
import xyz.zhezhi.module.vo.CommentVO;
import xyz.zhezhi.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public List<CommentVO> queryCommentsByBlogId(Long id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id", id);
        List<Comment> originComment = commentMapper.selectList(wrapper);
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : originComment) {
            commentVOList.add(new CommentVO(comment));
        }
        List<CommentVO> finalCommentVO = commentVOList;
        commentVOList = commentVOList.stream()
                .filter(commentVO -> commentVO.getParentId().equals(0L))
                .peek(e -> e.setChildren(getCommentChildren(e, finalCommentVO)))
                .collect(Collectors.toList());
        return commentVOList;
    }

    private List<CommentVO> getCommentChildren(CommentVO root, List<CommentVO> commentVOList) {
        return commentVOList
                .stream()
                .filter(e -> Objects.equals(e.getParentId(), root.getId()))
                .peek(e -> e.setChildren(getCommentChildren(e, commentVOList)))
                .collect(Collectors.toList());
    }
}
