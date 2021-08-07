package xyz.zhezhi.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel("用户实体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @ApiModelProperty("用户ID")
    private Integer id;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户密码")
    private String password;
    @TableLogic
    private Integer deleted;
}
