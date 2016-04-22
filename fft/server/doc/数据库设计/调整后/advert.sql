//广告位
create table advert(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始',  
  type varchar(32) NOT NULL COMMENT '类型 1-商户 2-商品 3-活动',  
  position varchar(32) not null COMMENT '位置 1-顶部 2-中间 3-底部',
  name varchar(32) not null comment '名称',
  images varchar(32) default null comment '图片地址',  
  link varchar(255) default null comment '链接地址',  
  is_blank_targe varchar(32) default '1' comment '是否在新窗口中打开 0-否 1-是',  
  start_time varchar(32) default NULL COMMENT '开始时间',
  end_time varchar(32) default NULL COMMENT '结束时间',  
  click_count varchar(32) default '0' comment '点击次数',

  state varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;