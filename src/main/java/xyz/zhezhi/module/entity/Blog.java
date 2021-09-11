package xyz.zhezhi.module.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author zhezhi
 * @className: Blog
 * @description: blog entity
 * @date 2021/9/4 下午1:37
 * @version：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {
    @ApiModelProperty("帖子id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("帖子id")
    private Long authorId;

    @ApiModelProperty("标题")
    @Size(min = 1, max = 100, message = "名称长度不符合要求")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("内容")
    @Size(min = 1, message = "名称长度不符合要求")
    @NotBlank(message = "帖子不能为空")
    private String text;

    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
