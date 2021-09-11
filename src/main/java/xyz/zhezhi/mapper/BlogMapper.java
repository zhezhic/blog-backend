package xyz.zhezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.zhezhi.module.entity.Blog;

/**
 * @author zhezhi
 * @className: BlogMapper
 * @description: blog mappre
 * @date 2021/9/4 下午1:41
 * @version：1.0
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {
}
