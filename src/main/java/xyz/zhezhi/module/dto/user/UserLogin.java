package xyz.zhezhi.module.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
  * @author zhezhi
  * @className: UserLogin
  * @description: 用户登陆dto类
  * @date 2021/8/17 下午9:23
  * @version：1.0
  */
@ApiModel("用户登陆类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @ApiModelProperty("用户邮箱")
    @Email(message = "邮箱不合规范")
    @NotBlank(message = "邮箱不能为空")
    private String email;
    @ApiModelProperty("用户密码")
    @Size(min = 6,max = 20,message = "密码长度不合法")
    @NotBlank(message = "密码不能为空")
    private String password;
}
