/**
 * 积分提现规则
 */
DROP TABLE IF EXISTS `fft_with_draw_points_rule`;
CREATE TABLE `fft_with_draw_points_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '规则名称',
  `fft_fee_rule` varchar(255) NOT NULL COMMENT '方付通手续费规则',
  `bank_fee_rule` varchar(255) NOT NULL COMMENT '银行手续费规则',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `description` varchar(255) DEFAULT NULL COMMENT '规则描述',
  `client_id` bigint(20) NOT NULL COMMENT '客户端编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='积分提现规则';
