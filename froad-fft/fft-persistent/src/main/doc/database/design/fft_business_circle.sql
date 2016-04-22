/**
 * 商圈表设计
 */
DROP TABLE IF EXISTS `fft_business_circle`;
CREATE TABLE `fft_business_circle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '上级资源ID',
  `tree_path` varchar(255) NOT NULL COMMENT '树路径',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `area_id` bigint(20) NOT NULL COMMENT '所属地区',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商圈';