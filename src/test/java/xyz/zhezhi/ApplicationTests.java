package xyz.zhezhi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.zhezhi.common.ElasticSearchIndex;
import xyz.zhezhi.mapper.BlogMapper;
import xyz.zhezhi.mapper.CategoryMapper;
import xyz.zhezhi.mapper.UserMapper;
import xyz.zhezhi.module.entity.*;
import xyz.zhezhi.module.vo.CategoryVO;
import xyz.zhezhi.service.BlogService;
import xyz.zhezhi.service.SayService;
import xyz.zhezhi.utils.ElasticSearchUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootTest
class ApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogService blogService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SayService sayService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Test
    void contextLoads() {
        User user = new User();
        user.setName("zhezhi");
        user.setPassword("0852159632");
        user.setEmail("25444@qq.com");
        int insert = userMapper.insert(user);
        System.out.println(insert);
        System.out.println(user);
    }

    @Test
    void query() {
        User user = new User();
        user.setEmail("2544438809@qq.com");
        user.setName("zhezhi");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(User::getEmail, user.getEmail())
                .or()
                .eq(User::getName, user.getName())
        ;
        List<User> users = userMapper.selectList(wrapper);

    }

    @Test
    void login() {
        User user = new User();
        user.setEmail("22@q.com");
        user.setPassword("664d568834a8e92e6aafa5ab7843d634");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .eq("email", user.getEmail())
        ;
        User u = userMapper.selectOne(wrapper);
        if (user.getPassword().equals(u.getPassword())) {
            System.err.println("登陆成功");
        }
        System.err.println("-------");
    }



    @Test
    void category() {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        wrapper.eq("author_id", 1428556050647941121L);
        List<Category> originCategories = categoryMapper.selectList(wrapper);
        List<CategoryVO> categories = new ArrayList<>();
        for (Category originCategory : originCategories) {
            categories.add(new CategoryVO(originCategory));
        }
        List<CategoryVO> finalCategories = categories;
        categories = categories
                .stream()
                .filter(e -> e.getParentId().equals(0L))
                .map(e -> {
                    System.out.println("map=>" + e);
                    e.setChildren(getChildren(e, finalCategories));
                    return e;
                }).collect(Collectors.toList());
        categories.forEach(System.err::println);
    }

    private List<CategoryVO> getChildren(CategoryVO root, List<CategoryVO> categoryVOList) {
        List<CategoryVO> finalCategories = categoryVOList;
        List<CategoryVO> childrenList = categoryVOList
                .stream()
                .filter(e -> e.getParentId().equals(root.getParentId()))
                .map(e -> {
                            System.out.println("map=>" + e);
                            e.setChildren(getChildren(e, finalCategories));
                            return e;
                        }
                ).collect(Collectors.toList());
        return childrenList;
    }

    @Test
    public void randomBackground() throws Exception {
        String suffix = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=2&n=1";
        String prefix = "https://api.btstu.cn/sjbz/?lx=dongman";
        HttpURLConnection connection = (HttpURLConnection) new URL(suffix).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(60000);
        connection.connect();
        if (connection.getResponseCode() == 200) {
           BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String images = JSONObject.parseObject(br.readLine()).getString("images");
            JSONArray array = JSONObject.parseArray(images);
            String s = array.get(0).toString();
            String url = JSONObject.parseObject(s).getString("url");
            System.out.println(url);
        }
    }

    @Test
    public void blogPage() {
        IPage<Blog> blogPage = new Page<>(2, 2);
        blogPage = blogMapper.selectPage(blogPage, null);
        List<Blog> records = blogPage.getRecords();
        for (Blog record : records) {
            System.out.println(record);
        }
    }
    @Test
    public void elasticsearch() throws IOException {
//        if(!restHighLevelClient.indices().exists(new GetIndexRequest("zz_blog"), RequestOptions.DEFAULT)){
//            restHighLevelClient.indices().create(new CreateIndexRequest("zz_blog"), RequestOptions.DEFAULT);
            Blog blog = new Blog();
            blog.setId(111112211111L);
            blog.setAuthorId(4234235532L);
            blog.setContent("ldkhflsakdjfshdvjsfhsfjakskjdhfaskjd");
            blog.setTitle("java extend");
            ElasticSearchUtils.IndexRequest(blog, ElasticSearchIndex.BLOG.getIndex(), blog.getId().toString());
//        System.out.println(ElasticSearchIndex.BLOG);
//        }else {
//            System.out.println("已存在");
//        }
//        CreateIndexRequest request = new CreateIndexRequest("zz_index");
//        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
//        System.out.println(response);
//        DeleteIndexRequest request = new DeleteIndexRequest("te");
//        restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    public void matchSearch()  {
        int current = 0;
        int size = 5;
        String index = ElasticSearchIndex.BLOG.getIndex();
        String properties = "title";
        String keyword = "中国";
        //条件查询
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        sourceBuilder.from(current);
        sourceBuilder.size(size);
        //精准匹配
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(properties, keyword);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false);//多个高亮显示
        highlightBuilder.field(properties);
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
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        try {
            for (SearchHit documentFields : searchResponse.getHits().getHits()) {
                Map<String, HighlightField> fields = documentFields.getHighlightFields();
                HighlightField title = fields.get(properties);
                Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//原来的结果
                //解析高亮的字段
                if(title!=null){
                    Text[] texts = title.fragments();
                    StringBuilder newTitle = new StringBuilder();
                    for (Text text : texts) {
                        newTitle.append(text);
                    }
                    sourceAsMap.put("title", newTitle.toString());//高亮字段替换掉原来的内容即可
                }
                list.add(sourceAsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Map<String, Object> map : list) {
            System.err.println(map);
        }
    }

    @Test
    public void multiSearch() throws IOException {
        String index = ElasticSearchIndex.BLOG.getIndex();
        String property1 = "title";
        String property2 = "content";
        String content = "java pixiv";
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(content, property1, property2));
        request.source(searchSourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHit[] hits = response.getHits().getHits();
        ArrayList<String> blogIdList = new ArrayList<>();
        for (SearchHit hit : hits) {
            blogIdList.add(hit.getId());
        }
        System.out.println(blogIdList);
    }

    @Test
    public void addSay() {
        Say say = new Say();
        say.setContent("hello");
        say.setAuthorId(1452969799829508097L);
        int i = sayService.addSay(say);
    }
}
