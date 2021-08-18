package xyz.zhezhi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.module.entity.Upload;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.utils.GenerateAvatar;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;
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
    void test() {
        try {
            GenerateAvatar.name("李","1267899dgffdgd143gdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
