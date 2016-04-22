//商品属性
CREATE TABLE `goods_attribute` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `attribute_type` varchar(255) NOT NULL COMMENT '属性类型',
  `is_required` varchar(32) default '0' COMMENT '是否必填(0-否 1-是)',
  'goods_type_id' varchar(32) not null comment '商品类型ID',
  `state` varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

