/**
 * 支付表设计
 */
DROP TABLE IF EXISTS froadfft.fft_pay;
CREATE TABLE froadfft.fft_pay (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',
	trans_id bigint(20) NOT NULL COMMENT '交易ID',
	type varchar(255) NOT NULL COMMENT '支付类型详情',
	type_details varchar(255) NOT NULL COMMENT '支付类型',
	step int(11) NOT NULL COMMENT '支付步骤',
	state varchar(255) NOT NULL COMMENT '支付状态',
	pay_value varchar(255) NOT NULL COMMENT '支付值',
	from_account_name varchar(255) COMMENT '从哪个账户名',
	from_account_no varchar(255) COMMENT '从哪个账户号',
	to_account_name varchar(255)  COMMENT '到哪个账户名',
	to_account_no varchar(255) COMMENT '到哪个账户号',
	from_phone varchar(255) COMMENT '从哪个手机银行',
	to_phone varchar(255) COMMENT '到哪个手机银行',
	from_role varchar(255) COMMENT '从哪个角色',
	to_role varchar(255) COMMENT '到哪个角色',
	
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='支付';
