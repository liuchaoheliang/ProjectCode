/**
 * 预售提货点表设计
 */
DROP TABLE IF EXISTS `fft_presell_delivery`;
CREATE TABLE `fft_presell_delivery` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `telephone` varchar(255) NOT NULL COMMENT '电话',
  `business_time` varchar(255) NOT NULL COMMENT '营业时间',
  `coordinate` varchar(255) NOT NULL COMMENT '坐标',
  `business_circle_id` bigint(20) NOT NULL COMMENT '所属商圈',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `data_state` int(11) NOT NULL COMMENT '基础数据状态',
  `client_id` bigint(20) NOT NULL COMMENT '客户端编号',
  `director` varchar(255) NOT NULL COMMENT '负责人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='预售提货点';
