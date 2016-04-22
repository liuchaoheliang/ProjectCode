/**
 * 资源表设计
 */
DROP TABLE IF EXISTS `fft_sys_resource`;
CREATE TABLE `fft_sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '资源名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '资源图标',
  `code` varchar(255) NOT NULL COMMENT '资源编码',
  `url` varchar(255) DEFAULT NULL COMMENT '资源URL',
  `type` int(11) NOT NULL COMMENT '类型',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级资源ID',
  `tree_path` varchar(255) NOT NULL COMMENT '树路径',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `is_system` bit(1) NOT NULL COMMENT '是否为系统资源',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `client_id` bigint(20) NOT NULL COMMENT '客户端ID',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='资源';

