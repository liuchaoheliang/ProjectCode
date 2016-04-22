/**
 * 团购商品表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product_group;
CREATE TABLE froadfft.fft_product_group (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	title varchar(255) NOT NULL COMMENT '团购标题',
	summary text DEFAULT NULL COMMENT '团购商品简介',
	area_name varchar(255) NOT NULL COMMENT '地区名称',
	per_number varchar(255) NOT NULL COMMENT '每人限购',
	permin_number varchar(255) NOT NULL COMMENT '最低购买',
	start_time datetime NOT NULL COMMENT '有效期-开始 ',
	end_time datetime NOT NULL COMMENT '有效期-结束',
	price varchar(255) NOT NULL COMMENT '销售价',
	buy_know text NOT NULL COMMENT '购买需知',
	image varchar(255) NOT NULL COMMENT '展示图片',
	description text NOT NULL COMMENT '描述',
	PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商品';
