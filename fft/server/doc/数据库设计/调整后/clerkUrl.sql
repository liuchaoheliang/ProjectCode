//�̻���¼�Ƿǰ�ȫ��ܵ�¼���̻��Ĺ���ƽ̨url����Ҫ��user_resource����������Դ,ȫ���Լ�����
1����̨:cashdesk_info.action
1������Ϣ:home.action
1�̼ҽ���:message_manage.action
1�̼Ҳ�Ʒ:merchant_product_list.action
1�̼����:merchant_photo_list.action
1�̼��Ż�:merchant_preferential_list.action
1���ַ���:query_trans.action
1���ֶһ�:query_exchange_trans.action
1�Ź�:query_group_trans.action
0���ߴ���:merchant_complaint_list.action
0���߻ظ�:to_merchantComplaint_reply.action
0�̻��ظ�Ͷ�ߣ�merchantComplaint_reply.action
1��֤����:toAuthentication.action
1�Ź��һ���֤:groupExchange_authentication.action
1�����޸�:toChangPasswordMerchant.action
0��Ӳ���Ա��addClerk.action
1�޸Ĳ���Ա���룺changeClerkPassword.action
0�޸��̻�������Ϣ��updPresentInfo.action
0�����̻���Ʒ��Ϣ��merchant_product_save.action
0�޸��̻���Ʒ��Ϣ��merchant_product_update.action
0ɾ���̻���Ʒ��deleteProduct.action
0�����̻���Ʒ��lineDownProductJSON.action
0�����̻���Ʒ��onLineProductJSON.action
0�����̻���᣺merchant_photo_save.action
0�޸��̻���᣺merchant_photo_update.action
0ɾ���̻���᣺deletePhoto.action
0�����̻���᣺lineDownPhotoJSON.action
0�����̻���᣺onLinePhotoJSON.action
0�����̻��Żݣ�merchant_preferential_save.action
0�޸��̻��Żݣ�merchant_preferential_update.action
0ɾ���̻��Żݣ�deletePreferential.action
0�����̻��Żݣ�lineDownPreferentialJSON.action
0�����̻��Żݣ�onLinePreferentialJSON.action

0�鿴����Ա�б�: merchant_clerk_list.action
0�鿴����Ա��Ϣ: clerk_info.action
0ɾ������Ա: delete_clerk.action
0���ò���Ա: online_clerk.action

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','home.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'������Ϣ�˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','cashdesk_info.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'����̨�˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','message_manage.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�̼ҽ��ܲ˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_product_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�̼Ҳ�Ʒ�˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_photo_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�̼����˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_preferential_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�̼��Żݲ˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ַ����˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_exchange_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ֶһ��˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_group_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�Ź��˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_complaint_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ߴ���˵�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','to_merchantComplaint_reply.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���߻ظ�');


insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','toAuthentication.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'��֤���Ĳ˵�');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','groupExchange_authentication.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�Ź��һ���֤');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','toChangPasswordMerchant.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����޸Ĳ˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','to_add_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'��Ӳ���Ա�˵�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','addClerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'��Ӳ���Ա');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','changeClerkPassword.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�޸Ĳ���Ա����');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','updPresentInfo.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�޸��̻�������Ϣ');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_product_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻���Ʒ��Ϣ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_product_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�޸��̻���Ʒ��Ϣ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deleteProduct.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'ɾ���̻���Ʒ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownProductJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻���Ʒ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLineProductJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻���Ʒ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_photo_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻����');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_photo_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�޸��̻����');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deletePhoto.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'ɾ���̻����');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownPhotoJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻����');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLinePhotoJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻����');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_preferential_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻��Ż�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_preferential_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�޸��̻��Ż�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deletePreferential.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'ɾ���̻��Ż�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownPreferentialJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻��Ż�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLinePreferentialJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�����̻��Ż�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchantComplaint_reply.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�̻��ظ�Ͷ��');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_clerk_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�鿴����Ա�б�');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','clerk_info.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�鿴����Ա��Ϣ');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','delete_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'ɾ������Ա');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','online_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ò���Ա');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','add_goods_page.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�ֻ����п��տ�');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','create_order.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�ֻ����п��տ����ɶ���');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','pay_transaction.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'�ֻ����п��տ��ύ����');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','send_points_page.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ͻ���');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','create_order_of_send_points.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ͻ������ɶ���');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','pay_transPoints.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'���ͻ����ύ����');