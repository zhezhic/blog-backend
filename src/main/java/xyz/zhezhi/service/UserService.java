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
    UserInfo getInfoById(Long id);
    UserInfo infoByName(String name);
    int updateProfileById(UserProfile userProfile);
    int editPasswordById(UserEditPassword userEditPassword, String id);
    String getAvatarById(Long id);
    int setAvatar(String id,String format);
}
