/**
 * 商品类型表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product_type;
CREATE TABLE froadfft.fft_product_type (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '类型名',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商品类型';
