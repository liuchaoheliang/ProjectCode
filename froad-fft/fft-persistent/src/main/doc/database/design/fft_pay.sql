/**
 * 支付表设计
 */
DROP TABLE IF EXISTS `fft_pay`;
CREATE TABLE `fft_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sn` varchar(255) NOT NULL COMMENT '编号',
  `trans_id` bigint(20) NOT NULL COMMENT '交易ID',
  `pay_type` varchar(255) NOT NULL COMMENT '支付类型详情',
  `pay_type_details` varchar(255) NOT NULL COMMENT '支付类型',
  `step` int(11) NOT NULL COMMENT '支付步骤',
  `pay_state` varchar(255) NOT NULL COMMENT '支付状态',
  `pay_org` varchar(255) DEFAULT NULL COMMENT '支付机构号',
  `bill_no` varchar(255) DEFAULT NULL COMMENT 'openapi平台号',
  `refund_order_id` varchar(255) DEFAULT NULL COMMENT 'openapi退款订单号',
  `point_bill_no` varchar(255) DEFAULT NULL COMMENT '积分平台号',
  `refund_point_no` varchar(255) DEFAULT NULL COMMENT '积分平台退分号',
  `pay_value` varchar(255) NOT NULL COMMENT '支付值',
  `from_account_name` varchar(255) DEFAULT NULL COMMENT '从哪个账户名',
  `from_account_no` varchar(255) DEFAULT NULL COMMENT '从哪个账户号',
  `to_account_name` varchar(255) DEFAULT NULL COMMENT '到哪个账户名',
  `to_account_no` varchar(255) DEFAULT NULL COMMENT '到哪个账户号',
  `from_phone` varchar(255) DEFAULT NULL COMMENT '从哪个手机银行',
  `to_phone` varchar(255) DEFAULT NULL COMMENT '到哪个手机银行',
  `from_role` varchar(255) DEFAULT NULL COMMENT '从哪个角色',
  `to_role` varchar(255) DEFAULT NULL COMMENT '到哪个角色',
  `from_user_name` varchar(255) DEFAULT NULL COMMENT '从哪个用户',
  `to_user_name` varchar(255) DEFAULT NULL COMMENT '到哪个用户',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '收款方商户ID',
  `result_code` varchar(255) DEFAULT NULL COMMENT '结果码',
  `result_desc` varchar(255) DEFAULT NULL COMMENT '结果码描述',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='支付';

