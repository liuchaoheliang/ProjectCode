//基础商品
CREATE TABLE `goods` (
  `id` int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',
  `merchant_id` varchar(32) NOT NULL COMMENT '商户ID',
  `goods_sn` varchar(32) NOT NULL COMMENT '商品编号',
  `goods_name` varchar(255) default NULL COMMENT '名称',
  `price` varchar(32) default '0' COMMENT '商品价格',
  `market_price` varchar(32) default '0' COMMENT '市场价格',
  `weight` varchar(32) default '0' COMMENT '商品重量',
  `weight_unit` varchar(32) default NULL COMMENT '重量单位 （g、kg、t）',
  `store` varchar(32) default '0' COMMENT '商品库存数量',
  `freeze_store` varchar(32) default '0' COMMENT '冻结库存数量',
  `warn_number` varchar(32) default '0' COMMENT '警告数量',
  `is_best` varchar(32) default '0' COMMENT '是否精品 0-否 1-是',
  `is_new` varchar(32) default '0' COMMENT '是否新品 0-否 1-是',
  `is_hot` varchar(32) default '0' COMMENT '是否热销 0-否 1-是',
  `keywords` varchar(255) default NULL COMMENT '关键字',
  `goods_desc` varchar(1024) default NULL COMMENT '描述',
  `source_url` varchar(255) default NULL COMMENT '商品图片原路径',
  `big_url` varchar(255) default NULL COMMENT '商品图片大路径',
  `small_url` varchar(255) default NULL COMMENT '商品图片小路径',
  `thumbnail_url` varchar(255) default NULL COMMENT '商品图片缩略图路径',
  `goods_category_id` varchar(32) default NULL COMMENT '商品分类ID',
  `brand_id` varchar(32) default NULL COMMENT '品牌ID',
  `goods_type` varchar(32) default NULL COMMENT '类型ID',
  `state` varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  `create_time` varchar(32) default NULL COMMENT '创建时间',
  `update_time` varchar(32) default NULL COMMENT '更新时间',
  `remark` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

#
# Dumping data for table goods
#
LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;

/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
