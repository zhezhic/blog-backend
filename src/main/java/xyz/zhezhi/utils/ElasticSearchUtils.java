package xyz.zhezhi.utils;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.stereotype.Component;
import xyz.zhezhi.module.entity.Blog;
import xyz.zhezhi.module.entity.User;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
    public static void updateRequestByUser(User user,String index) {

        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.timeField("updateTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(user.getUpdateTime()));
                builder.field("name", user.getName());
                builder.field("email", user.getEmail());
            }
            builder.endObject();
            UpdateRequest request = new UpdateRequest(index, String.valueOf(user.getId()))
                    .doc(builder);
            restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateRequestByBlog(Blog blog, String index) {
        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.timeField("updateTime", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(blog.getUpdateTime()));
                builder.field("alias", blog.getAlias());
                builder.field("title", blog.getTitle());
                builder.field("categoriesId", blog.getCategoriesId());
                builder.field("content", blog.getContent());
                builder.field("context", blog.getContext());
            }
            builder.endObject();
            UpdateRequest request = new UpdateRequest(index, String.valueOf(blog.getId()))
                    .doc(builder);
            restHighLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRequest(String index,String id) {
        DeleteRequest request = new DeleteRequest(index,id);
        request.timeout("10s");

        try {
            restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Map<String,Object>> multiSearchRequest(String content,String index,  int current, int size,
                                                  String... fieldNames) {
        if (current < 0) {
            current = 0;
        }
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //分页
        searchSourceBuilder.from(current);
        searchSourceBuilder.size(size);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);//多个高亮显示
        highlightBuilder.field(fieldNames[0]);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //match 匹配
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.multiMatchQuery(content, fieldNames))
                .must(QueryBuilders.termQuery("isPublic", 1));
        searchSourceBuilder.query(boolQueryBuilder);
        request.source(searchSourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        assert response != null;
        for (SearchHit hit : response.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            HighlightField highlightField = hit.getHighlightFields().get(fieldNames[0]);
            if (highlightField != null) {
                Text[] fragments = highlightField.getFragments();
                StringBuilder stringBuilder = new StringBuilder();
                for (Text fragment : fragments) {
                    stringBuilder.append(fragment);
                }
                sourceAsMap.put("highlight", stringBuilder.toString());
            }
            list.add(sourceAsMap);
        }
        return  list;
    }

    public static List<Map<String, Object>> searchRequest(String keyword,String index, boolean returnAll,int current, int size,String fieldName) {
        if (current < 0) {
            current = 0;
        }
        //条件查询
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(current);
        sourceBuilder.size(size);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 返回字段
        if (!returnAll) {
            String[] includeFields = new String[]{fieldName};
            String[] excludeFields = new String[]{};
            sourceBuilder.fetchSource(includeFields, excludeFields);
        }
        //match 匹配
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery(fieldName, keyword))
                .must(QueryBuilders.termQuery("isPublic", 1));
//        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fieldName, keyword);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);//多个高亮显示
        highlightBuilder.field(fieldName);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析结果
        List<Map<String, Object>> list = new ArrayList<>();
        assert searchResponse != null;
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get(fieldName);
            if (highlightField != null) {
                Text[] texts = highlightField.fragments();
                StringBuilder newHighlightField = new StringBuilder();
                for (Text text : texts) {
                    newHighlightField.append(text);
                }
                sourceAsMap.put("highlight", newHighlightField.toString());//高亮字段替换掉原来的内容即可
            }
            list.add(sourceAsMap);
        }
        return list;
    }


}
