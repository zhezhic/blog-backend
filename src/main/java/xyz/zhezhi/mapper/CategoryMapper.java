package xyz.zhezhi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import xyz.zhezhi.module.entity.Category;

/**
 * @author zhezhi
 * @className: Category
 * @description: 博客分类映射类
 * @date 2021/9/17 下午7:12
 * @version：1.0
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}
