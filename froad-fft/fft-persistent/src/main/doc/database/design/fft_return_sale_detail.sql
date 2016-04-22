/**
 * 退/换明细表
 */
DROP TABLE IF EXISTS `fft_return_sale_detail`;
CREATE TABLE `fft_return_sale_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `return_sale_id` bigint(20) NOT NULL COMMENT '退货Id',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) NOT NULL COMMENT '数量',
  `securities_no` varchar(255) NOT NULL COMMENT '券号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='退/换明细表';
