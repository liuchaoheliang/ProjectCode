/**
 * 现金和积分的比例表设计
 */
DROP TABLE IF EXISTS `fft_cash_points_ratio`;
CREATE TABLE `fft_cash_points_ratio` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `fft_points` varchar(255) DEFAULT '1' COMMENT '现金和分分通积分的比例(如果 1元=0.5积分,该值为0.5;1元=3积分,该值为3)',
  `bank_points` varchar(255) DEFAULT '1' COMMENT '现金和银行积分的比例',
  `sys_client_id` bigint(20) NOT NULL COMMENT '客户端',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_client_id` (`sys_client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='现金和积分的比例';
