﻿//商品提现架
CREATE TABLE `goods_carry_rack` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `is_rack` varchar(32) default '0' COMMENT '是否上架 0-否 1-是',
  `rack_time` varchar(32) default NULL COMMENT '上架时间',
  `inspectors` varchar(32) default NULL COMMENT '审核人',
  `market_total_number` varchar(32) default '0' COMMENT '销售数量',
  `is_cash` varchar(32) default '0' COMMENT '是否启用现金 0-否 1-是',
  `is_fft_point` varchar(32) default '0' COMMENT '是否启用分分通积分 0-否 1-是',
  `is_bank_point` varchar(32) default '0' COMMENT '是否启用银行积分 0-否 1-是',
  `is_fftpoint_cash` varchar(32) default '0' COMMENT '是否启用分分通积分+现金 0-否 1-是',
  `is_bankpoint_cash` varchar(32) default '0' COMMENT '是否启用银行积分+现金 0-否 1-是',
  `is_fftpointcash_ratio_pricing` varchar(32) default '0' COMMENT '是否启用分分通积分+现金比例定价 0-否 1-是',
  `is_bankpointcash_ratio_pricing` varchar(32) default '0' COMMENT '是否启用银行积分+现金比例定价 0-否 1-是',
  `cash_pricing` varchar(32) default NULL COMMENT '现金定价',
  `fft_point_pricing` varchar(32) default NULL COMMENT '分分通积分定价',
  `bank_point_pricing` varchar(32) default NULL COMMENT '银行积分定价',
  `fftpoint_cash_pricing` varchar(32) default NULL COMMENT '分分通积分和现金定价',
  `bankpoint_cash_pricing` varchar(32) default NULL COMMENT '银行积分和现金定价',
  `fftpointcash_ratio_pricing` varchar(32) default NULL COMMENT '分分通+现金比例定价',
  `bankpointcash_ratio_pricing` varchar(32) default NULL COMMENT '银行+现金比例定价',
  `state` varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

