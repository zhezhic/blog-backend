package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.entity.User;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.PasswordEncrypt;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int register(User user) {
        user.setPassword(PasswordEncrypt.encypt(user.getPassword()));
        return userMapper.insert(user);
    }
}
