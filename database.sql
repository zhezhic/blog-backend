-- MariaDB dump 10.19  Distrib 10.6.4-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: blog
-- ------------------------------------------------------
-- Server version	10.6.4-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog` (
  `id` bigint(32) NOT NULL COMMENT 'blog id',
  `author_id` bigint(32) NOT NULL COMMENT 'author id',
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'blog title',
  `content` text COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'blog content',
  `context` varchar(300) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'context/上下文简略',
  `is_public` int(11) DEFAULT 0 COMMENT 'is public',
  `categories_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'blog categories id',
  `hot` int(11) DEFAULT NULL COMMENT 'hot/热度',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NOT NULL COMMENT 'update time',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT 'logic delete',
  `alias` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'blog alias',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='blog table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1441626441571962881,1428556050647941121,'javafx-图形化编程','# 1.环境说明\n1. jdk:openjdk 16 [下载地址](http://jdk.java.net/java-se-ri/16)\n2. 开发工具IDEA\n3. 项目构建工具: maven 3.6.3 [下载地址](https://maven.apache.org/download.cgi)\n4. javafx: 16\n# 2.pom文件\n```xml\n<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n    <modelVersion>4.0.0</modelVersion>\n\n    <groupId>xyz.zhezhi</groupId>\n    <artifactId>javafxDemo</artifactId>\n    <version>1.0-SNAPSHOT</version>\n\n    <properties>\n        <maven.compiler.source>16</maven.compiler.source>\n        <maven.compiler.target>16</maven.compiler.target>\n        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n        <javafx.version>16</javafx.version>\n        <javafx.maven.plugin.version>0.0.6</javafx.maven.plugin.version>\n<!--        <exec.mainClass>xyz.zhezhi.HelloFX</exec.mainClass>-->\n    </properties>\n    <dependencies>\n        <dependency>\n            <groupId>org.openjfx</groupId>\n            <artifactId>javafx-controls</artifactId>\n            <version>${javafx.version}</version>\n        </dependency>\n        <dependency>\n            <groupId>org.openjfx</groupId>\n            <artifactId>javafx-fxml</artifactId>\n            <version>${javafx.version}</version>\n        </dependency>\n    </dependencies>\n    <build>\n        <plugins>\n            <plugin>\n                <groupId>org.openjfx</groupId>\n                <artifactId>javafx-maven-plugin</artifactId>\n                <version>${javafx.maven.plugin.version}</version>\n                <configuration>\n                    <mainClass>xyz.zhezhi.HelloFX</mainClass>\n                </configuration>\n            </plugin>\n        </plugins>\n    </build>\n</project>\n```\n# 3.项目结构\n![Snipaste_20210505_154335.png](http://119.29.110.184/upload/2021/05/Snipaste_2021-05-05_15-43-35-c1042cf1367b4152939658b98b7f246b.png)\n# 4.启动类\n```java\npackage xyz.zhezhi;\n\nimport javafx.application.Application;\nimport javafx.scene.Scene;\nimport javafx.scene.control.Label;\nimport javafx.scene.layout.StackPane;\nimport javafx.stage.Stage;\n\n/**\n * 主启动类,继承javafx.application.Application是JavaFX的开始\n *\n * @Author zhezhi\n * @Date 2021/5/5 14:49\n * @Version 1.0\n **/\npublic class HelloFX extends Application {\n    /**\n     * Stage：就是你能看到的整个软件界面（窗口）\n     * Scene：就是除了窗口最上面有最大、最小化及关闭按钮那一行及窗口边框外其它的区域（场景）\n     * 场景（Scene）是一个窗口（Stage）必不可少的\n     */\n    @Override\n    public void start(Stage stage) throws Exception {\n        String javaVersion = System.getProperty(\"java.version\");\n        String javafxVersion = System.getProperty(\"javafx.version\");\n        // 创建一个标签，用于存放我们的Hello World文本，并设置让它在父容器中居中\n        Label label = new Label(\"hello,java\" + javaVersion + \"javafx\" + javafxVersion);\n        /**\n         * 三步曲\n         */\n        // 1、初始化一个场景\n        Scene scene = new Scene(new StackPane(label), 640, 480);\n\n        // 2、将场景放入窗口\n        stage.setScene(scene);\n\n        // 3、打开窗口\n        stage.show();\n    }\n\n    public static void main(String[] args) {\n        // 启动软件\n        launch();\n    }\n}\n```\n# 5.重点\n高版本jdk 错误: 缺少 JavaFX 运行时组件, 需要使用该组件来运行此应用程序\n1. 解决方法一：module-info.java\n创建module-info.java\n```java\nmodule xyz.zhezhi {\n    requires javafx.controls;\n    requires javafx.fxml;\n    // 暴露包 xyz.zhezhi 给 javafx 的模块们，使其可以在运行时使用反射访问\n    opens xyz.zhezhi to javafx.graphics, javafx.fxml;\n}\n```\n2. 解决方法二：新建一个启动类(不推荐，项目杂乱）\n```java\npackage xyz.zhezhi;\n\nimport javafx.application.Application;\n/**\n * \n *\n * @Author zhezhi\n * @Date 2021/5/5 14:49\n * @Version 1.0\n **/\npublic class Main {\n    public static void main(String[] args) {\n        // 启动软件\n        Application.launch(HelloFX.class,args);\n    }\n}\n参考 [原因分析](https://my.oschina.net/tridays/blog/2222909)\n```','# 1.环境说明\n1. jdk:openjdk 16 [下载地址](http://jdk.java.net/java-se-ri/16)\n2. 开发工具IDEA\n3. 项目构建工具: maven 3.',0,'[\"1439831249953091585\",\"1439838104062345217\"]',NULL,'2021-09-25 12:51:42','2021-09-25 12:51:42',0,'javafx-1'),(1441630064284610562,1428556050647941121,'Java统一返回数据格式','## R类\n```java\npackage xyz.zhezhi.vo;\n\n\nimport lombok.Data;\n\nimport java.util.HashMap;\nimport java.util.Map;\n\n@Data\npublic class R {\n\n    //是否成功\n    private Boolean success;\n\n    //返回码\n    private Integer code;\n\n    //返回消息\n    private String message;\n\n    //返回数据\n    private Map<String, Object> data = new HashMap<String, Object>();\n\n    private R(){}\n\n    public static R ok(){\n        R r = new R();\n        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());\n        r.setCode(ResultCodeEnum.SUCCESS.getCode());\n        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());\n        return r;\n    }\n\n    public static R error(){\n        R r = new R();\n        r.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());\n        r.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());\n        r.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());\n        return r;\n    }\n\n    public R success(Boolean success){\n        this.setSuccess(success);\n        return this;\n    }\n\n    public R message(String message){\n        this.setMessage(message);\n        return this;\n    }\n\n    public R code(Integer code){\n        this.setCode(code);\n        return this;\n    }\n\n    public R data(String key, Object value){\n        this.data.put(key, value);\n        return this;\n    }\n\n    public R data(Map<String, Object> map){\n        this.setData(map);\n        return this;\n    }\n}\n\n```\n## 结果码枚举类\n```java\npackage xyz.zhezhi.vo;\n\nimport lombok.Getter;\n\n/**\n * TODO\n *\n * @Author zhezhi\n * @Date 2021/5/2 23:05\n * @Version 1.0\n **/\n@Getter\npublic enum ResultCodeEnum {\n    SUCCESS(true,20000,\"成功\"),\n    UNKNOWN_REASON(false,20001,\"未知错误\"),\n    BAD_SQL_GRAMMAR(false,21002,\"sql语法错误\"),\n    PARAM_ERROR(false,21003,\"参数不正确\");\n    private Boolean success;\n    private Integer code;\n    private String message;\n\n    ResultCodeEnum(Boolean success, Integer code, String message) {\n        this.success = success;\n        this.code = code;\n        this.message = message;\n    }\n}\n\n```\n','## R类\n```java\npackage xyz.zhezhi.vo;\n\n\nimport lombok.Data;\n\nimport java.util.HashMap;\nimport java.ut',0,'[\"1439831249953091585\"]',NULL,'2021-09-25 13:06:06','2021-09-25 13:06:06',0,'java-return'),(1442028054035841025,1428556050647941121,'dddd','ddd','ddd',0,'[\"1439831249953091585\",\"1439838104062345217\",\"1439838180360929282\",\"1439838230570942466\",\"1440987015397367810\",\"1439831295159300098\",\"1439838308517888002\",\"1439838494690459649\"]',NULL,'2021-09-26 15:27:34','2021-09-26 15:27:34',0,'test'),(1442376711468134401,1428556050647941121,'ddddd','<script>alert(\"hello\")</script>','<script>alert(\"hello\")</script>',0,'[]',NULL,'2021-09-27 14:33:00','2021-09-27 14:33:00',0,'');
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(32) NOT NULL COMMENT '分类id',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '分类名称',
  `author_id` bigint(32) NOT NULL COMMENT '作者id',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '父分类',
  `rank` tinyint(4) DEFAULT 0 COMMENT '分类顺序',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT 'logic delete',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NOT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1439831249953091585,'java',1428556050647941121,0,0,0,'2021-09-20 13:58:15','2021-09-20 13:58:15'),(1439831295159300098,'c',1428556050647941121,0,0,0,'2021-09-20 13:58:26','2021-09-20 13:58:26'),(1439838104062345217,'javafx',1428556050647941121,1439831249953091585,0,0,'2021-09-20 14:25:29','2021-09-20 14:25:29'),(1439838180360929282,'window',1428556050647941121,1439838104062345217,0,0,'2021-09-20 14:25:47','2021-09-20 14:25:47'),(1439838230570942466,'panel',1428556050647941121,1439838180360929282,0,0,'2021-09-20 14:25:59','2021-09-20 14:25:59'),(1439838308517888002,'nginx',1428556050647941121,1439831295159300098,0,0,'2021-09-20 14:26:18','2021-09-20 14:26:18'),(1439838494690459649,'G',1428556050647941121,0,0,0,'2021-09-20 14:27:02','2021-09-20 14:27:02'),(1439838526164516865,'gg',1428556050647941121,1439838494690459649,0,0,'2021-09-20 14:27:10','2021-09-20 14:27:10'),(1440593976013561857,'电脑',1428556050647941121,0,0,0,'2021-09-22 16:29:03','2021-09-22 16:29:03'),(1440987015397367810,'mybatis',1428556050647941121,1439831249953091585,0,0,'2021-09-23 18:30:51','2021-09-23 18:30:51');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(32) NOT NULL COMMENT '评论id',
  `content` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论',
  `blog_id` bigint(32) NOT NULL COMMENT '博客id',
  `parent_id` bigint(32) DEFAULT NULL COMMENT '父分类',
  `author_id` bigint(32) NOT NULL COMMENT '作者id',
  `rank` tinyint(4) DEFAULT 0 COMMENT '分类顺序',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT 'logic delete',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NOT NULL COMMENT 'update time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(32) NOT NULL COMMENT '用户id',
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
  `password` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `email` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `intro` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '个人介绍',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT 'mybatis逻辑删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1428556050647941121,'zhezhi','3103ae6fe92b5588a762bb24455fb8d9','2544438809@qq.com','http://127.0.0.1:8087/user/getAvatar/1428556050647941121.jpeg','hello!word',0,'2021-08-20 11:14:38','2021-08-20 11:14:38'),(1434738957386801153,'hhh','3103ae6fe92b5588a762bb24455fb8d9','25@qq.com','http://127.0.0.1:8087/user/getAvatar/1434738957386801153.png',NULL,0,'2021-09-06 12:43:18','2021-09-06 12:43:18');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-28 19:26:15
