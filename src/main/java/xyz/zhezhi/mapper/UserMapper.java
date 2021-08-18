package xyz.zhezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.zhezhi.module.entity.User;
/**
  * @author zhezhi
  * @className: UserMapper
  * @description: 用户dao类
  * @date 2021/8/17 下午9:22
  * @version：1.0
  */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
