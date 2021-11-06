package xyz.zhezhi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.dto.user.UserEditPassword;
import xyz.zhezhi.module.dto.user.UserLogin;
import xyz.zhezhi.module.dto.user.UserProfile;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.UploadUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhezhi
 * @className: UserController
 * @description: 用户controller类
 * @date 2021/8/17 下午9:20
 * @version：1.0
 */
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
        userService.register(user);
        return R.ok().message("注册成功");
    }

    @PostMapping("login")
    @ApiOperation("登陆")
    public R login(@Validated @RequestBody UserLogin user) {
        if (userService.login(user)) {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok().message("登陆成功").data(tokenInfo.getTokenName(), tokenInfo.getTokenValue());
        }
        return R.error().message("密码错误");
    }

    @GetMapping("getInfo")
    @SaCheckLogin
    @ApiOperation("用户信息")
    public R getInfo() {
        UserInfo userInfo = userService.getInfoById(StpUtil.getLoginIdAsLong());
        return R.ok().data("userInfo", userInfo);
    }
    @GetMapping("getInfoById/{id}")
    @ApiOperation("根据id获取用户信息")
    public R getInfoById(@PathVariable("id") String id) {
        UserInfo userInfo = userService.getInfoById(Long.valueOf(id));
        return R.ok().data("userInfo", userInfo);
    }

    @DeleteMapping("logout")
    @SaCheckLogin
    @ApiOperation("用户注销")
    public R logout() {
        StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
        return R.ok().message("注销成功");
    }

    @PostMapping("updateAvatar")
    @SaCheckLogin
    @ApiOperation("添加用户头像")
    public R updateAvatar(@RequestParam("file") MultipartFile imgFile) {
        if (imgFile.isEmpty()) {
            return R.error().message("请上传图片");
        }
        String originalFilename = imgFile.getOriginalFilename();
        assert originalFilename != null;
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = StpUtil.getLoginIdAsString()+format;
        String datePath = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        try {
            // 构建真实的文件路径
            StringBuilder fileBuilder = new StringBuilder();
            fileBuilder
                    .append(UploadUtils.getAvatarPath()) //头像根路径
                    .append(datePath) //头像日期文件夹
            ;
            File filePath = new File(fileBuilder.toString());
            if (!filePath.exists()) {
                if (!filePath.mkdirs()) {
                    return R.internal_error();
                }
            }
            File absolutePath = new File(fileBuilder.toString(), fileName);
            // 上传图片到 -> “绝对路径”
            imgFile.transferTo(absolutePath);
            userService.setAvatar(StpUtil.getLoginIdAsString(),datePath+ fileName);
            return R.ok().message("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("上传异常");
        }
    }

    @GetMapping("getAvatarById/{id}")
    @ApiOperation("根据用户id获取用户头像")
    public R getAvatarById(@PathVariable("id") String id) {
        String avatar = userService.getAvatarById(Long.valueOf(id));
        return R.ok().data("avatar", avatar);
    }

    @PostMapping("updateProfile")
    @SaCheckLogin
    @ApiOperation("更新用户个人资料")
    public R updateProfileById(@Validated @RequestBody UserProfile userProfile) {
        userProfile.setId(StpUtil.getLoginIdAsLong());
        int result = userService.updateProfileById(userProfile);
        if (result >= 1) {
            return R.ok().message("保存成功");
        }
        return R.error().message("保存失败");
    }

    @PostMapping("updatePassword")
    @SaCheckLogin
    @ApiOperation("更新用户密码")
    public R updatePassword(@Validated @RequestBody UserEditPassword userEditPassword) {
        int result = userService.editPasswordById(userEditPassword, StpUtil.getLoginIdAsString());
        if (result >= 1) {
            return R.ok().message("修改成功");
        }
        return R.error().message("修改失败");
    }
}
