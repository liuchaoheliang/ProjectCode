/**
 * 客户端信息表设计
 */
DROP TABLE IF EXISTS `fft_sys_client`;
CREATE TABLE `fft_sys_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '资金机构名称',
  `number` varchar(255) NOT NULL COMMENT '客户端编号',
  `short_letter` varchar(255) NOT NULL COMMENT '字母简称 如（重庆|CQ）',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `area` varchar(255) DEFAULT NULL COMMENT '客户端所属地',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `org_no` varchar(255) NOT NULL,
  `partner_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `number` (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='客户端信息';

