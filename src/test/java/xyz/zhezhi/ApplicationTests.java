package xyz.zhezhi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhezhi.mapper.BlogMapper;
import xyz.zhezhi.mapper.CategoryMapper;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.entity.Category;
import xyz.zhezhi.module.entity.Upload;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.CategoryVO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    Upload upload;

    @Test
    void contextLoads() {
        User user = new User();
        user.setName("zhezhi");
        user.setPassword("0852159632");
        user.setEmail("25444@qq.com");
        int insert = userMapper.insert(user);
        System.out.println(insert);
        System.out.println(user);
    }

    @Test
    void query() {
        User user = new User();
        user.setEmail("2544438809@qq.com");
        user.setName("zhezhi");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(User::getEmail, user.getEmail())
                .or()
                .eq(User::getName, user.getName())
        ;
        List<User> users = userMapper.selectList(wrapper);

    }

    @Test
    void login() {
        User user = new User();
        user.setEmail("22@q.com");
        user.setPassword("664d568834a8e92e6aafa5ab7843d634");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email", user.getEmail())
        ;
        User u = userMapper.selectOne(wrapper);
        if (user.getPassword().equals(u.getPassword())) {
            System.err.println("登陆成功");
        }
        System.err.println("-------");
    }

    @Test
    void register() throws IOException {
        System.out.println(upload.getAvatar());
    }

    @Test
    void category() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", 1428556050647941121L);
        List<Category> originCategories = categoryMapper.selectList(wrapper);
        List<CategoryVO> categories = new ArrayList<>();
        for (Category originCategory : originCategories) {
            categories.add(new CategoryVO(originCategory));
        }
        List<CategoryVO> finalCategories = categories;
        categories = categories
                .stream()
                .filter(e -> e.getParentId().equals(0L))
                .map(e -> {
                    System.out.println("map=>" + e);
                    e.setChildren(getChildren(e, finalCategories));
                    return e;
                }).collect(Collectors.toList());
        categories.forEach(System.err::println);
    }

    private List<CategoryVO> getChildren(CategoryVO root, List<CategoryVO> categoryVOList) {
        List<CategoryVO> finalCategories = categoryVOList;
        List<CategoryVO> childrenList = categoryVOList
                .stream()
                .filter(e -> e.getParentId().equals(root.getParentId()))
                .map(e -> {
                            System.out.println("map=>" + e);
                            e.setChildren(getChildren(e, finalCategories));
                            return e;
                        }
                ).collect(Collectors.toList());
        return childrenList;
    }

    @Test
    public void randomBackground() throws Exception {
        String suffix = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=2&n=1";
        String prefix = "https://api.btstu.cn/sjbz/?lx=dongman";
        HttpURLConnection connection = (HttpURLConnection) new URL(suffix).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(60000);
        connection.connect();
        if (connection.getResponseCode() == 200) {
           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String images = JSONObject.parseObject(br.readLine()).getString("images");
            JSONArray array = JSONObject.parseArray(images);
            String s = array.get(0).toString();
            String url = JSONObject.parseObject(s).getString("url");
            System.out.println(url);
        }
    }

    @Test
    public void blogPage() {
        IPage<Blog> blogPage = new Page<>(2, 2);
        blogPage = blogMapper.selectPage(blogPage, null);
        List<Blog> records = blogPage.getRecords();
        for (Blog record : records) {
            System.out.println(record);
        }

    }
}
