/**
 * 门店和提货点关联表设计
 */
DROP TABLE IF EXISTS `fft_outlet_presell_delivery`;
CREATE TABLE `fft_outlet_presell_delivery` (
  `merchant_outlet_id` bigint(20) NOT NULL COMMENT '商户门店ID',
  `presell_delivery_id` bigint(20) NOT NULL COMMENT '预售提货点ID'
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='门店和提货点关联表';
