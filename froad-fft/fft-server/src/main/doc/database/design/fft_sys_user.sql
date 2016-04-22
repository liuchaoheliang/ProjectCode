/**
 * 用户表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_user;
CREATE TABLE froadfft.fft_sys_user (
	id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	create_time datetime NOT NULL COMMENT '创建时间',
	update_time datetime NOT NULL COMMENT '更新时间',  
	username varchar(255) NOT NULL UNIQUE COMMENT '帐号',
	password varchar(255) NOT NULL COMMENT '密码',
	alias varchar(255) DEFAULT NULL COMMENT '别名',
	email varchar(255) DEFAULT NULL COMMENT '邮箱地址',
	phone varchar(255) DEFAULT NULL COMMENT '电话号码',
	department varchar(255) DEFAULT NULL COMMENT '部门',
	is_enabled bit(1) NOT NULL COMMENT '是否启用',
	is_locked bit(1) NOT NULL COMMENT '是否锁定',
	login_failure_count int(11) DEFAULT 0 COMMENT '连续登录失败次数',
	locked_date datetime DEFAULT NULL COMMENT '锁定时间',
	login_date datetime DEFAULT NULL COMMENT '登录时间',
	login_ip varchar(255) DEFAULT NULL COMMENT '登录IP',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='用户';
