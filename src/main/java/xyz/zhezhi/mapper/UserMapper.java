package xyz.zhezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.zhezhi.module.entity.User;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
