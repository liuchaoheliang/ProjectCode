/**
 * 话费充值表设计
 */
DROP TABLE IF EXISTS froadfft.fft_hfcz;
CREATE TABLE froadfft.fft_hfcz (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',
	trans_id bigint(20) NOT NULL COMMENT '交易ID',
	phone_number varchar(255) NOT NULL COMMENT '充值号码',
	money varchar(255) NOT NULL COMMENT '充值金额',
	area varchar(255) COMMENT '所属地区',
	operator varchar(255) COMMENT '运营商',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='话费充值';
