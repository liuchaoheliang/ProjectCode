//商品属性存储
CREATE TABLE `goods_goods_attribute_map_store` (
  `goods_id` varchar(32) NOT NULL COMMENT '商品ID',
  `element` varchar(255) default NULL COMMENT '属性值',
  `goods_attribute_id` varchar(32) NOT NULL COMMENT '商品属性ID',
  PRIMARY KEY  (`goods_id`,`goods_attribute_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

