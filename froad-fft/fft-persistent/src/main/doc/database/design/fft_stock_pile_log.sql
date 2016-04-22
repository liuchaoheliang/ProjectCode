/**
 * 库存日志表设计
 */
DROP TABLE IF EXISTS `fft_stock_pile_log`;
CREATE TABLE `fft_stock_pile_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `type` varchar(255) NOT NULL COMMENT '类型',
  `product_id` bigint(20) NOT NULL COMMENT '商品',
  `merchant_outlet_id` bigint(20) NOT NULL COMMENT '门店Id',
  `quantity` int(11) NOT NULL COMMENT '商品数量',
  `content` text COMMENT '内容',
  `operator` varchar(255) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='库存日志表';
