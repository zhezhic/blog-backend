package xyz.zhezhi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.zhezhi.common.R;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.service.BlogService;
import xyz.zhezhi.utils.UploadUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author zhezhi
 * @className: EditorController
 * @description: 编辑器controller
 * @date 2021/8/31 下午7:39
 * @version：1.0
 */
@Api(tags = "博客管理")
@RestController
@RequestMapping("/blog/")
public class BlogController {
    BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @ApiOperation("上传图片")
    @SaCheckLogin
    @PostMapping("uploadImage")
    public R uploadImage(@RequestParam("file") MultipartFile imgFile) {
        if (imgFile.isEmpty()) {
            return R.error().message("请上传图片");
        }
        String format = imgFile.getContentType().split("/")[1];
        try {
            // 构建真实的文件路径
            File userFolder = new File(UploadUtils.getBlogImage() + File.separator + StpUtil.getLoginIdAsString());
            if (!userFolder.exists()) {
                if (!userFolder.mkdirs()) {
                    return R.internal_error();
                }
            }
            String imageName =UUID.randomUUID()+"." + format;
            File newFile = new File(userFolder+File.separator+imageName);
            // 上传图片到 -》 “绝对路径”
            imgFile.transferTo(newFile);
            return R.ok().data("imagePath","/blog/getImage/"+StpUtil.getLoginIdAsString()+File.separator+imageName);
        } catch (IOException e) {
            return R.error().message("上传异常");
        }
    }
    @GetMapping("getImage/{id}/{name}")
    @ApiOperation("获取图片")
    public void getImage(HttpServletResponse response,@PathVariable("id") String id,@PathVariable("name") String name) throws IOException {
        OutputStream os = null;
        BufferedImage image;
        try {
            image = ImageIO.read(new FileInputStream(UploadUtils.getBlogImage()+File.separator + id + File.separator+name));
            //获取文件的后缀名 jpg
            String suffix = name.substring(name.lastIndexOf(".")+1);
            os = response.getOutputStream();
            response.setContentType("image/"+suffix);
            if (image != null) {
                ImageIO.write(image,suffix, os);
            }
        } finally {
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
    @PostMapping("release")
    @SaCheckLogin
    @ApiOperation("发布")
    public R release(@Validated @RequestBody Blog blog)  {
        blog.setAuthorId(StpUtil.getLoginIdAsLong());
        if (blogService.release(blog)>=1) {
            return R.ok().message("发布成功");
        }
        return R.error().message("发布失败");
    }
}