/**
 * 商户门店表设计
 */
DROP TABLE IF EXISTS `fft_merchant_outlet`;
CREATE TABLE `fft_merchant_outlet` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `full_name` varchar(255) NOT NULL COMMENT '全名',
  `logo` varchar(255) DEFAULT NULL COMMENT '公司LOGO',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `business_hours` varchar(255) NOT NULL COMMENT '营业时间',
  `zip` varchar(255) DEFAULT NULL COMMENT '邮编',
  `fax` varchar(255) DEFAULT NULL COMMENT '传真',
  `tel` varchar(255) NOT NULL COMMENT '电话',
  `contact_name` varchar(255) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(255) DEFAULT NULL COMMENT '联系人电话',
  `contact_email` varchar(255) DEFAULT NULL COMMENT '联系人邮件',
  `service_provider` varchar(255) DEFAULT NULL COMMENT '服务提供商',
  `coordinate` varchar(255) DEFAULT NULL COMMENT '坐标值',
  `order_value` int(11) DEFAULT '0' COMMENT '排序',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户ID',
  `data_state` int(11) NOT NULL COMMENT '基础数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户门店';

