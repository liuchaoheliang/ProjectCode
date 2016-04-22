/**
 * 客户端信息表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_client;
CREATE TABLE froadfft.fft_sys_client (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '资金机构名称',
	number varchar(255) NOT NULL COMMENT '客户端编号',
	area_id bigint(20) NOT NULL COMMENT '客户端所属地',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='客户端信息';
