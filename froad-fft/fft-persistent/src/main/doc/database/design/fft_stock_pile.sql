/**
 * 库存表设计
 */
DROP TABLE IF EXISTS `fft_stock_pile`;
CREATE TABLE `fft_stock_pile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `product_id` bigint(20) NOT NULL COMMENT '商品Id',
  `merchant_outlet_id` bigint(20) NOT NULL COMMENT '门店Id',
  `quantity` int(11) NOT NULL COMMENT '分店实际库存数量',
  `last_income_time` datetime NOT NULL COMMENT '最后一次入库时间',
  `last_outcome_time` datetime DEFAULT NULL COMMENT '最后一次出库时间',
  `warn_type` varchar(255) NOT NULL COMMENT '警告类型',
  `warn_value` varchar(255) NOT NULL COMMENT '警告值',
  `total_quantity` int(11) NOT NULL COMMENT '最大库存量',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='库存';