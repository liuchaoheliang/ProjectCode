/**
 * 预售商品和提货点表设计
 */
DROP TABLE IF EXISTS `fft_product_presell_delivery`;
CREATE TABLE `fft_product_presell_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `product_presell_id` bigint(20) NOT NULL COMMENT '预售商品ID',
  `presell_delivery_id` bigint(20) NOT NULL COMMENT '预售提货点ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='预售商品和提货点';