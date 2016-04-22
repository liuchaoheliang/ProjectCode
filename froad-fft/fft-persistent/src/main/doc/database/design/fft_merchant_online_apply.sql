/**
 *商户在线申请表设计
 */
DROP TABLE IF EXISTS `fft_merchant_online_apply`;
CREATE TABLE `fft_merchant_online_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `company_name` varchar(255) NOT NULL COMMENT '公司名称',
  `linkman` varchar(255) NOT NULL COMMENT '联系人',
  `mobile` varchar(255) NOT NULL COMMENT '手机',
  `phone` varchar(255) DEFAULT NULL COMMENT '座机',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `area_id` bigint(20) NOT NULL COMMENT '所在地区',
  `cooperation_type` int(11) NOT NULL COMMENT '意向合作模式 ',
  `cooperative_purpose` varchar(255) DEFAULT NULL COMMENT '合作意向',
  `is_relation` bit(1) NOT NULL COMMENT '是否已联系',
  `relation_account` bigint(20) DEFAULT NULL COMMENT '联系处理人',
  `relation_time` datetime DEFAULT NULL COMMENT '联系处理时间',
  `relation_mark` varchar(255) DEFAULT NULL COMMENT '联系处理备注',
  `client_id` bigint(20) NOT NULL COMMENT '所属客户端',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户在线申请';

