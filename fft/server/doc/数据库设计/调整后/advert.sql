//���λ
create table advert(
  id int(32) NOT NULL auto_increment COMMENT '���� ����100001001��ʼ',  
  type varchar(32) NOT NULL COMMENT '���� 1-�̻� 2-��Ʒ 3-�',  
  position varchar(32) not null COMMENT 'λ�� 1-���� 2-�м� 3-�ײ�',
  name varchar(32) not null comment '����',
  images varchar(32) default null comment 'ͼƬ��ַ',  
  link varchar(255) default null comment '���ӵ�ַ',  
  is_blank_targe varchar(32) default '1' comment '�Ƿ����´����д� 0-�� 1-��',  
  start_time varchar(32) default NULL COMMENT '��ʼʱ��',
  end_time varchar(32) default NULL COMMENT '����ʱ��',  
  click_count varchar(32) default '0' comment '�������',

  state varchar(32) default '10' COMMENT '״̬(10-������20-¼�룬30-���ã�40-ͣ�ã�50-ɾ��)',
  create_time varchar(32) default NULL COMMENT '����ʱ��',
  update_time varchar(32) default NULL COMMENT '����ʱ��',
  remark varchar(255) default NULL COMMENT '��ע',  
  PRIMARY KEY  (`id`)
)ENGINE=MyISAM AUTO_INCREMENT=100001001 DEFAULT CHARSET=utf8;