/**
 * 商品限购表设计
 */
DROP TABLE IF EXISTS `fft_product_restrict_rule`;
CREATE TABLE `fft_product_restrict_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `restrict_type` varchar(255) NOT NULL COMMENT '限购类型',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `quantity` int(11) DEFAULT 1 NOT NULL COMMENT '限购数量默认 1 ',
  `remark` varchar(255) COMMENT '备注说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商品限购表'
