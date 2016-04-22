/**
 * 注册送分规则
 */
DROP TABLE IF EXISTS `fft_register_give_point_rule`;
CREATE TABLE `fft_register_give_point_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `client_id` bigint(20) NOT NULL COMMENT '客户端',
  `begine_time` datetime NOT NULL COMMENT '规则生效时间',
  `end_time` datetime NOT NULL COMMENT '规则失效时间',
  `give_point` varchar(255) NOT NULL COMMENT '具体送分数量',
  `is_enable` bit(1) NOT NULL COMMENT '规则是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='注册送分';
