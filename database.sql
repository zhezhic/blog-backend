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
  `is_public` int(11) DEFAULT 1 COMMENT 'is public',
  `alias` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'blog alias',
  `categories_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'blog categories id',
  `hot` int(11) DEFAULT 0 COMMENT 'hot/热度',
  `comment_count` int(11) DEFAULT 0 COMMENT '评论总数',
  `create_time` datetime NOT NULL COMMENT 'create time',
  `update_time` datetime NOT NULL COMMENT 'update time',
  `deleted` int(11) NOT NULL DEFAULT 0 COMMENT 'logic delete',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='blog table';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `say`
--

DROP TABLE IF EXISTS `say`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `say` (
  `id` bigint(32) NOT NULL COMMENT '说说id',
  `author_id` bigint(32) NOT NULL COMMENT '作者id',
  `content` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '说说内容',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='说说表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-30 13:49:12
