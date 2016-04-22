/**
 * 日志表设计
 */
DROP TABLE IF EXISTS froadfft.fft_log;
CREATE TABLE froadfft.fft_log (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	operation varchar(255) DEFAULT NULL COMMENT '操作',
	operator varchar(255) DEFAULT NULL COMMENT '操作员',
	content text DEFAULT NULL COMMENT '内容',
	parameter text DEFAULT NULL COMMENT '请求参数',
	ip varchar(255) DEFAULT NULL COMMENT 'IP',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='日志';
