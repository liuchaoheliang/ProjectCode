/**
 * 商户表设计
 */
DROP TABLE IF EXISTS froadfft.fft_merchant;
CREATE TABLE froadfft.fft_merchant (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '名称',
	full_name varchar(255) NOT NULL COMMENT '全名',
	logo varchar(255) NOT NULL COMMENT '公司LOGO',
	address varchar(255) NOT NULL COMMENT '地址',
	zip varchar(255) DEFAULT NULL COMMENT '邮编',
	fax varchar(255) DEFAULT NULL COMMENT '传真',
	tel varchar(255) NOT NULL COMMENT '电话',
	contact_name varchar(255) NOT NULL COMMENT '联系人姓名',
	contact_phone varchar(255) NOT NULL COMMENT '联系人电话',
	contact_email varchar(255) NOT NULL COMMENT '联系人邮件',
	legal_name varchar(255) NOT NULL COMMENT '法人名',
	legal_credent_type varchar(255) NOT NULL COMMENT '法人证件类型',
	legal_credent_no varchar(255) NOT NULL COMMENT '法人证件号',
	contract_begintime datetime NOT NULL COMMENT '签约时间',
	contract_endtime datetime NOT NULL COMMENT '到期时间',
	contract_staff varchar(255) NOT NULL COMMENT '签约人员',
	review_staff varchar(255) NOT NULL COMMENT '复核人员',
	order_value int(11) DEFAULT 0 COMMENT '排序',
	type int(11) NOT NULL COMMENT '商户类型',
	area_id bigint(20) NOT NULL COMMENT '所在地区',
	merchant_category_id bigint(20) NOT NULL COMMENT '所属分类',		
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='商户';
