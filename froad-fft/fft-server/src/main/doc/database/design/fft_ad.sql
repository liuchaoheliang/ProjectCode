/**
 * 广告表设计
 */
DROP TABLE IF EXISTS froadfft.fft_ad;
CREATE TABLE froadfft.fft_ad (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	title varchar(255) NOT NULL COMMENT '标题',  
	type int(11) NOT NULL COMMENT '类型 0-文本 1-图片 2-flash',
	begin_time datetime DEFAULT NULL COMMENT '开始时间',
	end_time datetime DEFAULT NULL COMMENT '结束时间',  
	content text COMMENT '内容',
	path varchar(255) DEFAULT NULL COMMENT '路径',
	link varchar(255) DEFAULT NULL COMMENT '链接地址',  
	is_enabled bit(1) NOT NULL COMMENT '是否启用',  
	is_blank_targe bit(1) NOT NULL COMMENT '是否在新窗口打开',
	click_count int(11) DEFAULT 0 COMMENT '点击次数',  
	order_value int(11) DEFAULT 0 COMMENT '排序',  
	ad_position_id bigint(20) NOT NULL COMMENT '广告位ID',
	client_id bigint(20) NOT NULL COMMENT '客户端编号',
	data_state int(11) NOT NULL COMMENT '基础数据状态',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='广告';
