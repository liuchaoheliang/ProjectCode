//商户与用户对应表
create table merchant_user_set(
  `merchant_id` varchar(32) NOT NULL COMMENT '商户ID',
  `user_id` varchar(36) NOT NULL COMMENT '用户id',
  `is_enabled` varchar(32) DEFAULT '1' COMMENT '是否启用 0-否 1-是',
  PRIMARY KEY  (`merchant_id`,`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;