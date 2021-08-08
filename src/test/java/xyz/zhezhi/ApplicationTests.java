package xyz.zhezhi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhezhi.entity.User;
import xyz.zhezhi.mapper.UserMapper;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        User user = new User();
        user.setName("zz");
        user.setPassword("123");
        user.setEmail("2544.");
        int insert = userMapper.insert(user);
        System.out.println(insert);
        System.out.println(user);
    }

}
