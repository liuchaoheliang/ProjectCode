//买家规则
CREATE TABLE `buyers_rule` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `rule_desc` text COMMENT '规则描述',  
  `rule_type` varchar(32) COMMENT '类型',
  `currency` varchar(32) default NULL COMMENT '货币单位，例如：RMB',
  `currency_formula` varchar(32) default NULL COMMENT '货币规则公式 例如：x=(M*0.85)',
  `points_formula` varchar(32) default NULL COMMENT '积分规则公式 例如：x=(M*0.85)',
  `state` varchar(32) NOT NULL COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) NOT NULL COMMENT '创建时间',
  `update_time` varchar(32) NOT NULL COMMENT '修改时间',
  `remark` varchar(32) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

