/**
 * 协议表设计
 */
DROP TABLE IF EXISTS froadfft.fft_agreement;
CREATE TABLE froadfft.fft_agreement (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	type int(11) NOT NULL COMMENT '类型 0-注册',
	content text COMMENT '详细内容',
	client_id bigint(20) NOT NULL COMMENT '客户端版本号',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='协议';
