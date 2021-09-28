package xyz.zhezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.zhezhi.module.entity.Comment;

/**
 * @author zhezhi
 * @className: CommentMapper
 * @description: Comment Mapper
 * @date 2021/9/28 下午7:02
 * @version：1.0
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
