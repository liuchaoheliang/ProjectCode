/**
 * 商品属性值表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product_attribute_value;
CREATE TABLE froadfft.fft_product_attribute_value (
	product_id bigint(20) NOT NULL COMMENT '商品ID',
	product_attribute_id bigint(20) NOT NULL COMMENT '商品属性ID',
	value varchar(255) NOT NULL COMMENT '属性值'
) DEFAULT CHARSET=utf8 COMMENT='商品属性值';
