
UPDATE cb_area SET client_id='chongqing' WHERE client_id ='0' AND tree_path LIKE '2090%';

INSERT  INTO `cb_client_payment_channel`(`id`,`client_id`,`payment_channel_id`,`name`,`full_name`,`type`,`ico_url`,`payment_org_no`,`is_froad_check_token`,`point_rate`,`is_delete`) VALUES 
(null,'chongqing','003dd8e60003','�������л���','�������л���','2','/froad-cb/froad-ico/cq_logo.png','100010043','\0','10','\0'),
(null,'chongqing','003dd8e60005','����ũ��','����ũ��','51','/froad-cb/froad-ico/cq_logo.png','664','\0','','\0'),
(null,'chongqing','003dd8e60006','���켰ʱ֧��','���켴ʱ֧��','55','/froad-cb/froad-ico/cq_logo.png','257','\0','','\0'),
(null,'chongqing','01A68B608000','����ũ��','����ũ��','20','/froad-cb/froad-ico/cq_logo.png','264','\0','','\0');


delete from cb_transfer_cq;
insert into cb_transfer_cq (old_id,new_id,type) values ('11','100001030,100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('111','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('112','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('113','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('114','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('115','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('116','100001031','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('12','100001032,100001033,100001045,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('120','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('121','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('122','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('123','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('124','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('125','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('126','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('127','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('128','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('129','100001033,100001046','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('130','100001033,100001046','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('80','100001038,100001039','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('86','100001036,100001037','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('2','100001040','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('23','100001041','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('231','100001041','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('232','100001041','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('96','100001010','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('97','100001011,100001013','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('245','100001011,100001013','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('98','100001018','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('99','100001019,100001020','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('240','100001026','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('241','100001027','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('243','100001002','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('244','100001001','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('251','100001004','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('252','100001005','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('246','100001016','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('247','100001017','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('248','100001017','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('249','100001017','15');

insert into cb_transfer_cq (old_id,new_id,type) values ('990','100001040','15');
insert into cb_transfer_cq (old_id,new_id,type) values ('991','100001041','15');





-------------------------------------------------------------
-----------------������ֲ��ɺ�ִ��--------------------------
INSERT INTO cb_product_category VALUES ('100000000', 'chongqing', 'ȫ������', '100000000', '0', '\0', '/froad-cb/coremodule/201505/0df86335-247e-471c-891b-3563d8e06396.jpg', '0');
update cb_product_category set ico_url='/froad-cb/froad-ico/baihuo.png' where name='���ðٻ�' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/meishi.png' where name='������ʳ' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/meiti.png' where name='��������' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/fuwu.png' where name='��������' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/jiazheng.png' where name='ϴ�¼���' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/sheyin.png' where name='��ɴ��Ӱ' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/yule.png' where name='��������' and client_id='chongqing';
update cb_product_category set ico_url='/froad-cb/froad-ico/travel.png' where name='��ͨ����' and client_id='chongqing';

update cb_merchant_category set ico_url='/froad-cb/froad-ico/meishi.png' where name='������ʳ' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/yule.png' where name='��������' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/sheyin.png' where name='��ɴ��Ӱ' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/jiazheng.png' where name='ϴ�¼���' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/meiti.png' where name='��������' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/fuwu.png' where name='��������' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/baihuo.png' where name='���ðٻ�' and client_id='chongqing';
update cb_merchant_category set ico_url='/froad-cb/froad-ico/travel.png' where name='��ͨ����' and client_id='chongqing';

update  cb_product_category set order_value='8' where name='������ҵ' and client_id='chongqing';
update  cb_product_category set order_value='7' where name='��������' and client_id='chongqing';
update  cb_product_category set order_value='6' where name='��ɴ��Ӱ' and client_id='chongqing';
update  cb_product_category set order_value='5' where name='ϴ�¼���' and client_id='chongqing';
update  cb_product_category set order_value='4' where name='��������' and client_id='chongqing';
update  cb_product_category set order_value='3' where name='��������' and client_id='chongqing';
update  cb_product_category set order_value='2' where name='���ðٻ�' and client_id='chongqing';
update  cb_product_category set order_value='1' where name='��ͨ����' and client_id='chongqing';

UPDATE cb_outlet INNER JOIN cb_org ON cb_outlet.outlet_id=cb_org.outlet_id AND cb_org.`client_id`='chongqing' SET cb_outlet.merchant_id=cb_org.merchant_id WHERE cb_outlet.client_id='chongqing' AND cb_outlet.merchant_id!=cb_org.merchant_id
