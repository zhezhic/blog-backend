package xyz.zhezhi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zhezhi.module.entity.Upload;

import java.io.File;
import java.util.ArrayList;

/**
  * @author zhezhi
  * @className: Initialization
  * @description: 初始化项目运行环境
  * @date 2021/8/17 下午9:08
  * @version：1.0
  */
@Slf4j
public class Initialization{
    @Autowired
    Upload upload;
    public void init() {
        ArrayList<String> paths = new ArrayList<>();
        paths.add(upload.getAvatar());
        paths.add(upload.getBlogImage());
        for (String path : paths) {
            // 构建上传文件的存放 "文件夹" 路径
            File fileDir = new File(path);
            if(!fileDir.exists()){
                // 递归生成文件夹
                if (!fileDir.mkdirs()) {
                    log.error("初始化创建文件夹失败"+path);
                }
            }
            log.info("初始化文件夹成功:"+path);
        }

    }
}
