package xyz.zhezhi.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.dto.user.UserLogin;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
import xyz.zhezhi.service.UserService;

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
    public R register(@Validated @RequestBody User user) {
        int result = userService.register(user);
        if (result >= 1) {
            return R.ok().message("注册成功");
        }

        return R.error().message("注册失败");
    }

    @PostMapping("login")
    @ApiOperation("登陆")
    public R login(@Validated @RequestBody UserLogin user) {
        Boolean isLogin = userService.login(user);
        if (isLogin) {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok().message("登陆成功").data(tokenInfo.getTokenName(),tokenInfo.getTokenValue());
        }
        return R.error().message("密码错误");
    }
    @GetMapping("info")
    @ApiOperation("用户信息")
    public R info() {
        UserInfo userInfo = userService.info();
        return R.ok().data("userInfo",userInfo);
    }
    @DeleteMapping("logout")
    @ApiOperation("用户注销")
    public R logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
        }
        return R.ok().message("注销成功");
    }
}
