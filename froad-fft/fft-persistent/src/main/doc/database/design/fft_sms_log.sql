/**
 * 短信日志表设计
 */
DROP TABLE IF EXISTS `fft_sms_log`;
CREATE TABLE `fft_sms_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `sms_type` int(11) NOT NULL COMMENT '短信类型',
  `client_id` bigint(20) NOT NULL COMMENT '客户端编号',
  `mobile` varchar(255) NOT NULL COMMENT '手机号码',
  `content` text NOT NULL COMMENT '发送内容',
  `is_success` bit(1) NOT NULL COMMENT '是否发送成功',
  `send_user` varchar(255) DEFAULT NULL COMMENT '发送短信方 null表示为系统发送',
  `send_ip` varchar(255) NOT NULL COMMENT '发送方IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='短信日志';

