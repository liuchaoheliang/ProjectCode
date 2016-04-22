/**
 * 积分返利的送分规则
 */
DROP TABLE IF EXISTS `fft_present_point_rule`;
CREATE TABLE `fft_present_point_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '规则名称',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户Id',
  `rule` varchar(255) NOT NULL COMMENT '送分规则',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `description` varchar(255) DEFAULT NULL COMMENT '规则描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='积分返利的送分规则';
