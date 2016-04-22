/**
 * 商户用户组表设计
 */
DROP TABLE IF EXISTS `fft_merchant_group_user`;
CREATE TABLE `fft_merchant_group_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户ID',
  `merchant_outlet_id` bigint(20) DEFAULT NULL COMMENT '商户门店ID',
  `sys_user_id` bigint(20) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户用户组';
