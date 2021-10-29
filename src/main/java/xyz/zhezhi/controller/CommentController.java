package xyz.zhezhi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.entity.Comment;
import xyz.zhezhi.module.vo.CommentVO;
import xyz.zhezhi.service.BlogService;
import xyz.zhezhi.service.CommentService;

import java.util.List;

/**
 * @author zhezhi
 * @className: CommentController
 * @description: 评论controller
 * @date 2021/10/3 下午1:53
 * @version：1.0
 */
@Api(tags = "评论管理")
@RestController
@RequestMapping("/comment/")
public class CommentController {
    CommentService commentService;
    BlogService blogService;
    public CommentController(CommentService commentService,BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    @PostMapping("addComment")
    @SaCheckLogin
    public R addComment(@RequestBody Comment comment) {
        comment.setAuthorId(StpUtil.getLoginIdAsLong());
        System.out.println(comment);
        if (commentService.addComment(comment)>=1) {
            blogService.updateBlogCommentCount(String.valueOf(comment.getBlogId()));
            return R.ok().message("留言成功");
        }
        return R.error();
    }
    @GetMapping("queryCommentsByBlogId/{id}")
    public R queryCommentsByBlogId(@PathVariable("id") String id) {
        List<CommentVO> commentVOList = commentService.queryCommentsByBlogId(Long.valueOf(id));
        return R.ok().data("comments",commentVOList);
    }

}
