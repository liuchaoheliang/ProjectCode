//意见或建议
create table suggestion(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',    
  user_id varchar(36) not null comment '用户ID',  
  user_name varchar(32) not null comment '用户姓名',
  user_mobile varchar(11) DEFAULT null comment '用户手机',  
  user_email varchar(32) default null comment '用户EMAIL',
  title varchar(64) default null comment '标题',
  content varchar(1024) not null comment '内容',  
  is_reply varchar(32) default '0' comment '是否回复 0-否 1-是',  

  state varchar(32) default '30' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',  
  PRIMARY KEY  (`id`) 
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;