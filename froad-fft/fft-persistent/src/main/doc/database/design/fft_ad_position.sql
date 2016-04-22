/**
 *广告位表设计
 */
DROP TABLE IF EXISTS `fft_ad_position`;
CREATE TABLE `fft_ad_position` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `width` int(11) DEFAULT '0' COMMENT '宽度',
  `height` int(11) DEFAULT '0' COMMENT '高度',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `position_style` text COMMENT '样式',
  `data_state` int(11) NOT NULL COMMENT '基础数据状态',
  `client_id` bigint(20) NOT NULL COMMENT '客户端编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='广告位';
