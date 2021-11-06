package xyz.zhezhi.utils;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import xyz.zhezhi.common.ElasticSearchIndex;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author zhezhi
 * @className: Initialization
 * @description: 初始化项目运行环境
 * @date 2021/8/17 下午9:08
 * @version：1.0
 */
@Slf4j
public class Initialization {
    @Value("${my-config.file.upload.avatar}")
    private String avatar;
    @Value("${my-config.file.upload.blog-image}")
    private String blogImage;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    public void init() throws IOException {
        ArrayList<String> paths = new ArrayList<>();
        paths.add(avatar);
        paths.add(blogImage);
        for (String path : paths) {
            // 构建上传文件的存放 "文件夹" 路径
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                // 递归生成文件夹
                if (!fileDir.mkdirs()) {
                    log.error("初始化创建文件夹失败" + path);
                }
            }
            log.info("初始化文件夹成功:" + path);
        }
        GetIndexRequest getIndexRequest = new GetIndexRequest(ElasticSearchIndex.BLOG.getIndex());
        boolean blogIndex = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        getIndexRequest = new GetIndexRequest(ElasticSearchIndex.USER.getIndex());
        boolean userIndex = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!blogIndex) {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchIndex.BLOG.getIndex());
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {
                    builder.startObject("authorId");
                    {
                        builder.field("type", "long");
                    }
                    builder.endObject();

                    builder.startObject("title");
                    {
                        builder.field("type", "text");
                        builder.field("analyzer", "ik_max_word");
                        builder.field("search_analyzer", "ik_smart");
                    }
                    builder.endObject();

                    builder.startObject("content");
                    {
                        builder.field("type", "text");
                        builder.field("analyzer", "ik_max_word");
                        builder.field("search_analyzer", "ik_smart");
                    }
                    builder.endObject();

                    builder.startObject("createTime");
                    {
                        builder.field("type", "date");
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();

                    builder.startObject("updateTime");
                    {
                        builder.field("type", "date");
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();

                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping(builder);
            restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        }
        if (!userIndex) {
            CreateIndexRequest request = new CreateIndexRequest(ElasticSearchIndex.USER.getIndex());
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.startObject("properties");
                {

                    builder.startObject("name");
                    {
                        builder.field("type", "text");
                        builder.field("analyzer", "ik_max_word");
                        builder.field("search_analyzer", "ik_max_word");
                    }
                    builder.endObject();

                    builder.startObject("createTime");
                    {
                        builder.field("type", "date");
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();

                    builder.startObject("updateTime");
                    {
                        builder.field("type", "date");
                        builder.field("format", "yyyy-MM-dd HH:mm:ss");
                    }
                    builder.endObject();

                }
                builder.endObject();
            }
            builder.endObject();
            request.mapping(builder);
            restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        }
    }
}
