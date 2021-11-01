package xyz.zhezhi.service.impl;

import org.springframework.stereotype.Service;
import xyz.zhezhi.service.ElasticSearchService;
import xyz.zhezhi.utils.ElasticSearchUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhezhi
 * @className: ElasticSearchServiceImpl
 * @description:
 * @date 2021/10/25 上午9:08
 * @version：1.0
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Override
    public List<Map<String, Object>> searchTitle(String keyword,String index, boolean returnAll,int current, int size,
                                                 String fieldName) {
        return ElasticSearchUtils.searchRequest(keyword, index,returnAll, current, size, fieldName);
    }

    @Override
    public List<Map<String, Object>> searchBlog(String content, String index, int current, int size,
                                                String... fieldNames) {
        return ElasticSearchUtils.multiSearchRequest(content, index, current, size,
                fieldNames);
    }

    @Override
    public List<Map<String, Object>> searchUserName(String keyword, String index,boolean returnAll, int current, int size, String fieldName) {
        return ElasticSearchUtils.searchRequestByName(keyword, index,returnAll, current, size, fieldName);
    }
}
