package xyz.zhezhi.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.dto.UserLogin;
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
        Boolean isLogin = userService.login(user);
        if (isLogin) {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return R.ok().message("登陆成功").data(tokenInfo.getTokenName(), tokenInfo.getTokenValue());
        }
        return R.error().message("密码错误");
    }

    @GetMapping("info")
    @ApiOperation("用户信息")
    public R info() {
        if (StpUtil.isLogin()) {
            UserInfo userInfo = userService.infoById(StpUtil.getLoginIdAsString());
            return R.ok().data("userInfo", userInfo);
        }
        return R.error().message("无令牌");
    }

    @DeleteMapping("logout")
    @ApiOperation("用户注销")
    public R logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logoutByTokenValue(StpUtil.getTokenValue());
        }
        return R.ok().message("注销成功");
    }

    @PostMapping("addAvatar")
    @ApiOperation("添加用户头像")
    public R addAvatar(@RequestParam("file") MultipartFile imgFile) {
        if (StpUtil.isLogin()) {
            if (imgFile.isEmpty()) {
                return R.error().message("请上传图片");
            }
            String imgDirFile = UploadUtils.getImgFolder();
            try {
                // 构建真实的文件路径
                File newFile = new File(imgDirFile + File.separator + StpUtil.getLoginIdAsString() + ".jpg");
                // 上传图片到 -》 “绝对路径”
                imgFile.transferTo(newFile);
                return R.ok().message("上传成功");
            } catch (IOException e) {
                e.printStackTrace();
                return R.error().message("上传异常");
            }
        }
        return R.ok().message("无权限");
    }

    @GetMapping("getAvatar")
    @ApiOperation("获取用户头像")
    public void getAvatar(HttpServletResponse response, String id) throws IOException {
        OutputStream os = null;
        BufferedImage image;
//        String imgPath = UploadUtils.getImgDirFile()+File.separator + id + ".jpg";
        try {
            image = ImageIO.read(new FileInputStream(upload.getAvatar()+id+".png"));
            os = response.getOutputStream();
            response.setContentType("image/png");
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
}
