//每日任务
create table daily_task(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始', 

  user_id varchar(36) not null comment '用户ID',   
  sign_no varchar(32) not null comment '签到次数',  
  last_sign_time varchar(32) not null comment '最后签到时间',

  state varchar(32) default '30' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;