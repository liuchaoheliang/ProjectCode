/**
 * 团购商品表设计
 */
DROP TABLE IF EXISTS `fft_product_group`;
CREATE TABLE `fft_product_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `title` varchar(255) NOT NULL COMMENT '团购标题',
  `summary` text COMMENT '团购商品简介',
  `area_name` varchar(255) NOT NULL COMMENT '地区名称',
  `per_number` int(11) NOT NULL COMMENT '每人限购',
  `permin_number` int(11) NOT NULL COMMENT '最低购买',
  `start_time` datetime NOT NULL COMMENT '有效期-开始 ',
  `end_time` datetime NOT NULL COMMENT '有效期-结束',
  `buy_know` text NOT NULL COMMENT '购买需知',
  `generalize_image` varchar(255) NOT NULL COMMENT '展示图片',
  `details_image` varchar(255) NOT NULL,
  `description` text NOT NULL COMMENT '描述',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商品';
