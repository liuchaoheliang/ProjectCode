//交易明细
CREATE TABLE `trans_details` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `trans_id` int(11) not NULL COMMENT '交易ID',
  `goods_rack_id` varchar(32) not NULL COMMENT '交易架商品ID',
  `goods_number` varchar(32) not NULL COMMENT '购买数量',
  `buyers_rule_id` varchar(32) default NULL COMMENT '买家规则ID',
  `buyers_rule_desc` varchar(32) default NULL COMMENT '买家规则描述',
  `seller_rule_id` varchar(32) default NULL COMMENT '卖家规则ID',
  `seller_rule_desc` varchar(32) default NULL COMMENT '卖家规则描述',
  `currency` varchar(32) default NULL COMMENT '货币单位',
  `costprice_total` varchar(32) default NULL COMMENT '原价总和',
  `currency_value` varchar(32) default NULL COMMENT '货币值',
  `currency_value_real` varchar(32) default NULL COMMENT '实际货币值',
  `bank_points_value_all` varchar(32) default NULL COMMENT '银行总积分值，所有交易明细累计',
  `bank_points_value_real_all` varchar(32) default NULL COMMENT '银行总实际积分数值，由规则计算出',
  `fft_points_value_all` varchar(32) default NULL COMMENT '分分通总积分值，所有交易明细累计',
  `fft_points_value_real_all` varchar(32) default NULL COMMENT '分分通总实际积分数值，由规则计算出',
  `state` varchar(32) default NULL COMMENT '状态',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;
