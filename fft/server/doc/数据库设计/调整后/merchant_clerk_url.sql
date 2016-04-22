//增加商户管理员和操作员资源表-------------------liqiaopeng
create table merchant_clerk_url(
id int NOT NULL AUTO_INCREMENT COMMENT '主键',
url_type varchar(32) NOT NULL COMMENT  '资源类型 0：不许普通操作员访问url 1：普通操作员和管理员都可访问url' ,
url varchar(250) NOT NULL COMMENT  '资源' ,
state varchar(32) COMMENT '状态',
create_time varchar(32) COMMENT '创建时间',
update_time varchar(32) COMMENT '更新时间',
remark varchar(100),
PRIMARY KEY (`id`)
) ENGINE=MyISAM auto_increment=100001001 DEFAULT CHARSET=utf8;