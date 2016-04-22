/**
 * 商户标签表设计
 */
DROP TABLE IF EXISTS froadfft.fft_merchant_outlet_tag;
CREATE TABLE froadfft.fft_merchant_outlet_tag (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	merchant_outlet_id bigint(20) NOT NULL COMMENT '商户门店Id',
	tag_id bigint(20) NOT NULL COMMENT '标签Id',
	order_value int(11) DEFAULT 0 COMMENT '排序',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商户门店';
