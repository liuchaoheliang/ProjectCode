/**
 * 商品属性值表设计
 */
DROP TABLE IF EXISTS `fft_product_attribute_value`;
CREATE TABLE `fft_product_attribute_value` (
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `product_attribute_id` bigint(20) NOT NULL COMMENT '商品属性ID',
  `value` varchar(255) NOT NULL COMMENT '属性值'
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商品属性值';

