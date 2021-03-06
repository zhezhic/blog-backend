package xyz.zhezhi.module.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
  * @author zhezhi
  * @className: UserInfo
  * @description: 用户信息vo类
  * @date 2021/8/17 下午9:23
  * @version：1.0
  */
@ApiModel("用户信息类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    @ApiModelProperty("用户ID")
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
    public UserInfo(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.intro = user.getIntro();
    }
}
