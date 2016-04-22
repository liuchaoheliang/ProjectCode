//商户登录是非安全框架登录，商户的管理平台url不需要在user_resource表中配置资源,全部自己控制
1收银台:cashdesk_info.action
1基本信息:home.action
1商家介绍:message_manage.action
1商家产品:merchant_product_list.action
1商家相册:merchant_photo_list.action
1商家优惠:merchant_preferential_list.action
1积分返利:query_trans.action
1积分兑换:query_exchange_trans.action
1团购:query_group_trans.action
0客诉处理:merchant_complaint_list.action
0客诉回复:to_merchantComplaint_reply.action
0商户回复投诉：merchantComplaint_reply.action
1认证中心:toAuthentication.action
1团购兑换认证:groupExchange_authentication.action
1密码修改:toChangPasswordMerchant.action
0添加操作员：addClerk.action
1修改操作员密码：changeClerkPassword.action
0修改商户介绍信息：updPresentInfo.action
0增加商户商品信息：merchant_product_save.action
0修改商户商品信息：merchant_product_update.action
0删除商户商品：deleteProduct.action
0下线商户商品：lineDownProductJSON.action
0上线商户商品：onLineProductJSON.action
0增加商户相册：merchant_photo_save.action
0修改商户相册：merchant_photo_update.action
0删除商户相册：deletePhoto.action
0下线商户相册：lineDownPhotoJSON.action
0上线商户相册：onLinePhotoJSON.action
0增加商户优惠：merchant_preferential_save.action
0修改商户优惠：merchant_preferential_update.action
0删除商户优惠：deletePreferential.action
0下线商户优惠：lineDownPreferentialJSON.action
0上线商户优惠：onLinePreferentialJSON.action

0查看操作员列表: merchant_clerk_list.action
0查看操作员信息: clerk_info.action
0删除操作员: delete_clerk.action
0启用操作员: online_clerk.action

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','home.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'基本信息菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','cashdesk_info.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'收银台菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','message_manage.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'商家介绍菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_product_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'商家产品菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_photo_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'商家相册菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','merchant_preferential_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'商家优惠菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'积分返利菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_exchange_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'积分兑换菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','query_group_trans.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'团购菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_complaint_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'客诉处理菜单');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','to_merchantComplaint_reply.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'客诉回复');


insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','toAuthentication.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'认证中心菜单');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','groupExchange_authentication.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'团购兑换认证');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','toChangPasswordMerchant.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'密码修改菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','to_add_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'添加操作员菜单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','addClerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'添加操作员');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','changeClerkPassword.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'修改操作员密码');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','updPresentInfo.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'修改商户介绍信息');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_product_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'增加商户商品信息');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_product_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'修改商户商品信息');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deleteProduct.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'删除商户商品');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownProductJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'下线商户商品');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLineProductJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'上线商户商品');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_photo_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'增加商户相册');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_photo_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'修改商户相册');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deletePhoto.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'删除商户相册');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownPhotoJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'下线商户相册');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLinePhotoJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'上线商户相册');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_preferential_save.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'增加商户优惠');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_preferential_update.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'修改商户优惠');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','deletePreferential.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'删除商户优惠');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','lineDownPreferentialJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'下线商户优惠');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','onLinePreferentialJSON.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'上线商户优惠');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchantComplaint_reply.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'商户回复投诉');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','merchant_clerk_list.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'查看操作员列表');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','clerk_info.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'查看操作员信息');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','delete_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'删除操作员');

insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','0','online_clerk.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'启用操作员');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','add_goods_page.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'手机银行卡收款');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','create_order.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'手机银行卡收款生成订单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','pay_transaction.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'手机银行卡收款提交订单');
		
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','send_points_page.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'赠送积分');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','create_order_of_send_points.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'赠送积分生成订单');
insert into merchant_clerk_url
		(id,url_type,url,state,create_time,update_time,remark) 
		values
		('','1','pay_transPoints.action','30',DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),DATE_FORMAT(NOW(),"%Y-%m-%d|%H:%i:%s"),'赠送积分提交订单');