//交易上架商品属性存储
create table trans_goods_attriute(
  `trans_id` varchar(32) NOT NULL COMMENT '交易ID',
  `element` varchar(255) default NULL COMMENT '属性值',
  `goods_rack_attriute_id` varchar(32) NOT NULL COMMENT '商品属性ID',
  PRIMARY KEY  (`trans_id`,`goods_rack_attriute_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;