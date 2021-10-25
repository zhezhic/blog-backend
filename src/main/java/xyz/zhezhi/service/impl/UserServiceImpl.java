package xyz.zhezhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.common.ElasticSearchIndex;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.module.dto.user.UserES;
import xyz.zhezhi.module.dto.user.UserEditPassword;
import xyz.zhezhi.module.dto.user.UserLogin;
import xyz.zhezhi.module.dto.user.UserProfile;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
import xyz.zhezhi.service.UserService;
import xyz.zhezhi.utils.ElasticSearchUtils;
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
    public UserInfo getInfoById(Long id) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("id", id)
        ;
        User u = userMapper.selectOne(wrapper);
        if (u == null) {
            throw new CustomException(400, "用户不存在");
        }
        return new UserInfo(u);
    }

    @Override
    public UserInfo infoByName(String name) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        User u = userMapper.selectOne(wrapper);
        if (u == null) {
            throw new CustomException(400, "用户不存在");
        }
        return new UserInfo(u);
    }


    @Override
    public int updateProfileById(UserProfile userProfile) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("email", userProfile.getEmail())
                .or()
                .eq("name", userProfile.getName())
        ;
        List<User> users = userMapper.selectList(queryWrapper);
        for (User u : users) {
            if (userProfile.getId().equals(u.getId())) {
                continue;
            }
            if (u.getName().equals(userProfile.getName())) {
                throw new CustomException(400, "用户名已注册");
            } else if (u.getEmail().equals(userProfile.getEmail())) {
                throw new CustomException(400, "邮箱已注册");
            }
        }
        User user = new User(userProfile);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("id", user.getId())
                .set("name", user.getName())
                .set("email",user.getEmail())
                .set("intro",user.getIntro())
        ;
        int update = userMapper.update(user, updateWrapper);
        System.err.println(user);
        ElasticSearchUtils.updateRequestByUser(user,ElasticSearchIndex.USER.getIndex());
        return update;
    }

    @Override
    public int editPasswordById(UserEditPassword userEditPassword, String id) {
        if (userEditPassword.getOriginPassword().equals(userEditPassword.getNewPassword())) {
            throw new CustomException(400, "新旧密码一致");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("id", id)
                .eq("password", PasswordEncrypt.encrypt(userEditPassword.getOriginPassword()))
        ;
        if (userMapper.selectOne(queryWrapper) == null) {
            throw new CustomException(400, "原密码错误");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("password", PasswordEncrypt.encrypt(userEditPassword.getNewPassword()));
        User user = new User();
        return userMapper.update(user, updateWrapper);
    }

    @Override
    public String getAvatarById(Long id) {
        User user = userMapper.selectById(id);
        if (user.getAvatar().isBlank()) {
            throw new CustomException(400, "无头像");
        }
        return user.getAvatar();
    }

    @Override
    public int setAvatar(String id,String avatarUrl) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("avatar",avatarUrl);
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
        try {
            GenerateAvatar.name(user.getName(),String.valueOf(user.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder avatarUrl = new StringBuilder();
        avatarUrl
                .append("http://127.0.0.1:8087/user/getAvatar/")
                .append(user.getId())
                .append(".")
                .append("png");
        result = setAvatar(String.valueOf(user.getId()), avatarUrl.toString());
        if (result > 0) {
            user.setAvatar(avatarUrl.toString());
        }
        ElasticSearchUtils.IndexRequest(new UserES(user), ElasticSearchIndex.USER.getIndex(),String.valueOf(user.getId()));
        return result;
    }

}
