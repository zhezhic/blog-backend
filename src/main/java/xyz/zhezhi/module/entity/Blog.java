package xyz.zhezhi.module.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.handler.JsonStringArrayTypeHandler;

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
@TableName(autoResultMap = true)
public class Blog {
    @ApiModelProperty("帖子id")
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("作者id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long authorId;

    @ApiModelProperty("标题")
    @Size(min = 1, max = 100, message = "名称长度不符合要求")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty("内容")
    @Size(min = 1, message = "博客长度不符合要求")
    @NotBlank(message = "帖子不能为空")
    private String content;

    @ApiModelProperty("简略")
    @NotBlank(message = "简略不能为空")
    private String context;

    @ApiModelProperty("公开")
    private Integer isPublic;

    @ApiModelProperty("博客别名")
    private String alias;

    @ApiModelProperty("博客分类")
    @TableField(typeHandler = JsonStringArrayTypeHandler.class)
    private String[] categoriesId;

    @ApiModelProperty("热度")
    private Integer hot;

    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN",timezone = "Asia/Shanghai")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN",timezone = "Asia/Shanghai")
    @ApiModelProperty("更新时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
