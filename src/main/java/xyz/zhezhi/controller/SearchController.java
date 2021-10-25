package xyz.zhezhi.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.zhezhi.common.ElasticSearchIndex;
import xyz.zhezhi.common.R;
import xyz.zhezhi.service.ElasticSearchService;

import java.util.List;
import java.util.Map;

/**
 * @author zhezhi
 * @className: SearchController
 * @description:
 * @date 2021/10/18 上午9:57
 * @version：1.0
 */
@Api(tags = "搜索" +
        "")
@RestController
@RequestMapping("/search/")
public class SearchController {
    ElasticSearchService elasticSearchService;

    public SearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @GetMapping("/title/{keyword}/{current}/{size}")
    public R searchTitleOfBlog(@PathVariable("keyword") String keyword,
                    @PathVariable("current") int current,
                    @PathVariable("size") int size){
        List<Map<String, Object>> list = elasticSearchService.searchTitle(keyword,
                ElasticSearchIndex.BLOG.getIndex(),false, current, size, "title");
        return R.ok().data("list", list);
    }
    @GetMapping("/blog/{content}/{current}/{size}")
    public R multiSearchOfBlog(@PathVariable("content") String content,
                         @PathVariable("current") int current,
                         @PathVariable("size") int size){
        List<Map<String, Object>> blogs = elasticSearchService.searchBlog(content,ElasticSearchIndex.BLOG.getIndex(), current,
                size,
                "title", "content");
        return R.ok().data("list",blogs);
    }
    @GetMapping("/name/{keyword}/{current}/{size}")
    public R searchUserName(@PathVariable("keyword") String keyword,
                         @PathVariable("current") int current,
                         @PathVariable("size") int size){
        List<Map<String, Object>> list = elasticSearchService.searchUserName(keyword,
                ElasticSearchIndex.USER.getIndex(),true, current, size, "name");
        return R.ok().data("list",list);
    }
}
