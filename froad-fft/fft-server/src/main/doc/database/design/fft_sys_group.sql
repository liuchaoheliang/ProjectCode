/**
 * 组表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_group;
CREATE TABLE froadfft.fft_sys_group (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '名称',
	description varchar(255) NOT NULL COMMENT '描述',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='组';
