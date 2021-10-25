package xyz.zhezhi.module.dto.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author zhezhi
 * @className: UserES
 * @description: UserElasticSearch
 * @date 2021/10/24 下午9:31
 * @version：1.0
 */
@ApiModel("UserElasticSearch")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserES {
    @ApiModelProperty("用户ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户名称")
    @Size(min = 3, max = 10, message = "名称长度不符合要求")
    @NotBlank(message = "用户名不能为空")
    private String name;

    @ApiModelProperty("用户邮箱")
    @Email
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("用户个人介绍")
    private String intro;

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

    public UserES(User user) {
        this.id= user.getId();
        this.name = user.getName();
        this.email=user.getEmail();
        this.avatar = user.getAvatar();
        this.intro = user.getIntro();
        this.createTime = user.getCreateTime();
        this.updateTime = user.getUpdateTime();
    }
}
