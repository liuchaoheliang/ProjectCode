/**
 * 资金渠道表设计
 */
DROP TABLE IF EXISTS froadfft.fft_funds_channel;
CREATE TABLE froadfft.fft_funds_channel (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '资金机构名称',
	full_name varchar(255) NOT NULL COMMENT '资金机构全名',
	channel_type int(11) NOT NULL COMMENT '资产渠道类型 0-贴膜卡渠道',
	pay_org varchar(255) DEFAULT NULL COMMENT '资金机构代号',
	funds_channel_no varchar(255) DEFAULT NULL COMMENT '资金机构编号',
	currency varchar(255) DEFAULT NULL COMMENT '货币单位',
	fft_gathering_account_name varchar(255) DEFAULT NULL COMMENT '方付通收款账户',
	fft_gathering_account_number varchar(255) DEFAULT NULL ,
	bank_gathering_account_name varchar(255) DEFAULT NULL COMMENT '银行收款账户',
	bank_gathering_account_number varchar(255) DEFAULT NULL ,
	fft_points_account_name varchar(255) DEFAULT NULL COMMENT '买积分收款资金账户',
	fft_points_account_number varchar(255) DEFAULT NULL ,
	fft_points_poundage_account_name varchar(255) DEFAULT NULL COMMENT '买积分收款手续费账户',
	fft_points_poundage_account_number varchar(255) DEFAULT NULL ,
	points_withdraw_account_name varchar(255) DEFAULT NULL COMMENT '积分提现转出账户',
	points_withdraw_account_number varchar(255) DEFAULT NULL ,
	points_withdraw_poundage_account_name varchar(255) DEFAULT NULL COMMENT '积分提现手续费收款账户',
	points_withdraw_poundage_account_number varchar(255) DEFAULT NULL ,
	fft_allowance_account_name varchar(255) DEFAULT NULL COMMENT '补贴账户',
	fft_allowance_account_number varchar(255) DEFAULT NULL ,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='资金渠道';
