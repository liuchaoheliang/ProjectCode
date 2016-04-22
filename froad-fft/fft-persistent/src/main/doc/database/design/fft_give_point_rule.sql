/**
 * 送积分规则表设计
 */
DROP TABLE IF EXISTS `fft_give_point_rule`;
CREATE TABLE `fft_give_point_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `type` int(11) NOT NULL COMMENT '类型 ',
  `point_value` varchar(255) NOT NULL COMMENT '赠送积分值 ',
  `active_time` datetime NOT NULL COMMENT '规则生效时间',
  `expire_time` datetime NOT NULL COMMENT '规则失效时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='送积分规则';
