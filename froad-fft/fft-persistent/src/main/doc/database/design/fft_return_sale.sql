/**
 * 退/换表
 */
DROP TABLE IF EXISTS `fft_return_sale`;
CREATE TABLE `fft_return_sale` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sn` varchar(255) NOT NULL COMMENT '编号',
  `type` varchar(255) NOT NULL COMMENT '类型',
  `merchant_outlet_id` bigint(20) DEFAULT NULL COMMENT '执行退换货操作的门店（若为空则表示超级管理员操作）',
  `reason` varchar(255) NOT NULL COMMENT '退货事由',
  `sys_user_id` bigint(20) NOT NULL COMMENT '操作员Id',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='退/换表';
