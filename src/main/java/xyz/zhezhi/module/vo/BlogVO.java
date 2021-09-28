package xyz.zhezhi.module.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.entity.Blog;

import java.util.List;

/**
 * @author zhezhi
 * @className: Blog
 * @description: blog entity
 * @date 2021/9/4 下午1:37
 * @version：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogVO {
    private Integer current;
    private Integer size;
    private Long total;
    private List<Blog> blogList;
}
