package xyz.zhezhi.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@ApiModel("用户实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ApiModelProperty("用户ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    @ApiModelProperty("用户名称")
    @Size(min = 3,max = 10,message = "名称不符合要求")
    private String name;
    @ApiModelProperty("用户密码")
    @Min(value = 6,message = "密码不合法")
    private String password;
    @ApiModelProperty("用户邮箱")
    @Email
    private String email;
    @TableLogic
    private Integer deleted;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
