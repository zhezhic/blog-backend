package xyz.zhezhi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.zhezhi.module.entity.Category;

import java.util.List;

/**
 * @author zhezhi
 * @className: CategoryService
 * @description: 博客分类service类
 * @date 2021/9/17 下午7:19
 * @version：1.0
 */
public interface CategoryService extends IService<Category> {
    int addCategory(Category category);
    List<Category> getCategories(Long authorId);
    Category queryCategoryNameById(Long id);
    List<Category> queryCategoryNameByIds(List<String> ids);
}
