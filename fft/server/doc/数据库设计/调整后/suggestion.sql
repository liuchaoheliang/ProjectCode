//�������
create table suggestion(
  id int(32) NOT NULL auto_increment COMMENT '���� ����100001001��ʼ',    
  user_id varchar(36) not null comment '�û�ID',  
  user_name varchar(32) not null comment '�û�����',
  user_mobile varchar(11) DEFAULT null comment '�û��ֻ�',  
  user_email varchar(32) default null comment '�û�EMAIL',
  title varchar(64) default null comment '����',
  content varchar(1024) not null comment '����',  
  is_reply varchar(32) default '0' comment '�Ƿ�ظ� 0-�� 1-��',  

  state varchar(32) default '30' COMMENT '״̬(10-������20-¼�룬30-���ã�40-ͣ�ã�50-ɾ��)',
  create_time varchar(32) default NULL COMMENT '����ʱ��',
  update_time varchar(32) default NULL COMMENT '����ʱ��',
  remark varchar(255) default NULL COMMENT '��ע',  
  PRIMARY KEY  (`id`) 
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;