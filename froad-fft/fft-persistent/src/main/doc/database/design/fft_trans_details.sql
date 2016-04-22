/**
 * 交易详情表设计
 */
DROP TABLE IF EXISTS `fft_trans_details`;
CREATE TABLE `fft_trans_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `trans_id` bigint(20) NOT NULL COMMENT '交易ID',
  `product_id` bigint(20) NOT NULL COMMENT '商品ID',
  `product_name` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL COMMENT '购买数量',
  `price` varchar(255) NOT NULL COMMENT '金额',
  `single` varchar(255) NOT NULL COMMENT '单价',
  `supply_merchant_id` bigint(20) NOT NULL COMMENT '供应商商户ID',
  `gather_merchant_id` bigint(20) NOT NULL COMMENT '收款方商户Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='交易详情';

