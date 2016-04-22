-- MySQL dump 10.13  Distrib 5.1.39-ndb-7.0.9, for Win32 (ia32)
--
-- Host: 10.43.2.7    Database: fallow
-- ------------------------------------------------------
-- Server version	5.5.35-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cb_biz_instance_attr`
--

DROP TABLE IF EXISTS `cb_biz_instance_attr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_biz_instance_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `field_name` varchar(32) NOT NULL COMMENT '字段',
  PRIMARY KEY (`id`),
  KEY `history_task_client_id_index` (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务实例属性表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_biz_instance_attr`
--

LOCK TABLES `cb_biz_instance_attr` WRITE;
/*!40000 ALTER TABLE `cb_biz_instance_attr` DISABLE KEYS */;
/*!40000 ALTER TABLE `cb_biz_instance_attr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_biz_instance_attr_value`
--

DROP TABLE IF EXISTS `cb_biz_instance_attr_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_biz_instance_attr_value` (
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `attr_id` varchar(32) NOT NULL COMMENT '属性ID',
  `attr_name` varchar(32) NOT NULL COMMENT '属性名称',
  `attr_value` varchar(32) NOT NULL COMMENT '字段值',
  KEY `biz_instance_attr_value_client_id_index` (`attr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务实例属性值表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_biz_instance_attr_value`
--

LOCK TABLES `cb_biz_instance_attr_value` WRITE;
/*!40000 ALTER TABLE `cb_biz_instance_attr_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `cb_biz_instance_attr_value` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_history_instance`
--

DROP TABLE IF EXISTS `cb_history_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_history_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `instance_state` char(1) NOT NULL COMMENT '实例状态:0-结束,1-活动',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `last_updator` varchar(32) DEFAULT NULL COMMENT '最后一次更新人',
  `order_value` int(10) DEFAULT NULL COMMENT '优化级',
  `org_id` varchar(16) NOT NULL COMMENT '创建审核流水操作人所属的组织',
  PRIMARY KEY (`id`),
  KEY `history_instance_client_id_index` (`client_id`),
  KEY `history_instance_instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_history_instance`
--

LOCK TABLES `cb_history_instance` WRITE;
/*!40000 ALTER TABLE `cb_history_instance` DISABLE KEYS */;
INSERT INTO `cb_history_instance` VALUES (1,'2015-10-29 18:33:55','2015-10-29 18:35:05','chongqing','10E69C130000','100000000','0','100004933','100004921',1,'440203'),(2,'2015-10-30 11:21:01','2015-10-30 11:21:01','chongqing','10F55CB38000','100000000','1','100004933','100004933',1,'440203'),(3,'2015-10-30 15:52:22','2015-10-30 15:52:22','chongqing','10F956440000','100000000','1','100004933','100004933',1,'440203'),(4,'2015-10-30 18:00:34','2015-10-30 18:00:35','chongqing','10FB37008000','100000000','1','100004933','100004933',1,'440203'),(5,'2015-10-30 18:00:42','2015-10-30 18:00:43','chongqing','10FB37808000','100000000','1','100004933','100004933',1,'440203'),(6,'2015-11-02 15:23:24','2015-11-02 15:23:24','chongqing','113831A20000','100000000','1','100004933','100004933',1,'440203'),(7,'2015-11-02 16:32:22','2015-11-02 16:32:22','chongqing','113934420000','100000000','1','100005112',NULL,1,'020110'),(8,'2015-11-02 16:54:37','2015-11-02 16:54:37','chongqing','113987B20000','100000000','1','100005112',NULL,1,'020110'),(9,'2015-11-02 17:05:00','2015-11-02 17:05:00','chongqing','1139AEA28000','100000000','1','100004933',NULL,1,'440203'),(10,'2015-11-02 17:05:45','2015-11-02 17:05:45','chongqing','1139B1728000','100000000','1','100004933',NULL,1,'440203'),(11,'2015-11-02 17:19:39','2015-11-02 17:19:39','chongqing','1139E5928000','100000000','1','100005112','100005112',1,'020110'),(12,'2015-11-02 20:55:38','2015-11-02 20:55:38','chongqing','113D0F810000','100000000','1','100004933',NULL,1,'440203'),(13,'2015-11-02 21:04:30','2015-11-02 21:04:30','chongqing','113D30C10000','100000000','1','100004933',NULL,1,'440203'),(14,'2015-11-02 21:34:50','2015-11-02 21:34:50','chongqing','113DA2818000','100000001','1','100005112','100005112',1,'020110'),(15,'2015-11-03 14:28:14','2015-11-03 14:28:14','chongqing','114C7AC38000','100000001','1','100005112','100005112',1,'020110'),(16,'2015-11-03 15:41:29','2015-11-03 15:41:29','chongqing','114D8D740000','100000002','1','100383846',NULL,1,'100383846'),(17,'2015-11-03 15:44:29','2015-11-03 15:44:29','chongqing','114D98B40000','100000002','1','100383845',NULL,1,'100383845'),(18,'2015-11-03 15:51:13','2015-11-03 15:51:13','chongqing','114DB1F40000','100000000','1','100005112','100005112',1,'020110'),(19,'2015-11-03 16:01:08','2015-11-03 16:01:08','chongqing','114DD7240000','100000002','1','100383845',NULL,1,'100383845'),(20,'2015-11-03 16:12:39','2015-11-03 16:12:39','chongqing','114E02540000','100000000','1','100005112','100005112',1,'020110'),(21,'2015-11-03 16:20:31','2015-11-03 16:20:31','chongqing','114E1FD40000','100000002','1','100383845',NULL,1,'100383845'),(22,'2015-11-03 16:23:22','2015-11-03 16:23:22','chongqing','114E2A840000','100000002','1','100383845',NULL,1,'100383845'),(23,'2015-11-03 16:23:29','2015-11-03 16:23:29','chongqing','114E2AF40000','100000002','1','100383846','100383846',1,'100383846'),(24,'2015-11-03 16:23:42','2015-11-03 16:23:42','chongqing','114E2BC40000','100000002','1','100383846','100383846',1,'100383846'),(25,'2015-11-03 16:23:50','2015-11-03 16:23:50','chongqing','114E2C440000','100000002','1','100383845',NULL,1,'100383845');
/*!40000 ALTER TABLE `cb_history_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_history_task`
--

DROP TABLE IF EXISTS `cb_history_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_history_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `node_id` varchar(32) NOT NULL DEFAULT '' COMMENT '流程节点ID',
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `parent_task_id` varchar(32) DEFAULT NULL COMMENT '父任务ID',
  `name` varchar(100) NOT NULL COMMENT '任务名称',
  `display_name` varchar(255) NOT NULL COMMENT '任务显示名称',
  `perform_type` char(1) NOT NULL COMMENT '参与类型:0-普通,1-参与者会签',
  `task_type` char(1) NOT NULL COMMENT '任务类型:0-主办,1-协办',
  `task_state` char(1) NOT NULL COMMENT '任务状态:0-结束,1-活动',
  `operator` varchar(32) DEFAULT NULL COMMENT '任务处理人',
  `finish_time` datetime DEFAULT NULL COMMENT '任务完成时间',
  `audit_state` char(1) DEFAULT NULL COMMENT '审核状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '审核备注',
  `org_id` varchar(16) NOT NULL COMMENT '审核人所属组织(如果是银行操作员所在orgCode;如果是商户用户操作则传商户id;如果是门店用户操作则传门店id)',
  PRIMARY KEY (`id`),
  KEY `history_task_client_id_index` (`client_id`),
  KEY `history_task_task_id_index` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='历史任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_history_task`
--

LOCK TABLES `cb_history_task` WRITE;
/*!40000 ALTER TABLE `cb_history_task` DISABLE KEYS */;
INSERT INTO `cb_history_task` VALUES (1,'2015-10-29 18:33:55','2015-10-29 18:33:55','chongqing','10E69C130000','100000000','10E69C130000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-10-29 18:33:55',NULL,NULL,'440203'),(2,'2015-10-29 18:33:55','2015-10-29 18:33:55','chongqing','10E69C130000','100000001','10E69C130001','10E69C130000','审核任务','审核任务','0','0','0','100004921','2015-10-29 18:35:05','2','执行审核','440000'),(3,'2015-10-29 18:35:05','2015-10-29 18:35:05','chongqing','10E69C130000','100000002','10E6A0730000','10E69C130001','结束任务','结束任务','0','0','0','100004921','2015-10-29 18:35:05','2','执行审核','440000'),(4,'2015-10-30 11:21:01','2015-10-30 11:21:01','chongqing','10F55CB38000','100000000','10F55CB38000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-10-30 11:21:01',NULL,NULL,'440203'),(5,'2015-10-30 15:52:22','2015-10-30 15:52:22','chongqing','10F956440000','100000000','10F956440000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-10-30 15:52:22',NULL,NULL,'440203'),(6,'2015-10-30 18:00:35','2015-10-30 18:00:35','chongqing','10FB37008000','100000000','10FB37108000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-10-30 18:00:35',NULL,NULL,'440203'),(7,'2015-10-30 18:00:43','2015-10-30 18:00:43','chongqing','10FB37808000','100000000','10FB37908000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-10-30 18:00:43',NULL,NULL,'440203'),(8,'2015-11-02 15:23:24','2015-11-02 15:23:24','chongqing','113831A20000','100000000','113831A20000',NULL,'开始任务','开始任务','0','0','1','100004933','2015-11-02 15:23:24',NULL,NULL,'440203'),(9,'2015-11-02 17:19:39','2015-11-02 17:19:39','chongqing','1139E5928000','100000000','1139E5928000',NULL,'开始任务','开始任务','0','0','1','100005112','2015-11-02 17:19:39',NULL,NULL,'020110'),(10,'2015-11-02 21:34:50','2015-11-02 21:34:50','chongqing','113DA2818000','100000003','113DA2818000',NULL,'开始任务','开始任务','0','0','1','100005112','2015-11-02 21:34:50',NULL,NULL,'020110'),(11,'2015-11-03 14:28:14','2015-11-03 14:28:14','chongqing','114C7AC38000','100000003','114C7AC38000',NULL,'开始任务','开始任务','0','0','1','100005112','2015-11-03 14:28:14',NULL,NULL,'020110'),(12,'2015-11-03 15:51:13','2015-11-03 15:51:13','chongqing','114DB1F40000','100000000','114DB1F40000',NULL,'开始任务','开始任务','0','0','1','100005112','2015-11-03 15:51:13',NULL,NULL,'020110'),(13,'2015-11-03 16:12:39','2015-11-03 16:12:39','chongqing','114E02540000','100000000','114E02540000',NULL,'开始任务','开始任务','0','0','1','100005112','2015-11-03 16:12:39',NULL,NULL,'020110'),(14,'2015-11-03 16:23:29','2015-11-03 16:23:29','chongqing','114E2AF40000','100000006','114E2AF40000',NULL,'开始任务','开始任务','0','0','1','100383846','2015-11-03 16:23:29',NULL,NULL,'100383846'),(15,'2015-11-03 16:23:42','2015-11-03 16:23:42','chongqing','114E2BC40000','100000006','114E2BC40000',NULL,'开始任务','开始任务','0','0','1','100383846','2015-11-03 16:23:42',NULL,NULL,'100383846');
/*!40000 ALTER TABLE `cb_history_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_history_task_actor`
--

DROP TABLE IF EXISTS `cb_history_task_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_history_task_actor` (
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `actor_id` varchar(32) DEFAULT NULL COMMENT '参与者ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史任务参与者表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_history_task_actor`
--

LOCK TABLES `cb_history_task_actor` WRITE;
/*!40000 ALTER TABLE `cb_history_task_actor` DISABLE KEYS */;
INSERT INTO `cb_history_task_actor` VALUES ('10E69C130000','100004933'),('10E69C130001','100004921'),('10F55CB38000','100004933'),('10F956440000','100004933'),('10FB37108000','100004933'),('10FB37908000','100004933'),('113831A20000','100004933'),('1139E5928000','100005112'),('113DA2818000','100005112'),('114C7AC38000','100005112'),('114DB1F40000','100005112'),('114E02540000','100005112'),('114E2AF40000','100383846'),('114E2BC40000','100383846');
/*!40000 ALTER TABLE `cb_history_task_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_instance`
--

DROP TABLE IF EXISTS `cb_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `expire_time` datetime DEFAULT NULL COMMENT '期望完成时间',
  `last_updator` varchar(32) DEFAULT NULL COMMENT '最后一次更新人',
  `order_value` int(10) DEFAULT NULL COMMENT '优化级',
  `version` int(3) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `instance_client_id_index` (`client_id`),
  KEY `instance_instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_instance`
--

LOCK TABLES `cb_instance` WRITE;
/*!40000 ALTER TABLE `cb_instance` DISABLE KEYS */;
INSERT INTO `cb_instance` VALUES (2,'2015-10-30 11:21:01','2015-10-30 11:21:01','chongqing','10F55CB38000','100000000','100004933',NULL,NULL,1,NULL),(3,'2015-10-30 15:52:22','2015-10-30 15:52:22','chongqing','10F956440000','100000000','100004933',NULL,NULL,1,NULL),(4,'2015-10-30 18:00:34','2015-10-30 18:00:34','chongqing','10FB37008000','100000000','100004933',NULL,NULL,1,NULL),(5,'2015-10-30 18:00:42','2015-10-30 18:00:42','chongqing','10FB37808000','100000000','100004933',NULL,NULL,1,NULL),(6,'2015-11-02 15:23:24','2015-11-02 15:23:24','chongqing','113831A20000','100000000','100004933',NULL,NULL,1,NULL),(8,'2015-11-02 16:32:22','2015-11-02 16:32:22','chongqing','113934420000','100000000','100005112',NULL,NULL,1,NULL),(9,'2015-11-02 16:54:37','2015-11-02 16:54:37','chongqing','113987B20000','100000000','100005112',NULL,NULL,1,NULL),(10,'2015-11-02 17:05:00','2015-11-02 17:05:00','chongqing','1139AEA28000','100000000','100004933',NULL,NULL,1,NULL),(11,'2015-11-02 17:05:45','2015-11-02 17:05:45','chongqing','1139B1728000','100000000','100004933',NULL,NULL,1,NULL),(12,'2015-11-02 17:19:39','2015-11-02 17:19:39','chongqing','1139E5928000','100000000','100005112',NULL,NULL,1,NULL),(13,'2015-11-02 20:55:38','2015-11-02 20:55:38','chongqing','113D0F810000','100000000','100004933',NULL,NULL,1,NULL),(14,'2015-11-02 21:04:30','2015-11-02 21:04:30','chongqing','113D30C10000','100000000','100004933',NULL,NULL,1,NULL),(15,'2015-11-02 21:34:50','2015-11-02 21:34:50','chongqing','113DA2818000','100000001','100005112',NULL,NULL,1,NULL),(16,'2015-11-03 14:28:14','2015-11-03 14:28:14','chongqing','114C7AC38000','100000001','100005112',NULL,NULL,1,NULL),(17,'2015-11-03 15:41:29','2015-11-03 15:41:29','chongqing','114D8D740000','100000002','100383846',NULL,NULL,1,NULL),(18,'2015-11-03 15:44:29','2015-11-03 15:44:29','chongqing','114D98B40000','100000002','100383845',NULL,NULL,1,NULL),(19,'2015-11-03 15:51:13','2015-11-03 15:51:13','chongqing','114DB1F40000','100000000','100005112',NULL,NULL,1,NULL),(20,'2015-11-03 16:01:08','2015-11-03 16:01:08','chongqing','114DD7240000','100000002','100383845',NULL,NULL,1,NULL),(21,'2015-11-03 16:12:39','2015-11-03 16:12:39','chongqing','114E02540000','100000000','100005112',NULL,NULL,1,NULL),(22,'2015-11-03 16:20:31','2015-11-03 16:20:31','chongqing','114E1FD40000','100000002','100383845',NULL,NULL,1,NULL),(23,'2015-11-03 16:23:22','2015-11-03 16:23:22','chongqing','114E2A840000','100000002','100383845',NULL,NULL,1,NULL),(24,'2015-11-03 16:23:29','2015-11-03 16:23:29','chongqing','114E2AF40000','100000002','100383846',NULL,NULL,1,NULL),(25,'2015-11-03 16:23:42','2015-11-03 16:23:42','chongqing','114E2BC40000','100000002','100383846',NULL,NULL,1,NULL),(26,'2015-11-03 16:23:50','2015-11-03 16:23:50','chongqing','114E2C440000','100000002','100383845',NULL,NULL,1,NULL);
/*!40000 ALTER TABLE `cb_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_process`
--

DROP TABLE IF EXISTS `cb_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `parent_process_id` varchar(32) DEFAULT NULL COMMENT '父流程ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `display_name` varchar(255) DEFAULT NULL COMMENT '显示名称',
  `type` char(1) DEFAULT NULL COMMENT '流程类型:1-商户,2-门店,3-团购商品,4-预售商品',
  `type_detail` char(1) DEFAULT NULL COMMENT '类型详情:0-新增,1-更新',
  `status` char(1) NOT NULL COMMENT '状态0-禁用1-启用',
  `version` int(3) NOT NULL COMMENT '版本号',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `process_client_id_index` (`client_id`),
  KEY `process_process_id_index` (`process_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000006 DEFAULT CHARSET=utf8 COMMENT='流程表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_process`
--

LOCK TABLES `cb_process` WRITE;
/*!40000 ALTER TABLE `cb_process` DISABLE KEYS */;
INSERT INTO `cb_process` VALUES (100000000,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000',NULL,'merchant_add','商户新增审核','1','0','1',1,NULL),(100000001,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001',NULL,'merchant_upd','商户更新审核','1','1','1',1,NULL),(100000002,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002',NULL,'outlet_add','门店新增审核','2','0','1',1,NULL),(100000003,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003',NULL,'outlet_upd','门店更新审核','2','1','1',1,NULL),(100000004,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004',NULL,'group_product_add','团购商品新增审核','3','0','1',1,NULL),(100000005,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005',NULL,'presell_product_add','预售商品新增审核','4','0','1',1,NULL);
/*!40000 ALTER TABLE `cb_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_process_node`
--

DROP TABLE IF EXISTS `cb_process_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_process_node` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `node_id` varchar(32) NOT NULL COMMENT '流程节点ID',
  `name` varchar(32) DEFAULT NULL COMMENT '节点名称',
  `pre_node_id` varchar(32) DEFAULT NULL COMMENT '前序节点ID',
  `next_node_id` varchar(32) DEFAULT NULL COMMENT '后续节点ID',
  `type` char(1) NOT NULL COMMENT '节点类型:0-开始节点,1-结束节点,2-任务节点',
  `node_logic` char(1) DEFAULT NULL COMMENT '节点逻辑',
  `runner_flag` char(1) DEFAULT NULL COMMENT '执行类型:0-用户,1-岗位,2-部门,3-用户组,4-机构级别',
  `next_runner_org` char(1) DEFAULT NULL COMMENT '后续执行机构:0-自身机构,1-发展机构',
  `status` char(1) NOT NULL COMMENT '状态:0-禁用1-启用',
  `runner_user_id` bigint(20) DEFAULT NULL COMMENT '执行用户ID',
  `runner_post_id` bigint(20) DEFAULT NULL COMMENT '执行岗位ID',
  `runner_depart_id` bigint(20) DEFAULT NULL COMMENT '执行部门ID',
  `runner_usergroup_id` bigint(20) DEFAULT NULL COMMENT '执行用户组ID',
  `runner_org_level` char(1) DEFAULT NULL COMMENT '执行机构等级',
  `is_multiselect` bit(1) NOT NULL COMMENT '是否允许多选',
  `is_other_man` bit(1) NOT NULL COMMENT '是否允许转发其它人',
  `is_assign_man` bit(1) NOT NULL COMMENT '是否允许由代理人处理',
  `is_revoke` bit(1) NOT NULL COMMENT '是否允许撤销',
  `is_fallback` bit(1) NOT NULL COMMENT '是否允许回退',
  PRIMARY KEY (`id`),
  KEY `process_node_client_id_index` (`client_id`),
  KEY `process_node_node_id_index` (`node_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000018 DEFAULT CHARSET=utf8 COMMENT='流程节点表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_process_node`
--

LOCK TABLES `cb_process_node` WRITE;
/*!40000 ALTER TABLE `cb_process_node` DISABLE KEYS */;
INSERT INTO `cb_process_node` VALUES (100000000,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000000','网点提交',NULL,'100000001','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000001,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000001','支行审核','100000000','100000002','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2','\0','\0','\0','\0','\0'),(100000002,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000002','结束','100000001',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000003,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000003','网点提交',NULL,'100000004','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000004,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000004','支行审核','100000003','100000005','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2','\0','\0','\0','\0','\0'),(100000005,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000005','结束','100000004',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000006,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000006','商户录入',NULL,'100000007','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000007,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000007','网点审核','100000006','100000008','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000008,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000008','结束','100000007',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000009,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000009','商户录入',NULL,'100000010','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000010,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000010','网点审核','100000009','100000011','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000011,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000011','结束','100000010',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000012,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000012','商户录入',NULL,'100000013','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000013,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000013','网点审核','100000012','100000014','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000014,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000014','结束','100000013',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0'),(100000015,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000015','网点提交',NULL,'100000016','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3','\0','\0','\0','\0','\0'),(100000016,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000016','支行审核','100000015','100000017','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2','\0','\0','\0','\0','\0'),(100000017,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000017','结束','100000016',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,'\0','\0','\0','\0','\0');
/*!40000 ALTER TABLE `cb_process_node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_task`
--

DROP TABLE IF EXISTS `cb_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `node_id` varchar(32) NOT NULL COMMENT '流程节点ID',
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `parent_task_id` varchar(32) DEFAULT NULL COMMENT '父任务ID',
  `name` varchar(100) NOT NULL COMMENT '任务名称',
  `display_name` varchar(255) NOT NULL COMMENT '任务显示名称',
  `perform_type` char(1) NOT NULL COMMENT '参与类型:0-普通,1-参与者会签',
  `task_type` char(1) NOT NULL COMMENT '任务类型:0-主办,1-协办',
  `operator` varchar(32) DEFAULT NULL COMMENT '任务处理人',
  `finish_time` datetime DEFAULT NULL COMMENT '任务完成时间',
  `org_id` varchar(16) NOT NULL COMMENT '机构代码',
  PRIMARY KEY (`id`),
  KEY `task_client_id_index` (`client_id`),
  KEY `task_task_id_index` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_task`
--

LOCK TABLES `cb_task` WRITE;
/*!40000 ALTER TABLE `cb_task` DISABLE KEYS */;
INSERT INTO `cb_task` VALUES (2,'2015-10-30 11:21:01','2015-10-30 11:21:01','chongqing','10F55CB38000','100000001','10F55CB38001','10F55CB38000','审核任务','审核任务','0','0',NULL,NULL,'440000'),(3,'2015-10-30 15:52:22','2015-10-30 15:52:22','chongqing','10F956440000','100000001','10F956440001','10F956440000','审核任务','审核任务','0','0',NULL,NULL,'440000'),(4,'2015-10-30 18:00:35','2015-10-30 18:00:35','chongqing','10FB37008000','100000001','10FB37108001','10FB37108000','审核任务','审核任务','0','0',NULL,NULL,'440000'),(5,'2015-10-30 18:00:43','2015-10-30 18:00:43','chongqing','10FB37808000','100000001','10FB37908001','10FB37908000','审核任务','审核任务','0','0',NULL,NULL,'440000'),(6,'2015-11-02 15:23:24','2015-11-02 15:23:24','chongqing','113831A20000','100000001','113831A20001','113831A20000','审核任务','审核任务','0','0',NULL,NULL,'440000'),(7,'2015-11-02 17:19:39','2015-11-02 17:19:39','chongqing','1139E5928000','100000001','1139E5928001','1139E5928000','审核任务','审核任务','0','0',NULL,NULL,'020000'),(8,'2015-11-02 21:34:50','2015-11-02 21:34:50','chongqing','113DA2818000','100000004','113DA2818001','113DA2818000','审核任务','审核任务','0','0',NULL,NULL,'020000'),(9,'2015-11-03 14:28:14','2015-11-03 14:28:14','chongqing','114C7AC38000','100000004','114C7AC38001','114C7AC38000','审核任务','审核任务','0','0',NULL,NULL,'020000'),(10,'2015-11-03 15:51:13','2015-11-03 15:51:13','chongqing','114DB1F40000','100000001','114DB1F40001','114DB1F40000','审核任务','审核任务','0','0',NULL,NULL,'020000'),(11,'2015-11-03 16:12:39','2015-11-03 16:12:39','chongqing','114E02540000','100000001','114E02540001','114E02540000','审核任务','审核任务','0','0',NULL,NULL,'020000'),(12,'2015-11-03 16:23:29','2015-11-03 16:23:29','chongqing','114E2AF40000','100000007','114E2AF40001','114E2AF40000','审核任务','审核任务','0','0',NULL,NULL,''),(13,'2015-11-03 16:23:42','2015-11-03 16:23:42','chongqing','114E2BC40000','100000007','114E2BC40001','114E2BC40000','审核任务','审核任务','0','0',NULL,NULL,'');
/*!40000 ALTER TABLE `cb_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cb_task_actor`
--

DROP TABLE IF EXISTS `cb_task_actor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cb_task_actor` (
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `actor_id` varchar(32) DEFAULT NULL COMMENT '参与者ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参与者表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cb_task_actor`
--

LOCK TABLES `cb_task_actor` WRITE;
/*!40000 ALTER TABLE `cb_task_actor` DISABLE KEYS */;
/*!40000 ALTER TABLE `cb_task_actor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'fallow'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-04 15:22:16
