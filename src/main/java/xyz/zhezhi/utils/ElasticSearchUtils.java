package xyz.zhezhi.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author zhezhi
 * @className: ElasticSearchUtils
 * @description:
 * @date 2021/10/18 上午8:57
 * @version：1.0
 */
@Component
public class ElasticSearchUtils {
    private static RestHighLevelClient restHighLevelClient;

    public ElasticSearchUtils(RestHighLevelClient restHighLevelClient) {
        ElasticSearchUtils.restHighLevelClient = restHighLevelClient;
    }

    public static void IndexRequest(Object object, String index, String id) {
        IndexRequest indexRequest = new IndexRequest(index);
        indexRequest
                .id(id)
                .timeout("10s")
                .source(JSON.toJSONString(object), XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Map<String,Object>> searchRequest(String keyword, int current, int size,String index,String properties) {
        if(current < 0){
            current = 0;
        }
        //条件查询
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(current);
        sourceBuilder.size(size);
        //match 匹配
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(properties, keyword);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮
//        HighlightBuilder highlightBuilder = new HighlightBuilder();
//        highlightBuilder.requireFieldMatch(false);//多个高亮显示
//        highlightBuilder.field(properties);
//        highlightBuilder.preTags("<span style='color:red'>");
//        highlightBuilder.postTags("</span>");
//        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析结果
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        try {
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
//                Map<String, HighlightField> fields = documentFields.getHighlightFields();
//                HighlightField title = fields.get(properties);
                Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//原来的结果
                //解析高亮的字段
//                if(title!=null){
//                    Text[] texts = title.fragments();
//                    StringBuilder newTitle = new StringBuilder();
//                    for (Text text : texts) {
//                        newTitle.append(text);
//                    }
//                    sourceAsMap.put("title", newTitle.toString());//高亮字段替换掉原来的内容即可
//                }
                list.add(sourceAsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
