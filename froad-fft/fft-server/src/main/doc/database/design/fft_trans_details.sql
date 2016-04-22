/**
 * 交易详情表设计
 */
DROP TABLE IF EXISTS froadfft.fft_trans_details;
CREATE TABLE froadfft.fft_trans_details (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',
	trans_id bigint(20) NOT NULL COMMENT '交易ID',
	product_id bigint(20) NOT NULL COMMENT '商品ID',
	quantity int(11) NOT NULL COMMENT '购买数量',
	price varchar(255) NOT NULL COMMENT '金额',
	single varchar(255) NOT NULL COMMENT '单价',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='交易详情';
