/**
 * 预售商品表设计
 */
DROP TABLE IF EXISTS `fft_product_presell`;
CREATE TABLE `fft_product_presell` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `title` varchar(255) NOT NULL COMMENT '团购标题',
  `summary` text COMMENT '团购商品简介',
  `per_number` int(11) NOT NULL COMMENT '每人限购',
  `permin_number` int(11) NOT NULL COMMENT '最低购买',
  `start_time` datetime NOT NULL COMMENT '有效期-开始 ',
  `end_time` datetime NOT NULL COMMENT '有效期-结束',
  `delivery_start_time` datetime NOT NULL COMMENT '提货-开始 ',
  `delivery_end_time` datetime NOT NULL COMMENT '提货-结束',
  `clustering_number` int(11) NOT NULL COMMENT '成功成团数量',
  `true_buyer_number` int(11) DEFAULT '0' COMMENT '实际购买商品数量',
  `virtual_buyer_number` int(11) DEFAULT '0' COMMENT '系统添加的虚拟购买商品数量',
  `cluster_state` varchar(255) NOT NULL COMMENT '是否成功成团',
  `cluster_type` int(11) NOT NULL COMMENT '成团类型 ',
  `buy_know` text NOT NULL COMMENT '购买需知',
  `generalize_image` varchar(255) NOT NULL COMMENT '展示图片',
  `details_image` varchar(255) NOT NULL,
  `description` text NOT NULL COMMENT '描述',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='预售商品';