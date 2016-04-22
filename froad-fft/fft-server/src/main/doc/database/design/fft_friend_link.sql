/**
 * 友情链接表设计
 */
DROP TABLE IF EXISTS froadfft.fft_friend_link;
CREATE TABLE froadfft.fft_friend_link (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	name varchar(255) NOT NULL COMMENT '名称',
	type int(11) NOT NULL COMMENT '类型 0-文本 1-图片',
	logo varchar(255) DEFAULT NULL COMMENT 'LOGO',
	url varchar(255) NOT NULL COMMENT '友情链接',
	order_value int(11) DEFAULT 0 COMMENT '排序',
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='友情链接';
