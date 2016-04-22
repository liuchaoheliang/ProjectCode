/**
 * 资金渠道表设计
 */
DROP TABLE IF EXISTS `fft_funds_channel`;
CREATE TABLE `fft_funds_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '资金机构名称',
  `full_name` varchar(255) NOT NULL COMMENT '资金机构全名',
  `channel_type` varchar(30) NOT NULL COMMENT '渠道类型',
  `pay_org` varchar(255) DEFAULT NULL COMMENT '资金机构代号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='资金渠道';

