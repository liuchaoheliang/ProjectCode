/**
 * 商户相册表设计
 */
DROP TABLE IF EXISTS `fft_merchant_photo`;
CREATE TABLE `fft_merchant_photo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `url` varchar(255) NOT NULL COMMENT 'URL地址',
  `width` int(11) DEFAULT NULL COMMENT '宽度',
  `height` int(11) DEFAULT NULL COMMENT '高度',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `merchant_outlet_id` bigint(20) NOT NULL COMMENT '门店ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户相册';
