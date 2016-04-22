/**
 * 角色表设计
 */
DROP TABLE IF EXISTS `fft_sys_role`;
CREATE TABLE `fft_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  `value` varchar(255) DEFAULT NULL COMMENT '角色标识',
  `is_system` bit(1) NOT NULL COMMENT '是否为系统内置角色',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='角色';

