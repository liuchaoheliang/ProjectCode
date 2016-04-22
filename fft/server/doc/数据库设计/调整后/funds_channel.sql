﻿//资金渠道
CREATE TABLE `funds_channel` (
  `fft_poundage_account_number` varchar(32) default NULL COMMENT '',
  `fft_poundage_account_name` varchar(32) default NULL COMMENT '手续费',
  `fft_allowance_account_number` varchar(32) default NULL COMMENT '',
  `fft_allowance_account_name` varchar(32) default NULL COMMENT '补贴账户',
  `fft_points_account_number` varchar(32) default NULL COMMENT '',
  `fft_points_account_name` varchar(32) default NULL COMMENT '积分资金账户',
  `fft_gathering_account_number` varchar(32) default NULL COMMENT '方付通收款帐号',
  `fft_gathering_account_name` varchar(32) default NULL COMMENT '方付通收款帐户',
  `bank_gathering_account_number` varchar(32) default NULL COMMENT '银行收款帐号',
  `bank_gathering_account_name` varchar(32) default NULL COMMENT '银行收款帐户',
  `currency` varchar(32) default NULL COMMENT '货币单位',
  `channel_type` varchar(32) default NULL COMMENT '渠道类型 10-贴膜卡渠道',
  `channel_short_name` varchar(32) default NULL COMMENT '资金机构短名',
  `channel_full_name` varchar(32) default NULL COMMENT '资金机构全名',
  `pay_org` varchar(32) default NULL COMMENT '资金机构代号',
  `funds_channel_no` varchar(32) default NULL COMMENT '资金机构编号',
  `remark` varchar(32) default NULL COMMENT '备注',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `state` varchar(32) default NULL COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `id` int(32) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;
