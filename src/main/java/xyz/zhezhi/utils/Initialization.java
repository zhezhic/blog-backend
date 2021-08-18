package xyz.zhezhi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.zhezhi.module.entity.Upload;

import java.io.File;

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
        final String IMG_PATH_PREFIX = upload.getAvatar();
        // 构建上传文件的存放 "文件夹" 路径
        File fileDir = new File(IMG_PATH_PREFIX);
        if(!fileDir.exists()){
            // 递归生成文件夹
            if (!fileDir.mkdirs()) {
                log.error("初始化创建文件夹失败");
            }
        }
        log.info("初始化文件夹成功:"+IMG_PATH_PREFIX);
    }
}
