package xyz.zhezhi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public R register(@RequestBody User user) {
        int result = userService.register(user);
        System.out.println(user);
        return R.ok().data("result",result);
    }
}
