//买家
CREATE TABLE `buyers` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `user_id` varchar(36) NOT NULL COMMENT '关联会员 user',
  `state` varchar(32) NOT NULL COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间',
  `update_time` varchar(32) NOT NULL COMMENT '修改时间',
  `remark` varchar(32) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

