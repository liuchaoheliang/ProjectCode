//认证券
create table auth_ticket(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',   
    
  user_id varchar(36) not null comment '用户ID',   
  merchant_id varchar(32) not null COMMENT '商户ID',  
  trans_id varchar(32) not null comment'交易ID',
  type varchar(32) not null comment '类型 0-团购 1-农产品', 
  securities_no varchar(32) not null comment '券号',
  is_consume varchar(32) default '0' comment '是否消费 0-否 1-是',  
  consume_time varchar(32) COMMENT '消费时间',  
  expire_time varchar(32) not null comment '过期时间',  
  sms_number varchar(32) comment '短信次数',  
  sms_time varchar(32) comment '短信时间',
  
  state varchar(32) default '30' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;