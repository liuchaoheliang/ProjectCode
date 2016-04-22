//卖家资金渠道
CREATE TABLE `seller_channel` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `user_id` varchar(36) NOT NULL COMMENT '关联会员 user',
  `merchant_id` varchar(32) NOT NULL COMMENT '关联FBU_Merchant',
  `seller_id` varchar(32) NOT NULL COMMENT '卖家ID 关联seller',
  `seller_rule_id` varchar(32) NOT NULL COMMENT '卖家规则ID 关联卖家规则SellerRule',
  `channel_id` varchar(32) NOT NULL COMMENT '资金渠道ID 关联FundsChannel',
  `is_default` varchar(32) NOT NULL COMMENT '是否是默认',
  `account_name` varchar(32) default NULL COMMENT '账户名',
  `account_number` varchar(32) NOT NULL COMMENT '账户号',
  `state` varchar(32) NOT NULL COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间',
  `update_time` varchar(32) NOT NULL COMMENT '修改时间',
  `remark` varchar(32) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `seller_rule_id` (`seller_rule_id`),
  UNIQUE KEY `channel_id` (`channel_id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;
