package xyz.zhezhi.module.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhezhi
 * @className: Comment
 * @description: 评论VO类
 * @date 2021/9/28 下午6:44
 * @version：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO {
    @ApiModelProperty("分类id")
    private Long id;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("博客id")
    private Long blogId;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("父评论id")
    private Long parentId;

    @ApiModelProperty("排序")
    private Integer rank;

    @ApiModelProperty("子评论")
    private List<CommentVO> children;

    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN", timezone = "Asia" +
            "/Shanghai")
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN", timezone = "Asia" +
            "/Shanghai")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    public CommentVO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.blogId = comment.getBlogId();
        this.authorId = comment.getAuthorId();
        this.parentId = comment.getParentId();
        this.rank = comment.getRank();
        this.createTime = comment.getCreateTime();
        this.updateTime = comment.getUpdateTime();
    }
}
