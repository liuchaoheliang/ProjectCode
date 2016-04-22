/**
 * 卖家表设计
 */
DROP TABLE IF EXISTS froadfft.fft_seller;
CREATE TABLE froadfft.fft_seller (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	merchant_id bigint(20) NOT NULL COMMENT '商家ID',
	seller_type int(11) NOT NULL COMMENT '卖家类型',
	description varchar(255) DEFAULT NULL COMMENT '描述',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='卖家';
