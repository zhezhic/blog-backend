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
import xyz.zhezhi.module.entity.Upload;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.UploadUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
    Upload upload;

    public UserController(UserService userService, Upload upload) {
        this.userService = userService;
        this.upload = upload;
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
        String imgDirFile = UploadUtils.getAvatarPath();
        String format = imgFile.getContentType().split("/")[1];
        try {
            // 构建真实的文件路径
            File newFile = new File(imgDirFile + File.separator + StpUtil.getLoginIdAsString() + "." + format);
            // 上传图片到 -》 “绝对路径”
            imgFile.transferTo(newFile);
            userService.setAvatar(StpUtil.getLoginIdAsString(), format);
            return R.ok().message("上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return R.error().message("上传异常");
        }
    }

    @GetMapping("getAvatar/{name}")
    @ApiOperation("获取用户头像")
    public void getAvatar(HttpServletResponse response, @PathVariable("name") String name) throws IOException {
        OutputStream os = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new FileInputStream(UploadUtils.getAvatarPath() + name));
            os = response.getOutputStream();
            //获取文件的后缀名 jpg
            String suffix = name.substring(name.lastIndexOf(".") + 1);
            response.setContentType("image/" + suffix);
            if (image != null) {
                ImageIO.write(image, suffix, os);
            }
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
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
