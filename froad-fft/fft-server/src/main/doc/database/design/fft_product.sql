/**
 * 商品表设计
 */
DROP TABLE IF EXISTS froadfft.fft_product;
CREATE TABLE froadfft.fft_product (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	sn varchar(255) NOT NULL COMMENT '商品编号',
	name varchar(255) NOT NULL COMMENT '商品名',
	full_name varchar(255) NOT NULL COMMENT '商品全名',
	price varchar(255) DEFAULT NULL COMMENT '销售价',
	cost varchar(255) DEFAULT NULL COMMENT '成本价',
	market_price varchar(255) DEFAULT NULL COMMENT '市场价',
	image varchar(255) NOT NULL COMMENT '图片地址',
	weight varchar(255) DEFAULT NULL COMMENT '商品重量',
	weight_unit varchar(255) DEFAULT NULL COMMENT '重量单位（G,KG,T）',
	store int(11) DEFAULT 0 NOT NULL COMMENT '商品库存数量',
	freeze_store int(11) DEFAULT 0 NOT NULL COMMENT '冻结库存数量',
	warn_number int(11) DEFAULT 0 NOT NULL COMMENT '警告数量',
	is_marketable bit(1) NOT NULL COMMENT '是否已上架',
	is_best bit(1) NOT NULL COMMENT '是否为精品',
	is_new bit(1) NOT NULL COMMENT '是否为新品',
	is_hot bit(1) NOT NULL COMMENT '是否为热销',
	hits int(11) DEFAULT 0 COMMENT '点击数量',
	introduction text DEFAULT NULL COMMENT '介绍',
	description text DEFAULT NULL COMMENT '备注',
	keyword varchar(255) DEFAULT NULL COMMENT '搜索关键字',
	seo_title varchar(255) DEFAULT NULL COMMENT '页面标题',
	seo_keywords varchar(255) DEFAULT NULL COMMENT '页面关键词',
	seo_description text DEFAULT NULL COMMENT '页面描述',
	rack_time datetime NOT NULL COMMENT '上架时间',
	inspectors bigint(20) NOT NULL COMMENT '审核人',
	product_category_id bigint(20) NOT NULL COMMENT '商品分类ID',
	product_type_id bigint(20) NOT NULL COMMENT '商品类型ID',
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	PRIMARY KEY (`id`) 
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商品';
