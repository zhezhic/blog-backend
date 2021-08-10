package xyz.zhezhi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhezhi.entity.User;
import xyz.zhezhi.mapper.UserMapper;

import java.util.List;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;

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
        wrapper
                .eq("email", user.getEmail())
                .or()
                .eq("name",user.getName())
        ;
        List<User> users = userMapper.selectList(wrapper);

    }

}
