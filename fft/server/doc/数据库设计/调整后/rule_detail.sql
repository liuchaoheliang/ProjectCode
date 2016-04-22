//规则明细
create table rule_detail(
  id int(32) NOT NULL auto_increment COMMENT '主键 自增100001001开始', 
  
  rule_id varchar(32) not null COMMENT '规则ID',
  rule_type varchar(32) not null COMMENT '规则类型 10-买家 20-卖家',
  from_role varchar(32) not null COMMENT '支出角色 10-买家 20-卖家 30-银行 40-方付通',
  to_role varchar(32) not null COMMENT '收入角色 10-买家 20-卖家 30-银行 40-方付通',
  calculate_type varchar(32) not null COMMENT '计算类型 10-公式 20-类',
  formula varchar(32) default null COMMENT '公式',
  param_of_formula varchar(32) default null COMMENT '公式的参数',
  class_name varchar(32) default null COMMENT '类名称',
  is_enable varchar(32) default '0' COMMENT '是否启用 0-否 1-是',
  state varchar(32) default '10' COMMENT '状态(10-创建，20-录入，30-启用，40-停用，50-删除)',
  create_time varchar(32) default NULL COMMENT '创建时间',
  update_time varchar(32) default NULL COMMENT '更新时间',
  remark varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (id) 
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;

