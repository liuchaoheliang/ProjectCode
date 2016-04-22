//建议回复
create table suggestion_reply(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',   
  suggestion_id varchar(32) not null comment '建议ID',
  title varchar(64) default null comment '标题',  
  content varchar(1024) not null comment '内容',  
  reply_account varchar(32) not null comment '回复人',
  
  state varchar(32) default '30' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;