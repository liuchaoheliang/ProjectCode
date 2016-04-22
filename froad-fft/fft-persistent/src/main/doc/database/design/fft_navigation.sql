/**
 * 导航表设计
 */
DROP TABLE IF EXISTS `fft_navigation`;
CREATE TABLE `fft_navigation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `position` int(11) NOT NULL COMMENT '位置 0-top 1-middle 2-bottom',
  `url` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `is_visible` bit(1) NOT NULL COMMENT '是否显示',
  `is_blank_target` bit(1) NOT NULL COMMENT '是否在新窗口中打开',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `client_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='导航'
