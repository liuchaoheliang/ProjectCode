/**
 * 商品属性表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product_attribute;
CREATE TABLE froadfft.fft_product_attribute (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间', 
	name varchar(255) NOT NULL COMMENT '属性名称',
	attribute_type int(11) NOT NULL COMMENT '属性类型',
	is_required bit(1) NOT NULL COMMENT '是否必填',
	is_enabled bit(1) NOT NULL COMMENT '是否启用',
	order_value int(1) DEFAULT 0 COMMENT '排序',
	product_type_id bigint(20) NOT NULL COMMENT '商品类型ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商品属性';
