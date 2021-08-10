package xyz.zhezhi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.entity.User;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.PasswordEncrypt;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int register(User user) {
        user.setPassword(PasswordEncrypt.encypt(user.getPassword()));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email", user.getEmail())
                .or()
                .eq("name", user.getName())
        ;
        List<User> users = userMapper.selectList(wrapper);
        for (User u : users) {
            if (u.getName().equals(user.getName()) ) {
                throw new CustomException(400, "用户名已注册");
            }else if (u.getEmail().equals(user.getEmail())){
                throw new CustomException(400, "邮箱已注册");
            }
        }
        return userMapper.insert(user);
    }

}
