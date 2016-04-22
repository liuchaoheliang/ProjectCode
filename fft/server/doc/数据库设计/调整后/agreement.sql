
CREATE TABLE `agreement` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `type` varchar(32) NOT NULL COMMENT '协议类型 0-注册协议',
  `content` text NOT NULL COMMENT '协议内容',
  `state` varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

