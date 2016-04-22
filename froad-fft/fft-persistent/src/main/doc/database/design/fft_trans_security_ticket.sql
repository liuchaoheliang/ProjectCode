/**
 * 验证券表设计
 */
DROP TABLE IF EXISTS `fft_trans_security_ticket`;
CREATE TABLE `fft_trans_security_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `trans_id` bigint(20) NOT NULL COMMENT '关联交易id',
  `member_code` bigint(20) NOT NULL COMMENT '账户账务id',
  `trans_type` varchar(255) NOT NULL COMMENT '交易类型',
  `securities_no` varchar(255) NOT NULL COMMENT '券号',
  `is_consume` bit(1) DEFAULT b'0' COMMENT '是否消费',
  `consume_time` datetime DEFAULT NULL COMMENT '消费时间',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `sms_number` int(11) DEFAULT '1' COMMENT '已发送的短信次数',
  `sms_time` datetime DEFAULT NULL COMMENT '最近一次短信发送时间',
  `sys_user_id` bigint(20) NOT NULL COMMENT '认证券的操作人',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `securities_no` (`securities_no`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='验证券表';

