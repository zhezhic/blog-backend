package xyz.zhezhi;

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
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
