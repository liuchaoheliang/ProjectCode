/**
 * 商户介绍表设计
 */
DROP TABLE IF EXISTS froadfft.fft_merchant_present;
CREATE TABLE froadfft.fft_merchant_present (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间', 
	title varchar(255) DEFAULT NULL COMMENT '标题',
	content text DEFAULT NULL COMMENT '内容', 
	merchant_outlet_id bigint(20) NOT NULL COMMENT '门店ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商户介绍';
