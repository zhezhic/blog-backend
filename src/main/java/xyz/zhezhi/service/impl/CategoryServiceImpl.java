package xyz.zhezhi.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.zhezhi.common.CustomException;
import xyz.zhezhi.mapper.CategoryMapper;
import xyz.zhezhi.module.entity.Category;
import xyz.zhezhi.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhezhi
 * @className: CategoryServiceImpl
 * @description: 博客分类实现类
 * @date 2021/9/17 下午7:21
 * @version：1.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public int addCategory(Category category) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("name",category.getName());
        if (!categoryMapper.selectList(wrapper).isEmpty()) {
            throw new CustomException(400, "标签名已存在");
        }
        category.setAuthorId(StpUtil.getLoginIdAsLong());
        return categoryMapper.insert(category);
    }

    @Override
    public List<Category> getCategories(Long authorId) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id",authorId);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Category queryCategoryNameById(Long id) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper
                .eq("id", id).select("name")
        ;
        return categoryMapper.selectOne(wrapper);
    }

    @Override
    public List<Category> queryCategoryNameByIds(List<String> ids) {
        List<Long> collect = ids.stream().map(Long::valueOf).collect(Collectors.toList());
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.in("id", collect);
        return categoryMapper.selectList(wrapper);
    }
}
