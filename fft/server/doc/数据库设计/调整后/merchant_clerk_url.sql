//�����̻�����Ա�Ͳ���Ա��Դ��-------------------liqiaopeng
create table merchant_clerk_url(
id int NOT NULL AUTO_INCREMENT COMMENT '����',
url_type varchar(32) NOT NULL COMMENT  '��Դ���� 0��������ͨ����Ա����url 1����ͨ����Ա�͹���Ա���ɷ���url' ,
url varchar(250) NOT NULL COMMENT  '��Դ' ,
state varchar(32) COMMENT '״̬',
create_time varchar(32) COMMENT '����ʱ��',
update_time varchar(32) COMMENT '����ʱ��',
remark varchar(100),
PRIMARY KEY (`id`)
) ENGINE=MyISAM auto_increment=100001001 DEFAULT CHARSET=utf8;