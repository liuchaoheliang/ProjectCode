/**
 * 会员表设计
 */
DROP TABLE IF EXISTS froadfft.fft_member;
CREATE TABLE froadfft.fft_member (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	member_code bigint(20) NOT NULL COMMENT '会员ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='会员';
