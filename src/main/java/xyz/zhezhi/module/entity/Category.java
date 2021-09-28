package xyz.zhezhi.module.entity;

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
 * @className: Category
 * @description: 博客分类实体类
 * @date 2021/9/17 下午7:12
 * @version：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @ApiModelProperty("分类id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("分类名称")
    @Size(min = 1, max = 30, message = "名称长度不符合要求")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty("作者id")
    private Long authorId;


    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("排序")
    private Integer rank;

    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN",timezone = "Asia/Shanghai")

    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN",timezone = "Asia/Shanghai")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;
}
