/**
 * 商品图片表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product_image;
CREATE TABLE froadfft.fft_product_image (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间', 
	title varchar(255) DEFAULT NULL COMMENT '标题',
	source varchar(255) DEFAULT NULL COMMENT '原图片',
	large varchar(255) DEFAULT NULL COMMENT '大图片',
	medium varchar(255) DEFAULT NULL COMMENT '中图片',
	thumbnail varchar(255) DEFAULT NULL COMMENT '缩略图',
	order_value int(11) DEFAULT 0 COMMENT '排序',
	product_id bigint(20) NOT NULL COMMENT '商品ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商品图片';
