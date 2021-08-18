package xyz.zhezhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.module.dto.UserLogin;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.GenerateAvatar;
import xyz.zhezhi.utils.PasswordEncrypt;

import java.io.IOException;
import java.util.List;

/**
 * @author zhezhi
 * @className: UserServiceImpl
 * @description: 用户service实现类
 * @date 2021/8/17 下午9:24
 * @version：1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Boolean login(UserLogin user) {
        user.setPassword(PasswordEncrypt.encrypt(user.getPassword()));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email", user.getEmail())
        ;
        User u = userMapper.selectOne(wrapper);
        if (u == null) {
            throw new CustomException(400, "邮箱不存在");
        }
        if (user.getPassword().equals(u.getPassword())) {
            StpUtil.login(u.getId());
            return true;
        }
        return false;
    }

    @Override
    public UserInfo infoById(String id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("id", id)
        ;
        User u = userMapper.selectOne(wrapper);
        if (u == null) {
            throw new CustomException(400, "用户不存在");
        }
        return new UserInfo(u.getId(), u.getName(), u.getEmail(), u.getAvatar());
    }

    @Override
    public UserInfo infoByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User u = userMapper.selectOne(wrapper);
        if (u == null) {
            throw new CustomException(400, "用户不存在");
        }
        return new UserInfo(u.getId(), u.getName(), u.getEmail(), u.getAvatar());
    }

    @Override
    public String getAvatar(String id) {
        User user = userMapper.selectById(id);
        if (user.getAvatar().isBlank()) {
            throw new CustomException(400, "无头像");
        }
        return user.getAvatar();
    }

    @Override
    public int setAvatar(String id) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("avatar","http://127.0.0.1:8087/user/getAvatar?id="+id);
        return userMapper.update(null, wrapper);
    }

    @Override
    public int register(User user) {
        user.setPassword(PasswordEncrypt.encrypt(user.getPassword()));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email", user.getEmail())
                .or()
                .eq("name", user.getName())
        ;
        List<User> users = userMapper.selectList(wrapper);
        for (User u : users) {
            if (u.getName().equals(user.getName())) {
                throw new CustomException(400, "用户名已注册");
            } else if (u.getEmail().equals(user.getEmail())) {
                throw new CustomException(400, "邮箱已注册");
            }
        }
        int result = userMapper.insert(user);
        if (!(result >= 1)) {
            throw new CustomException(400, "注册失败");
        }
        UserInfo userInfo = infoByName(user.getName());
        try {
            GenerateAvatar.name(userInfo.getName(), String.valueOf(userInfo.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAvatar(String.valueOf(userInfo.getId()));
        return result;
    }

}
