package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.dto.user.UserLogin;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;

public interface UserService extends IService<User> {
    int register(User user);

    Boolean login(UserLogin user);

    UserInfo info();
}
