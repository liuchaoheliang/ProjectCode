INSERT INTO cb_area  VALUES ('3091', 'chongqing', '两江新区', NULL, '2090,3091', '2090', '1', '两江新区', NULL);
INSERT INTO cb_area  VALUES ('3092', 'chongqing', '西永镇', NULL, '2090,3092', '2090', '1', '西永镇', NULL);
INSERT INTO cb_area  VALUES ('3093', 'chongqing', '营业部', NULL, '2090,3093', '2090', '1', '营业部', NULL);
INSERT INTO cb_area  VALUES ('3094', 'chongqing', '万盛区', NULL, '2090,3094', '2090', '1', '万盛区', NULL);

--银行资源
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001000','chongqing','任务中心','','0','','','fa-home','0','','','1');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001001','chongqing','每日工作提醒','','100001000','','work_remind.html','','100000000','','/bank/login/pan','2');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001002','chongqing','待审核团购','','100001000','','fomous_group.html;ground_detail.html','','100000000','','/bank/groupProduct/lt;/bank/bankOrg/sc;/bank/groupProduct/dl','4');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001004','chongqing','待审核商户','','100001000','','fomous_merch.html;fomous_merchant_detail.html','','100000000','','/bank/bankOrg/sc;/bank/merchant/gmt','3');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001010','chongqing','订单管理','','0','','','fa-file-o','0','','','12');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001011','chongqing','团购订单列表','','100001010','','orderGroup_list.html;orderGroup_detail.html;order_LargedetailGro','','100000010','','/bank/order/golt;/bank/bankOrg/sc;/bank/orderExport/goet;/bank/order/godl','13');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001013','chongqing','面对面订单列表','','100001010','','orderFaceToFace_list.html;orderFaceToFace_detail.html','','100000010','','/bank/order/colt;/bank/bankOrg/sc;/bank/orderExport/coet;/bank/order/codl','15');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001016','chongqing','商户管理','','0','','','fa-bookmark-o','0','','','18');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001017','chongqing','商户列表','','100001016','','merchant_list.html;merchant_infor.html;merchant_updata.html','','100000016','','/bank/merchant/ad;/bank/bankOrg/sa;/bank/merchant/gmc;/bank/bankOrg/bs;/bank/merchant/ue;/bank/merchant/dl;/bank/merchant/gmt;/bank/merchant/tn;/bank/bankOrg/sc;/bank/merchant/rp','19');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001018','chongqing','评价管理','','0','','','fa-tags','0','','','20');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001019','chongqing','商户评价列表','','100001018','','commodity_mod.html','','100000018','','/bank/bankOrg/sc;/bank/merchantComment/lt','21');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001020','chongqing','商品评价列表','','100001018','','commodityAppraise_yushou.html','','100000018','','/bank/presaleComment/lt;/bank/presaleComment/dl;/bank/presaleComment/ue;/bank/presaleComment/de','22');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001026','chongqing','团购管理','','0','','','fa-sitemap','0','','','27');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001027','chongqing','团购列表','','100001026','','group_list.html;grounp_list_detail.html','','100000026','','/bank/bankOrg/sc;/bank/groupProduct/lt;/bank/presaleProduct/it;/bank/groupProduct/dl','28');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001030','chongqing','角色管理','','0','','','fa-user','0','','','33');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001031','chongqing','角色列表','','100001030','','role_manage.html;add_role.html;mod_role.html','','100000030','','/bank/role/lt;/bank/role/de;/bank/role/rlt;/bank/role/ad;/bank/role/dl;/bank/role/ue','34');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001032','chongqing','操作员管理','','0','','','fa-th','0','','','35');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001033','chongqing','操作员列表','','100001032','','operation_manage.html;add_ope.html;mod_ope.html','','100000032','','/bank/operator/lt;/bank/bankOrg/lt;/bank/operator/it;/bank/operator/de;/bank/bankOrg/ls;/bank/role/alt;/bank/operator/ad;/bank/operator/dl;/bank/role/alt;/bank/operator/ue','36');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001036','chongqing','机构管理','','0','','','fa-cogs','0','','','39');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001037','chongqing','机构列表','','100001036','','information_main.html;add_org.html;mod_org.html','','100000036','','/bank/bankOrg/lt;/bank/bankOrg/sc;/bank/bankOrg/sa;/bank/bankOrg/it;/bank/bankOrg/dl;/bank/bankOrg/ad;/bank/bankOrg/ue','40');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001038','chongqing','操作日志','','0','','','fa-file-text','0','','','41');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001039','chongqing','操作日志','','100001038','','operate_log.html','','100000038','','/bank/bankOrg/ls;/bank/operatorLog/lt','42');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001040','chongqing','安全中心','','0','','','fa-bullhorn','0','','','43');
insert into `cb_bank_resource` (`id`, `client_id`, `resource_name`, `resource_type`, `parent_resource_id`, `status`, `resource_url`, `resource_icon`, `tree_path`, `is_delete`, `api`, `order_value`) values('100001041','chongqing','修改密码','','100001040','','password_set.html','','100000040','','bank/safeCenter/lpue','44');

--商户资源
INSERT INTO `cb_merchant_resource` VALUES (110000000, '商户管理', 'fa fa-folder-o', '', '', '1', 0, '110000000', '', NULL, NULL, '', 1, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (110000001, '基本信息', '', 'businessInfor.html', '/outlet/info', '2', 110000000, '110000000,110000001', '', NULL, NULL, '', 2, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (110000002, '门店管理', '', 'outletList.html', '/outlet/list;/outlet/add;/outlet/del;/outlet/qc;/common/bank;/img/upload;/outlet/ld', '2', 110000000, '110000000,110000002', '', NULL, NULL, '', 3, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (210000000, '商品管理', 'fa fa-gift', '', '', '1', 0, '210000000', '', NULL, NULL, '', 4, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (210000001, '商品查询', '', 'productList.html', '/product/list;/product/tth;/product/pd;/product/upd;/product/ld', '2', 210000000, '210000000,210000001', '', NULL, NULL, '', 5, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (210000002, '商品发布', '', 'addProduct.html', '/img/upload;/product/qc;/product/mdy;/product/add', '2', 210000000, '210000000,210000002', '', NULL, NULL, '', 6, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000000, '交易管理', 'fa fa-file-text', '', '', '1', 0, '310000000', '', NULL, NULL, '', 8, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000001, '团购提货', '', 'deliveryVerification.html', '/order/lsd;/order/qcl;/order/su', '2', 310000000, '310000000,310000001', '', NULL, NULL, '', 9, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000002, '订单查询', '', 'orderList.html', '/common/downQcode;/order/qfg;/order/gogd;/order/ship', '2', 310000000, '310000000,310000002', '', NULL, NULL, '', 10, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000003, '提货码管理', '', 'codeManage.html', '/order/qcl;/common/downCode', '2', 310000000, '310000000,310000003', '', NULL, NULL, '', 11, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000004, '商户评价管理', '', 'merchantReviews.html', '/comment/qoc;/comment/roc;/comment/pcd', '2', 310000000, '310000000,310000004', '', NULL, NULL, '', 12, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (310000005, '商品评价管理', '', 'productReviews.html', '/comment/qpcl;/comment/rpy;/comment/qpd;/comment/rpyb', '2', 310000000, '310000000,310000005', '', NULL, NULL, '', 13, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (410000000, '数据分析', 'fa fa-table', '', '', '1', 0, '410000000', '\0', NULL, NULL, '', 14, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (410000001, '商户营业报表', '', '', '', '2', 410000000, '410000000,410000001', '\0', NULL, NULL, '', 15, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (410000002, '商品销售报表', '', '', '', '2', 410000000, '410000000,410000002', '\0', NULL, NULL, '', 16, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (510000000, '用户管理', 'fa fa-user', '', '', '1', 0, '510000000', '', NULL, NULL, '', 17, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (510000001, '用户查询', '', 'userList.html', '/outlet/qall;/outlet/mou;/outlet/moup;/outlet/qall;/outlet/mou;/outlet/moua;/outlet/moup;/outlet/mou', '2', 510000000, '510000000,510000001', '', NULL, NULL, '', 18, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (510000002, '角色查询', '', 'roleQueries.html', '/role/list;/role/list;/outlet/qall;/outlet/mou', '2', 510000000, '510000000,510000002', '', NULL, NULL, '', 19, 'chongqing');
INSERT INTO `cb_merchant_resource` VALUES (510000003, '密码修改', '', 'changePassword.html', '/update;/reset;/outlet/mou', '2', 510000000, '510000000,510000003', '', NULL, NULL, '', 20, 'chongqing');

INSERT INTO `cb_merchant_role` VALUES ('100000003', 'chongqing', '超级管理员', '', '\0');
INSERT INTO `cb_merchant_role` VALUES ('100000004', 'chongqing', '门店管理员', '', '\0');
INSERT INTO `cb_merchant_role` VALUES ('100000005', 'chongqing', '门店操作员', '', '\0');

INSERT INTO `cb_merchant_type` VALUES ('100000003', '团购', '\0', '0', 'chongqing');
INSERT INTO `cb_merchant_type` VALUES ('100000004', '直接优惠', '\0', '1', 'chongqing');


INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1100', '您设置了新的支付密码，请注意保管密码，如非本人操作请立即联系客服电话｛0｝。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1000', '尊敬的用户，您现在进行注册操作，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1305', '您本次支付验证的验证码为：{0}，请确认是您本人操作。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1306', '尊敬的用户，您现在进行绑定账号操作手机号验证，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1307', '尊敬的用户，您现在进行重置登录密码，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1309', '尊敬的用户，您的积分兑换的验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1108', '您的团购商品{0}数量{1}，团购码为：{2}，以上团购码使用有效期为：{3}，具体详情请查看个人中心券码信息。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1101', '您预订的{0}商品数量{1}个，提货码为{2}，请在{3}-{4}期间，到{5}凭预售提货码提货，具体请登陆网站查询订单详情。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1102', '尊敬的{0}用户，您在{1}购买的精品预售商品已经可以提货了，请您准备好提货码在提货期内尽快提货，以免造成不必要的损失，谢谢。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1103', '尊敬的{0}用户，您的特惠商品消费码为：{1}、{2}，请您在{3}前使用，如过期未使用我们将为您自动退款，谢谢。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1300', '感谢您使用重庆农商，本次绑定手机号验证码为{0}，30分钟内有效。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1301', '感谢您使用重庆农商，本次商户找回登录密码验证码验证码为{0}，30分钟内有效。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1104', '尊敬的{0}用户，您的社区银行商户平台已经开通,您手机号码为{1}的商户帐号，密码已经重置为:{2}，请尽快使用，祝您生活愉快。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1105', '尊敬的{0}用户,您手机号码为{1}的商户帐号，密码已经重置为:{2}，请尽快使用，祝您生活愉快。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1109', '恭喜您精品预售预订成功！我们将在提货期开始前2天发送提货码及提货网点给您，谢谢。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1001', '尊敬的用户，您现在进行重置密码操作，短信验证码为{0}，30分钟内有效。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1002', '尊敬的用户，您现在进行修改支付密码操作，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1302', '尊敬的用户，您现在进行更换手机号码操作，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1303', '尊敬的用户，您现在进行找回支付密码，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1304', '尊敬的用户，您现在进行修改登录密码，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1308', '尊敬的用户，您正在操作银行卡签约，短信验证码为{0}。', 'ANYS', '【重庆农商】', '1000', '');
INSERT INTO cb_sms_content  VALUES (null, 'chongqing', '1106', '尊敬的{0}用户,您手机号码为{1}的商户帐号，密码已经重置为:{2}，请尽快使用，祝您生活愉快。', 'ANYS', '【重庆农商】', '1000', '');

/**
*商户资源
*/

{
  "_id" : "chongqing_100000002",
  "resources" : [{
      "icon" : "fa fa-file-text",
      "parent_id" : "0",
      "resource_id" : "310000000",
      "resource_name" : "交易管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "310000000"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000001",
      "resource_name" : "团购提货",
      "resource_type" : "1",
      "resource_url" : "deliveryVerification.html",
      "api" : "/order/lsd;/order/qcl;/order/su",
      "tree_path" : "310000001"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000002",
      "resource_name" : "订单查询",
      "resource_type" : "1",
      "resource_url" : "orderList.html",
      "api" : "/common/downQcode;/order/qfg;/order/gogd;/order/ship",
      "tree_path" : "310000002"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000003",
      "resource_name" : "提货码管理",
      "resource_type" : "1",
      "resource_url" : "codeManage.html",
      "api" : "/order/qcl;/common/downCode",
      "tree_path" : "310000004"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000004",
      "resource_name" : "商户评价管理",
      "resource_type" : "1",
      "resource_url" : "merchantReviews.html",
      "api" : "/comment/qoc;/comment/roc;/comment/pcd",
      "tree_path" : "310000003"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000005",
      "resource_name" : "商品评价管理",
      "resource_type" : "1",
      "resource_url" : "productReviews.html",
      "api" : "/comment/qpcl;/comment/rpy;/comment/qpd;/comment/rpyb",
      "tree_path" : "310000004"
    }, {
      "icon" : "fa fa-user",
      "parent_id" : "0",
      "resource_id" : "510000000",
      "resource_name" : "用户管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "510000000"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000003",
      "resource_name" : "密码修改",
      "resource_type" : "1",
      "resource_url" : "changePassword.html",
      "api" : "/update;/reset;/outlet/mou",
      "tree_path" : "510000003"
    }]
}


{
  "_id" : "chongqing_100000000",
  "resources" : [{
      "icon" : "fa fa-folder-o",
      "parent_id" : "0",
      "resource_id" : "110000000",
      "resource_name" : "商户管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "110000000"
    }, {
      "icon" : "",
      "parent_id" : "110000000",
      "resource_id" : "110000001",
      "resource_name" : "基本信息",
      "resource_type" : "1",
      "resource_url" : "businessInfor.html",
      "api" : "/outlet/info",
      "tree_path" : "110000001"
    }, {
      "icon" : "",
      "parent_id" : "110000000",
      "resource_id" : "110000002",
      "resource_name" : "门店管理",
      "resource_type" : "1",
      "resource_url" : "outletList.html",
      "api" : "/outlet/list;/outlet/add;/outlet/del;/outlet/qc;/common/bank;/img/upload;/outlet/ld",
      "tree_path" : "110000002"
    }, {
      "icon" : "fa fa-gift",
      "parent_id" : "0",
      "resource_id" : "210000000",
      "resource_name" : "商品管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "210000000"
    }, {
      "icon" : "",
      "parent_id" : "210000000",
      "resource_id" : "210000001",
      "resource_name" : "商品查询",
      "resource_type" : "1",
      "resource_url" : "productList.html",
      "api" : "/product/list;/product/tth;/product/pd;/product/upd;/product/ld",
      "tree_path" : "210000001"
    }, {
      "icon" : "",
      "parent_id" : "210000000",
      "resource_id" : "210000002",
      "resource_name" : "商品发布",
      "resource_type" : "1",
      "resource_url" : "addProduct.html",
      "api" : "/img/upload;/product/qc;/product/mdy;/product/add",
      "tree_path" : "210000002"
    }, {
      "icon" : "",
      "parent_id" : "210000000",
      "resource_id" : "210000003",
      "resource_name" : "查看名优特惠商品",
      "resource_type" : "1",
      "resource_url" : "famousProductList.html",
      "api" : "/product/list",
      "tree_path" : "210000003"
    }, {
      "icon" : "fa fa-file-text",
      "parent_id" : "0",
      "resource_id" : "310000000",
      "resource_name" : "交易管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "310000000"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000001",
      "resource_name" : "团购提货",
      "resource_type" : "1",
      "resource_url" : "deliveryVerification.html",
      "api" : "/order/lsd;/order/qcl;/order/su",
      "tree_path" : "310000001"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000002",
      "resource_name" : "订单查询",
      "resource_type" : "1",
      "resource_url" : "orderList.html",
      "api" : "/common/downQcode;/order/qfg;/order/gogd;/order/ship",
      "tree_path" : "310000002"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000003",
      "resource_name" : "提货码管理",
      "resource_type" : "1",
      "resource_url" : "codeManage.html",
      "api" : "/order/qcl;/common/downCode",
      "tree_path" : "310000004"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000004",
      "resource_name" : "商户评价管理",
      "resource_type" : "1",
      "resource_url" : "merchantReviews.html",
      "api" : "/comment/qoc;/comment/roc;/comment/pcd",
      "tree_path" : "310000003"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000005",
      "resource_name" : "商品评价管理",
      "resource_type" : "1",
      "resource_url" : "productReviews.html",
      "api" : "/comment/qpcl;/comment/rpy;/comment/qpd;/comment/rpyb",
      "tree_path" : "310000004"
    }, {
      "icon" : "fa fa-user",
      "parent_id" : "0",
      "resource_id" : "510000000",
      "resource_name" : "用户管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "510000000"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000001",
      "resource_name" : "用户查询",
      "resource_type" : "1",
      "resource_url" : "userList.html",
      "api" : "/outlet/qall;/outlet/mou;/outlet/moup;/outlet/qall;/outlet/mou;/outlet/moua;/outlet/moup;/outlet/mou",
      "tree_path" : "510000001"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000002",
      "resource_name" : "角色查询",
      "resource_type" : "1",
      "resource_url" : "roleQueries.html",
      "api" : "/role/list;/role/list;/outlet/qall;/outlet/mou",
      "tree_path" : "510000002"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000003",
      "resource_name" : "密码修改",
      "resource_type" : "1",
      "resource_url" : "changePassword.html",
      "api" : "/update;/reset;/outlet/mou",
      "tree_path" : "510000003"
    }]
}


{
  "_id" : "chongqing_100000001",
  "resources" : [{
      "icon" : "fa fa-gift",
      "parent_id" : "0",
      "resource_id" : "210000000",
      "resource_name" : "商品管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "210000000"
    }, {
      "icon" : "",
      "parent_id" : "210000000",
      "resource_id" : "210000001",
      "resource_name" : "商品查询",
      "resource_type" : "1",
      "resource_url" : "productList.html",
      "api" : "/product/list;/product/tth;/product/pd;/product/upd;/product/ld",
      "tree_path" : "210000001"
    }, {
      "icon" : "fa fa-file-text",
      "parent_id" : "0",
      "resource_id" : "310000000",
      "resource_name" : "交易管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "310000000"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000001",
      "resource_name" : "团购提货",
      "resource_type" : "1",
      "resource_url" : "deliveryVerification.html",
      "api" : "/order/lsd;/order/qcl;/order/su",
      "tree_path" : "310000001"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000002",
      "resource_name" : "订单查询",
      "resource_type" : "1",
      "resource_url" : "orderList.html",
      "api" : "/common/downQcode;/order/qfg;/order/gogd;/order/ship",
      "tree_path" : "310000002"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000003",
      "resource_name" : "提货码管理",
      "resource_type" : "1",
      "resource_url" : "codeManage.html",
      "api" : "/order/qcl;/common/downCode",
      "tree_path" : "310000004"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000004",
      "resource_name" : "商户评价管理",
      "resource_type" : "1",
      "resource_url" : "merchantReviews.html",
      "api" : "/comment/qoc;/comment/roc;/comment/pcd",
      "tree_path" : "310000003"
    }, {
      "icon" : "",
      "parent_id" : "310000000",
      "resource_id" : "310000005",
      "resource_name" : "商品评价管理",
      "resource_type" : "1",
      "resource_url" : "productReviews.html",
      "api" : "/comment/qpcl;/comment/rpy;/comment/qpd;/comment/rpyb",
      "tree_path" : "310000004"
    }, {
      "icon" : "fa fa-user",
      "parent_id" : "0",
      "resource_id" : "510000000",
      "resource_name" : "用户管理",
      "resource_type" : "1",
      "resource_url" : "",
      "api" : "",
      "tree_path" : "510000000"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000001",
      "resource_name" : "用户查询",
      "resource_type" : "1",
      "resource_url" : "userList.html",
      "api" : "/outlet/qall;/outlet/mou;/outlet/moup;/outlet/qall;/outlet/mou;/outlet/moua;/outlet/moup;/outlet/mou",
      "tree_path" : "510000001"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000002",
      "resource_name" : "角色查询",
      "resource_type" : "1",
      "resource_url" : "roleQueries.html",
      "api" : "/role/list;/role/list;/outlet/qall;/outlet/mou",
      "tree_path" : "510000002"
    }, {
      "icon" : "",
      "parent_id" : "510000000",
      "resource_id" : "510000003",
      "resource_name" : "密码修改",
      "resource_type" : "1",
      "resource_url" : "changePassword.html",
      "api" : "/update;/reset;/outlet/mou",
      "tree_path" : "510000003"
    }]
}
