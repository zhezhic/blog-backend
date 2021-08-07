package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.entity.User;

public interface UserService extends IService<User> {
    int register(User user);
}
