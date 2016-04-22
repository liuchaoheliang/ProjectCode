/**
 * 交易表设计
 */
DROP TABLE IF EXISTS `fft_trans`;
CREATE TABLE `fft_trans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sn` varchar(255) NOT NULL COMMENT '编号',
  `create_source` varchar(255) NOT NULL COMMENT '交易创建来源',
  `type` varchar(255) NOT NULL COMMENT '交易类型',
  `pay_method` varchar(255) NOT NULL COMMENT '支付方式',
  `pay_channel` varchar(255) DEFAULT NULL COMMENT '支付渠道',
  `state` varchar(255) NOT NULL COMMENT '交易状态',
  `member_code` bigint(20) NOT NULL COMMENT '账户服务ID',
  `pay_state` varchar(255) NOT NULL COMMENT '支付状态',
  `total_price` varchar(255) DEFAULT NULL COMMENT '总货币值',
  `real_price` varchar(255) DEFAULT NULL COMMENT '实际总货币数值',
  `fft_points` varchar(255) DEFAULT NULL COMMENT '分分通积分',
  `bank_points` varchar(255) DEFAULT NULL COMMENT '银行积分',
  `points_amount_value` varchar(255) DEFAULT NULL COMMENT '卖家买积分所需总金额(总金额=实际金额+手续费)',
  `points_amount_real_value` varchar(255) DEFAULT NULL COMMENT '卖家买积分所需实际金额',
  `buy_points` varchar(255) DEFAULT NULL COMMENT '购买的积分数',
  `buy_points_factorage` varchar(255) DEFAULT NULL COMMENT '买积分手续费',
  `gathering_value` varchar(255) DEFAULT NULL COMMENT '收款金额',
  `points_withdraw_factorage` varchar(255) DEFAULT NULL COMMENT '积分提现手续费',
  `merchant_id` bigint(20) DEFAULT NULL COMMENT '商户id',
  `reason` varchar(255) DEFAULT NULL COMMENT '事由',
  `film_mobile` varchar(255) DEFAULT NULL COMMENT '付款贴膜卡手机号',
  `phone` varchar(255) DEFAULT NULL COMMENT '用于接收券短信的手机号',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `client_id` bigint(20) NOT NULL COMMENT '客户端编号',
  `delivery_id` bigint(20) DEFAULT NULL COMMENT '提货点id',
  `delivery_name` varchar(255) DEFAULT NULL COMMENT '提货人姓名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sn` (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='交易';

