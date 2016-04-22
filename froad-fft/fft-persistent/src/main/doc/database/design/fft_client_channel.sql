DROP TABLE IF EXISTS `fft_client_channel`;
CREATE TABLE `fft_client_channel` (
  `funds_channel_id` bigint(20) NOT NULL COMMENT '资金渠道id',
  `client_id` bigint(20) NOT NULL COMMENT '客户端id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端表和资金渠道表的中间表';
