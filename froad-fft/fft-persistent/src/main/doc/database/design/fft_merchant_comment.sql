/**
 * 商户点评表设计
 */
DROP TABLE IF EXISTS `fft_merchant_comment`;
CREATE TABLE `fft_merchant_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `star_level` int(11) NOT NULL COMMENT '总体评价星级',
  `product_point` int(11) NOT NULL COMMENT '产品打分',
  `environment_point` int(11) NOT NULL COMMENT '环境打分',
  `serve_point` int(11) NOT NULL COMMENT '服务打分',
  `comment_description` text COMMENT '评论说明',
  `merchant_outlet_id` bigint(20) NOT NULL COMMENT '商户门店',
  `member_code` bigint(20) NOT NULL COMMENT '评分用户',
  `data_state` int(11) NOT NULL COMMENT '基础数据状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='商户点评表';
