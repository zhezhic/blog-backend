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
import xyz.zhezhi.module.entity.Category;
import xyz.zhezhi.module.vo.BlogVO;
import xyz.zhezhi.module.vo.CategoryVO;
import xyz.zhezhi.service.BlogService;
import xyz.zhezhi.service.CategoryService;
import xyz.zhezhi.utils.UploadUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    CategoryService categoryService;

    public BlogController(BlogService blogService, CategoryService categoryService) {
        this.blogService = blogService;
        this.categoryService = categoryService;
    }

    @ApiOperation("上传图片")
    @SaCheckLogin
    @PostMapping("uploadImage")
    public R uploadImage(@RequestParam("file") MultipartFile imgFile) {
        if (imgFile.isEmpty()) {
            return R.error().message("请上传图片");
        }
        String originalFilename = imgFile.getOriginalFilename();
        assert originalFilename != null;
        String format = originalFilename.substring(originalFilename.lastIndexOf("."));
        try {
            // 构建真实的文件路径
            StringBuilder fileBuilder = new StringBuilder();
            fileBuilder
                    .append(UploadUtils.getBlogImage())
                    .append(StpUtil.getLoginIdAsString())
            ;
            File userFolder = new File(fileBuilder.toString());
            if (!userFolder.exists()) {
                if (!userFolder.mkdirs()) {
                    return R.internal_error();
                }
            }
            String fileName = UUID.randomUUID() + format;
            File severPath = new File(userFolder + File.separator + fileName);
            // 上传图片到 -> “绝对路径”
            imgFile.transferTo(severPath);
            return R.ok().data("imagePath",StpUtil.getLoginIdAsString()+File.separator+ fileName);
        } catch (IOException e) {
            return R.error().message("上传异常");
        }
    }

//    @GetMapping("getImage/{id}/{name}")
//    @ApiOperation("获取图片")
//    public void getImage(HttpServletResponse response, @PathVariable("id") String id,
//                         @PathVariable("name") String name) throws IOException {
//        OutputStream os = null;
//        BufferedImage image;
//        try {
//            image = ImageIO.read(new FileInputStream(UploadUtils.getBlogImage() + File.separator + id + File.separator + name));
//            //获取文件的后缀名 jpg
//            String suffix = name.substring(name.lastIndexOf(".") + 1);
//            os = response.getOutputStream();
//            response.setContentType("image/" + suffix);
//            if (image != null) {
//                ImageIO.write(image, suffix, os);
//            }
//        } finally {
//            if (os != null) {
//                os.flush();
//                os.close();
//            }
//        }
//    }

    @GetMapping("getCategories")
    @SaCheckLogin
    @ApiOperation("获取分类")
    public R getCategories() {
        List<Category> originCategories = categoryService.getCategories(StpUtil.getLoginIdAsLong());
        List<CategoryVO> categories = new ArrayList<>();
        for (Category originCategory : originCategories) {
            categories.add(new CategoryVO(originCategory));
        }
        List<CategoryVO> finalCategories = categories;
        categories = categories.stream()
                .filter(e -> e.getParentId().equals(0L))
                .peek(e -> e.setChildren(getChildren(e, finalCategories)))
                .collect(Collectors.toList());
        return R.ok().data("categories", categories);
    }

    private List<CategoryVO> getChildren(CategoryVO root, List<CategoryVO> categoryVOList) {
        return categoryVOList
                .stream()
                .filter(e -> Objects.equals(e.getParentId(), root.getId()))
                .peek(e -> e.setChildren(getChildren(e, categoryVOList)))
                .collect(Collectors.toList());
    }

    @PostMapping("addCategory")
    @SaCheckLogin
    @ApiOperation("添加分类")
    public R addCategory(@Validated @RequestBody Category category) {
        if (categoryService.addCategory(category) >= 1) {
            return R.ok().message("添加成功");
        }
        return R.error().message("添加失败");
    }

    @PostMapping("release")
    @SaCheckLogin
    @ApiOperation("发布")
    public R release(@Validated @RequestBody Blog blog) {
        blog.setAuthorId(StpUtil.getLoginIdAsLong());
        if (blogService.release(blog) >= 1) {
            return R.ok().message("发布成功");
        }
        return R.error().message("发布失败");
    }

    @PostMapping("updateBlog")
    @SaCheckLogin
    @ApiOperation("更新博客")
    public R updateBlog(@Validated @RequestBody Blog blog) {
        blog.setAuthorId(StpUtil.getLoginIdAsLong());
        if (blogService.updateBlog(blog) >= 1) {
            return R.ok().message("更新成功");
        }
        return R.error().message("更新失败");
    }

    @DeleteMapping("deleteBlogById/{id}")
    @SaCheckLogin
    @ApiOperation("删除博客")
    public R deleteBlogById(@PathVariable("id") String id) {
        int i = blogService.deleteBlogById(id, StpUtil.getLoginIdAsString());
        if (i == 1) {
            return R.ok().message("删除成功");
        }
        return R.error().message("删除失败");
    }

    @GetMapping("queryBlogPage/{current}/{size}")
    public R queryBlogPage(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        BlogVO blogVO = blogService.selectPage(current, size);
        return R.ok().data("blog", blogVO);
    }

    @GetMapping("queryBlogById/{id}")
    public R queryBlogById(@PathVariable("id") String id) {
        Blog blog = blogService.queryBlogById(Long.valueOf(id));
        blogService.updateBlogHot(id);
        return R.ok().data("blog", blog);
    }

    @GetMapping("queryBlogsByUserId/{current}/{size}")
    @SaCheckLogin
    public R queryBlogsByUserId(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
        BlogVO blogVO = blogService.queryBlogPageByUserId(current, size, StpUtil.getLoginIdAsLong());
        return R.ok().data("blog", blogVO);
    }

    @GetMapping("queryBlogsByOtherUserId/{current}/{size}/{id}")
    public R queryBlogsByOtherUserId(@PathVariable("current") Integer current, @PathVariable("size") Integer size,
                                     @PathVariable Long id) {
        BlogVO blogVO = blogService.queryBlogPageByOtherUserId(current, size, id);
        return R.ok().data("blog", blogVO);
    }

    @GetMapping("queryBlogByIds/{ids}")
    public R queryBlogByIds(@PathVariable("ids") List<String> ids) {
        List<Blog> blogs = blogService.queryBlogVOByIds(ids);
        return R.ok().data("blogs", blogs);
    }

    @GetMapping("queryCategoryNameById/{id}")
    public R queryCategoryNameById(@PathVariable("id") String id) {
        Category category = categoryService.queryCategoryNameById(Long.valueOf(id));
        return R.ok().data("name", category.getName());
    }

    @GetMapping("queryCategoriesByUserId/{id}")
    public R queryCategoriesByUserId(@PathVariable("id") String id) {
        List<Category> categories = categoryService.queryCategoriesByUserId(id);
        return R.ok().data("categories", categories);
    }

    @GetMapping("queryCategoryNameByIds/{ids}")
    public R queryCategoryNameByIds(@PathVariable("ids") List<String> ids) {
        List<Category> categories = categoryService.queryCategoryNameByIds(ids);
        return R.ok().data("categories", categories);
    }
}
