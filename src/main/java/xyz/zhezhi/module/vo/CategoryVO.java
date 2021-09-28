package xyz.zhezhi.module.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.zhezhi.module.entity.Category;

import java.util.List;

/**
 * @author zhezhi
 * @className: CategoryInfo
 * @description: 分类视图类
 * @date 2021/9/19 下午4:17
 * @version：1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {
    @ApiModelProperty("分类id")
    private Long id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("父分类id")
    private Long parentId;

    @ApiModelProperty("子分类")
    private List<CategoryVO> children;

    @ApiModelProperty("排序")
    private Integer rank;

    public CategoryVO(Category category) {
        this.id=category.getId();
        this.name=category.getName();
        this.authorId=category.getAuthorId();
        this.parentId=category.getParentId();
        this.rank=category.getRank();
    }
}
