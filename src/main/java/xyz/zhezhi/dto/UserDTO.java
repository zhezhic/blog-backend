package xyz.zhezhi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户传输类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ApiModelProperty("用户ID")
    private Long id;
    @ApiModelProperty("用户名称")
    private String name;
}
