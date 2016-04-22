//支付
CREATE TABLE `pay` (
  `id` int(20) NOT NULL auto_increment,
  `track_no` varchar(32) not null COMMENT '业务跟踪号',
  `trans_id` varchar(32) not null COMMENT '交易ID',
  `type` varchar(32) not null COMMENT '支付类型 1000-资金 2000-积分',
  `from_account_name` varchar(32) default NULL COMMENT '支付人姓名',
  `from_account_no` varchar(32) default NULL COMMENT '支付号',
  `pay_value` varchar(32) default NULL COMMENT '支付值',
  `to_account_name` varchar(32) default NULL COMMENT '收款人姓名',
  `to_account_no` varchar(32) default NULL COMMENT '收款人号',
  `state` varchar(32) default NULL COMMENT '支付状态 00-支付成功 10-支付创建 20-支付失败 ',
  `step` varchar(32) default NULL COMMENT '步骤 1 2 3',
  `result_code` varchar(32) default NULL COMMENT '结果码',
  `result_desc` varchar(32) default NULL COMMENT '结果码描述',
  `create_time` varchar(32) default NULL COMMENT '',
  `update_time` varchar(32) default NULL COMMENT '',
  `remark` varchar(255) default NULL COMMENT '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1001000000000001 DEFAULT CHARSET=utf8;

