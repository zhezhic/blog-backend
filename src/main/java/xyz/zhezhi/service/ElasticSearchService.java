package xyz.zhezhi.service;

import java.util.List;
import java.util.Map;

/**
 * @author zhezhi
 * @className: ElasticSearchService
 * @description:
 * @date 2021/10/25 上午9:06
 * @version：1.0
 */
public interface ElasticSearchService{
    List<Map<String, Object>> searchTitle(String keyword,String index,boolean returnAll, int current, int size,  String fieldName);
    List<Map<String,Object>> searchBlog(String content,String index, int current, int size,String... fieldNames);
    List<Map<String,Object>> searchUserName(String keyword,String index,boolean returnAll,int current, int size,String fieldName);
}
