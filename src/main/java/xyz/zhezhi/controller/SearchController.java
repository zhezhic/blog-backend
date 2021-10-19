package xyz.zhezhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhezhi.common.ElasticSearchIndex;
import xyz.zhezhi.common.R;
import xyz.zhezhi.service.BlogService;

import java.util.List;
import java.util.Map;

/**
 * @author zhezhi
 * @className: SearchController
 * @description:
 * @date 2021/10/18 上午9:57
 * @version：1.0
 */
@RestController
public class SearchController {
    BlogService blogService;

    public SearchController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/searchTitle/{keyword}/{current}/{size}")
    public R searchTitle(@PathVariable("keyword") String keyword,
                    @PathVariable("current") int current,
                    @PathVariable("size") int size){
        List<Map<String, Object>> list = blogService.searchByKeyword(keyword, current,size, ElasticSearchIndex.BLOG.getIndex(),"title");
        return R.ok().data("list", list);
    }
}
