/**
 * 标签表设计
 */
DROP TABLE IF EXISTS `fft_tag`;
CREATE TABLE `fft_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '标题',
  `type` int(11) NOT NULL COMMENT '类型 0-merchant',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `description` varchar(255) DEFAULT NULL COMMENT '备注',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `data_state` int(11) NOT NULL COMMENT '基础数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='标签';

