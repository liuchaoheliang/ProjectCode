/**
 * 短信模版表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sms_content;
CREATE TABLE froadfft.fft_sms_content (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间', 
	sms_type int(11) NOT NULL COMMENT '短信类型',
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	content text NOT NULL COMMENT '模版内容',
	msgSuffix varchar(255) DEFAULT NULL COMMENT '后缀表',
	is_enable_suffix bit(1) NOT NULL COMMENT '后缀是否启用',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT=' 短信模版';
