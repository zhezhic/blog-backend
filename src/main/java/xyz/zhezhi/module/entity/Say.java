package xyz.zhezhi.module.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author zhezhi
 * @className: Say
 * @description: 说说
 * @date 2021/10/29 上午11:31
 * @version：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Say {
    @ApiModelProperty("说说id")
    @TableId(type = IdType.ASSIGN_ID)
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty("作者id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long authorId;

    @ApiModelProperty("内容")
    @Size(min = 1, message = "博客长度不符合要求")
    @NotBlank(message = "帖子不能为空")
    private String content;

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
