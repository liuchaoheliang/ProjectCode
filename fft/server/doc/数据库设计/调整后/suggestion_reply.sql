//����ظ�
create table suggestion_reply(
  id int(32) NOT NULL auto_increment COMMENT '���� ����100001001��ʼ',   
  suggestion_id varchar(32) not null comment '����ID',
  title varchar(64) default null comment '����',  
  content varchar(1024) not null comment '����',  
  reply_account varchar(32) not null comment '�ظ���',
  
  state varchar(32) default '30' COMMENT '״̬(10-������20-¼�룬30-���ã�40-ͣ�ã�50-ɾ��)',
  create_time varchar(32) default NULL COMMENT '����ʱ��',
  update_time varchar(32) default NULL COMMENT '����ʱ��',
  remark varchar(255) default NULL COMMENT '��ע',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;