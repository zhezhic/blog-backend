package xyz.zhezhi.module.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.dto.user.UserProfile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author zhezhi
 * @className: User
 * @description: 用户实体
 * @date 2021/8/17 下午9:23
 * @version：1.0
 */
@ApiModel("用户实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ApiModelProperty("用户ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户名称")
    @Size(min = 3, max = 10, message = "名称长度不符合要求")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty("用户密码")
    @Size(min = 6, max = 20, message = "密码长度不合法")
    @NotBlank(message = "密码不能为空")
    private String password;
    @ApiModelProperty("用户邮箱")
    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("用户个人介绍")
    private String intro;
    @TableLogic
    @ApiModelProperty("逻辑删除")
    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN", timezone = "Asia" +
            "/Shanghai")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh_CN", timezone = "Asia" +
            "/Shanghai")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public User(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.name = userProfile.getName();
        this.email = userProfile.getEmail();
        this.intro = userProfile.getIntro();
    }
}
