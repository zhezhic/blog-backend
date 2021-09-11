package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.dto.user.UserEditPassword;
import xyz.zhezhi.module.dto.user.UserLogin;
import xyz.zhezhi.module.dto.user.UserProfile;
import xyz.zhezhi.module.entity.User;
import xyz.zhezhi.module.vo.UserInfo;
/**
  * @author zhezhi
  * @className: UserService
  * @description: 用户service类
  * @date 2021/8/17 下午9:24
  * @version：1.0
  */
public interface UserService extends IService<User> {
    int register(User user);

    Boolean login(UserLogin user);
    UserInfo infoById(String id);
    UserInfo infoByName(String name);
    int editProfileById(UserProfile userProfile, String id);
    int editPasswordById(UserEditPassword userEditPassword, String id);
    String getAvatar(String id);
    int setAvatar(String id,String format);
}
