/**
 * 商户优惠活动表设计
 */
DROP TABLE IF EXISTS froadfft.fft_merchant_preferential;
CREATE TABLE froadfft.fft_merchant_preferential (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	title varchar(255) DEFAULT NULL COMMENT '标题',
	content text DEFAULT NULL COMMENT '内容',
	order_value int(11) DEFAULT NULL COMMENT '排序',
	merchant_outlet_id bigint(20) NOT NULL COMMENT '门店ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商户优惠活动';