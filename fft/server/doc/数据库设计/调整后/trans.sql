//交易
CREATE TABLE `trans` (
  `id` int(20) NOT NULL auto_increment,
  `track_no` varchar(32) not NULL COMMENT '业务跟踪号',
  `bill_no` varchar(32) default NULL COMMENT '账单平台号',
  `trans_sn` varchar(32) not NULL COMMENT '交易编号',
  `trans_type` varchar(32) not NULL COMMENT '交易类型 1000-积分返利 2000-积分兑换 3000-积分提现 4000-团购 5000-收款 ',
  `seller_id` varchar(32) default NULL COMMENT '卖家ID',
  `buyers_id` varchar(32) default NULL COMMENT '买家ID',
  `seller_deposit_channel_id` varchar(32) default NULL COMMENT '卖家收款渠道ID',
  `buyers_deposit_channel_id` varchar(32) default NULL COMMENT '买家支付渠道ID',
  `currency` varchar(32) default NULL COMMENT '货币单位',
  `costprice_total` varchar(32) default NULL COMMENT '原价总和',
  `currency_value_all` varchar(32) default NULL COMMENT '总货币值，所有交易明细累计',
  `currency_value_real_all` varchar(32) default NULL COMMENT '总实际货币数值，由规则计算出,买家实际支付金额',
  `bank_points_account` varchar(32) default NULL COMMENT '银行积分账户',
  `bank_points_value_all` varchar(32) default NULL COMMENT '银行总积分值，所有交易明细累计',
  `bank_points_value_real_all` varchar(32) default NULL COMMENT '银行总实际积分数值，由规则计算出',
  `fft_points_account` varchar(32) default NULL COMMENT '分分通积分账户',
  `fft_points_value_all` varchar(32) default NULL COMMENT '分分通总积分值，所有交易明细累计',
  `fft_points_value_real_all` varchar(32) default NULL COMMENT '分分通总实际积分数值，由规则计算出',
  `failure_time` varchar(32) default NULL COMMENT '失效时间',
  `state` varchar(32) default NULL COMMENT '状态',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

