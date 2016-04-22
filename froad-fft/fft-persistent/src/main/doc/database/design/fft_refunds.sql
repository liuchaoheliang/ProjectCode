/**
 * 退款表
 */
DROP TABLE IF EXISTS `fft_refunds`;
CREATE TABLE `fft_refunds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sn` varchar(255) NOT NULL COMMENT '编号',
  `trans_id` bigint(20) NOT NULL COMMENT '交易Id',
  `state` varchar(255) NOT NULL COMMENT '状态',
  `reason` varchar(255) NOT NULL COMMENT '退款事由',
  `sys_user_id` varchar(255) NOT NULL COMMENT '审核员',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='退款表';