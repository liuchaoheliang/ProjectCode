
CREATE TABLE `cb_biz_instance_attr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `field_name` varchar(32) NOT NULL COMMENT '字段',
  PRIMARY KEY (`id`),
  KEY `history_task_client_id_index` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='业务实例属性表';

CREATE TABLE `cb_biz_instance_attr_value` (
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `attr_id` varchar(32) NOT NULL COMMENT '属性ID',
  `attr_name` varchar(32) NOT NULL COMMENT '属性名称',
  `attr_value` varchar(32) NOT NULL COMMENT '字段值',
  KEY `biz_instance_attr_value_client_id_index` (`attr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务实例属性值表';

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
  PRIMARY KEY (`id`),
  KEY `history_instance_client_id_index` (`client_id`),
  KEY `history_instance_instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='实例表';

CREATE TABLE `cb_history_task` (
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
  `tsak_state` char(1) NOT NULL COMMENT '任务状态:0-结束,1-活动',
  `operator` varchar(32) DEFAULT NULL COMMENT '任务处理人',
  `finish_time` datetime DEFAULT NULL COMMENT '任务完成时间',
  PRIMARY KEY (`id`),
  KEY `history_task_client_id_index` (`client_id`),
  KEY `history_task_task_id_index` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='任务表';

CREATE TABLE `cb_history_task_actor` (
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `actor_id` varchar(32) DEFAULT NULL COMMENT '参与者ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史任务参与者表';


CREATE TABLE `cb_instance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `instance_id` varchar(32) NOT NULL COMMENT '实例ID',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建人',
  `expire_time` datetime NOT NULL COMMENT '期望完成时间',
  `last_updator` varchar(32) DEFAULT NULL COMMENT '最后一次更新人',
  `order_value` int(10) DEFAULT NULL COMMENT '优化级',
  `version` int(3) DEFAULT NULL COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `instance_client_id_index` (`client_id`),
  KEY `instance_instance_id_index` (`instance_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='实例表';


CREATE TABLE `cb_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` varchar(32) NOT NULL COMMENT '客户端',
  `process_id` varchar(32) NOT NULL COMMENT '流程ID',
  `parent_process_id` varchar(32) DEFAULT NULL COMMENT '父流程ID',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `display_name` varchar(255) DEFAULT NULL COMMENT '显示名称',
  `type` char(1) DEFAULT NULL COMMENT '流程类型:0-商户,1-门店,2-团购商品',
  `type_detail` char(1) DEFAULT NULL COMMENT '类型详情:0-新增,1-更新',
  `status` char(1) NOT NULL COMMENT '状态0-禁用1-启用',
  `version` int(3) NOT NULL COMMENT '版本号',
  `creator` varchar(32) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`id`),
  KEY `process_client_id_index` (`client_id`),
  KEY `process_process_id_index` (`process_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='流程表';


INSERT INTO `cb_process` VALUES (100000000,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000',NULL,'merchant_add','商户新增审核','0','0','1',1,NULL),(100000001,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001',NULL,'merchant_upd','商户更新审核','0','1','1',1,NULL),(100000002,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002',NULL,'outlet_add','门店新增审核','1','0','1',1,NULL),(100000003,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003',NULL,'outlet_upd','门店更新审核','1','1','1',1,NULL),(100000004,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004',NULL,'group_product_add','团购商品新增审核','2','0','1',1,NULL);


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
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='流程节点表';


INSERT INTO `cb_process_node` (`id`,`create_time`,`update_time`,`client_id`,`process_id`,`node_id`,`name`,`pre_node_id`,`next_node_id`,`type`,`node_logic`,`runner_flag`,`next_runner_org`,`status`,`runner_user_id`,`runner_post_id`,`runner_depart_id`,`runner_usergroup_id`,`runner_org_level`,`is_multiselect`,`is_other_man`,`is_assign_man`,`is_revoke`,`is_fallback`) VALUES (100000000,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000000','网点提交',NULL,'100000001','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000001,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000001','支行审核','100000000','100000002','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2',b'0',b'0',b'0',b'0',b'0'),(100000002,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000000','100000002','结束','100000001',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000003,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000003','网点提交',NULL,'100000004','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000004,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000004','支行审核','100000003','100000005','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2',b'0',b'0',b'0',b'0',b'0'),(100000005,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000001','100000005','结束','100000004',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000006,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000006','商户录入',NULL,'100000007','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000007,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000007','网点审核','100000006','100000008','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000008,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000002','100000008','结束','100000007',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000009,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000009','商户录入',NULL,'100000010','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000010,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000010','网点审核','100000009','100000011','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000011,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000003','100000011','结束','100000010',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000012,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000012','商户录入',NULL,'100000013','0',NULL,NULL,'1','1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000013,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000013','网点审核','100000012','100000014','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000014,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000004','100000014','结束','100000013',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0'),(100000015,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000015','网点提交',NULL,'100000016','0',NULL,'4','0','1',NULL,NULL,NULL,NULL,'3',b'0',b'0',b'0',b'0',b'0'),(100000016,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000016','支行审核','100000015','100000017','2',NULL,'4',NULL,'1',NULL,NULL,NULL,NULL,'2',b'0',b'0',b'0',b'0',b'0'),(100000017,'2015-10-12 13:41:19','2015-10-12 13:41:19','chongqing','100000005','100000017','结束','100000016',NULL,'1',NULL,NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,b'0',b'0',b'0',b'0',b'0');


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
  PRIMARY KEY (`id`),
  KEY `task_client_id_index` (`client_id`),
  KEY `task_task_id_index` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='任务表';


CREATE TABLE `cb_task_actor` (
  `task_id` varchar(32) NOT NULL COMMENT '任务ID',
  `actor_id` varchar(32) DEFAULT NULL COMMENT '参与者ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务参与者表';


