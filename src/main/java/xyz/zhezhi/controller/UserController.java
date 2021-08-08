package xyz.zhezhi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.zhezhi.entity.User;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.R;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user/")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@Validated @RequestBody User user){
        if (user.getPassword().length() < 6) {
            return R.error().message("密码不合法");
        }
        int result = userService.register(user);
        System.out.println(user);
        return R.ok().message("注册成功");
    }
}
