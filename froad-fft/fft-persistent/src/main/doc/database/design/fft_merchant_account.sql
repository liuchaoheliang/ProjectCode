/**
 * 商户账户表
 */
DROP TABLE IF EXISTS `fft_merchant_account`;
CREATE TABLE `fft_merchant_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户Id',
  `acct_name` varchar(255) NOT NULL COMMENT '账户名称',
  `acct_number` varchar(255) NOT NULL COMMENT '账户号',
  `acct_type` varchar(255) NOT NULL COMMENT '账户类型',
  `is_enabled` bit(1) NOT NULL COMMENT '是否启用',
  `funds_channel_id` bigint(20) DEFAULT NULL COMMENT '资金渠道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户账户表';
