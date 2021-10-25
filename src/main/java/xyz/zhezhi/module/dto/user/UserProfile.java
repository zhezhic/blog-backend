package xyz.zhezhi.module.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
  * @className: UserInfo
  * @description: 用户信息vo类
  * @date 2021/8/17 下午9:23
  * @version：1.0
  */
@ApiModel("用户信息类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
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
    @ApiModelProperty("用户个人介绍")
    private String intro;
}
