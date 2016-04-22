/*
database
*/
CREATE DATABASE "froad_report"
	DEFAULT CHARACTER SET utf8
	DEFAULT COLLATE utf8_general_ci;

/*
tables
*/
CREATE TABLE cb_report_bank_org (
  id bigint(20) NOT NULL COMMENT 'id',
  client_id varchar(32) DEFAULT NULL COMMENT '客户端id',
  bank_card_id char(2) DEFAULT NULL COMMENT '卡bin',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='机构银行卡对照表';
CREATE TABLE cb_report_bank_user  ( 
	id            	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	client_id     	varchar(32) COMMENT '客户端'  NOT NULL,
	create_time   	datetime COMMENT '日期'  NOT NULL,
	forg_code     	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name     	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code     	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name     	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code     	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name     	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code     	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name     	varchar(64) COMMENT '四级机构名'  NULL,
	sign_user_name	varchar(64) COMMENT '签约人名'  NULL,
	remark        	varchar(200) COMMENT '备注'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '签约用户表' ;

CREATE TABLE cb_report_batch_chunk  ( 
	chunk_id       	int(15) COMMENT 'chunk id'  NOT NULL,
	batch_date     	int(15) COMMENT '跑批日期 yyyymmdd'  NOT NULL,
	last_batch_date	int(15) NOT NULL,
	batch_id       	int(15) COMMENT '批处理ID'  NOT NULL,
	node_id        	int(15) COMMENT '节点ID'  NOT NULL,
	job_id         	int(15) COMMENT 'job id'  NOT NULL,
	job_name       	varchar(64) NOT NULL,
	input_file     	varchar(30) COMMENT '输入文件'  NOT NULL,
	chunk_page     	int(15) COMMENT '所在批次页码'  NOT NULL,
	chunk_size     	int(15) COMMENT '批处理记录数'  NOT NULL,
	total_chunk    	int(15) COMMENT '总批次'  NOT NULL,
	total_size     	int(15) COMMENT '总数量'  NOT NULL,
	start_time     	timestamp COMMENT '开始时间'  NULL,
	end_time       	timestamp COMMENT '结束时间'  NULL,
	status         	char(1) COMMENT '状态'  NOT NULL 
	)
COMMENT = '批处理记录表' ;	

CREATE TABLE cb_report_batch_cycle  ( 
	batch_id            	int(15) COMMENT '批处理任务ID'  NOT NULL,
	batch_date          	int(15) COMMENT '当前跑批日期 yyyymmdd'  NOT NULL,
	last_batch_date     	int(15) COMMENT '上次跑批日期 yyyymmdd'  NOT NULL,
	last_last_batch_date	int(15) COMMENT '上上次跑批日期 yyyymmdd'  NOT NULL,
	start_time          	date COMMENT '批处理任务开始时间'  NULL,
	end_time            	date COMMENT '批处理结束时间'  NULL,
	status              	char(1) COMMENT '批处理任务状态'  NOT NULL 
	)
COMMENT = '批处理时间表' ;	

CREATE TABLE cb_report_batch_job  ( 
	job_id         	int(15) COMMENT 'job id'  NOT NULL,
	job_name       	varchar(64) COMMENT 'job name'  NOT NULL,
	input_file     	varchar(30) NOT NULL,
	input_file_type	char(1) NOT NULL,
	chunk_size     	int(15) COMMENT '每批次处理记录数'  NOT NULL,
	remark         	varchar(128) COMMENT '备注'  NULL 
	);
COMMENT = '批处理任务表' ;	
CREATE TABLE cb_report_batch_node  ( 
	node_id    	int(15) COMMENT '节点ID'  NOT NULL,
	node_name  	varchar(64) COMMENT '节点名字'  NOT NULL,
	node_ip    	varchar(32) COMMENT '节点IP'  NOT NULL,
	node_port  	int(15) NOT NULL,
	master_node	char(1) COMMENT '主节点'  NOT NULL,
	remark     	varchar(128) COMMENT '备注'  NULL 
	)
COMMENT = '批处理节点表' ;

CREATE TABLE cb_report_merchant_product  ( 
	id            	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_time   	datetime COMMENT '日期'  NOT NULL,
	client_id     	varchar(32) COMMENT '客户端ID'  NULL,
	product_id    	varchar(32) COMMENT '商品ID'  NULL,
	org_code      	varchar(16) COMMENT '商品机构码'  NULL,
	org_name      	varchar(64) COMMENT '商品机构名'  NULL,
	forg_code     	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name     	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code     	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name     	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code     	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name     	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code     	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name     	varchar(64) COMMENT '四级机构名'  NULL,
	type          	varchar(1) COMMENT '业务类型'  NULL,
	merchant_id   	varchar(32) COMMENT '商户号'  NULL,
	merchant_name 	varchar(64) COMMENT '商户名'  NULL,
	category      	varchar(64) COMMENT '类目'  NULL,
	category_name 	varchar(64) COMMENT '类目名称'  NULL,
	total_products	bigint(20) COMMENT '商户商品总数'  NULL,
	new_count     	bigint(20) COMMENT '新增商品总数'  NULL,
	sale_count    	bigint(20) COMMENT '销售商品总数'  NULL,
	sale_amount   	bigint(20) COMMENT '商品销售金额'  NULL,
	refund_amount 	bigint(20) COMMENT '退款金额'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '商户商品表' ;

CREATE TABLE cb_report_merchant_sale  ( 
	id                 	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_time        	datetime COMMENT '日期'  NOT NULL,
	client_id          	varchar(32) COMMENT '客户端ID'  NULL,
	merchant_id        	varchar(32) COMMENT '商户号'  NULL,
	merchant_name      	varchar(65) COMMENT '商户名'  NULL,
	merchant_types      varchar(64) COMMENT '商户类型'  NULL,
	org_code           	varchar(16) COMMENT '商户机构码'  NULL,
	forg_code          	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name          	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code          	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name          	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code          	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name          	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code          	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name          	varchar(64) COMMENT '四级机构名'  NULL,
	sale_product_number	bigint(20) COMMENT '商品销售数量'  NULL,
	sale_product_amount	bigint(20) COMMENT '商品总金额'  NULL,
	total_order        	bigint(20) COMMENT '订单总数'  NULL,
	total_amount       	bigint(20) COMMENT '订单总金额'  NULL,
	cash               	bigint(20) COMMENT '现金金额'  NULL,
	bank_point_amount  	bigint(20) COMMENT '银行积分金额'  NULL,
	fft_point_amount   	bigint(20) COMMENT '联盟积分金额'  NULL,
	refund_amount      	bigint(20) COMMENT '退款金额'  NULL,
	buy_trips          	bigint(20) COMMENT '购买人次'  NULL,
	order_type         	varchar(1) COMMENT '订单类型'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '商户销售表' ;

CREATE TABLE cb_report_order  ( 
	id              	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_date     	int(8) COMMENT '创建日期yyyymmdd'  NOT NULL,
	client_id       	varchar(32) COMMENT '客户端ID'  NOT NULL,
	sub_order_id    	varchar(32) COMMENT '子订单号'  NOT NULL,
	order_id        	varchar(32) COMMENT '大订单号'  NOT NULL,
	member_code     	bigint(20) COMMENT '会员编号'  NOT NULL,
	member_name     	varchar(64) COMMENT '会员名'  NULL,
	merchant_id     	varchar(32) COMMENT '商户号'  NOT NULL,
	merchant_name   	varchar(64) COMMENT '商户名'  NULL,
	org_code        	varchar(16) COMMENT '商户机构码'  NOT NULL,
	forg_code       	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name       	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code       	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name       	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code       	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name       	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code       	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name       	varchar(64) COMMENT '四级机构名'  NULL,
	order_time      	datetime COMMENT '下单日期'  NOT NULL,
	order_status    	varchar(1) COMMENT '订单状态'  NULL,
	order_type      	varchar(1) COMMENT '订单类型'  NULL,
	refund_state    	varchar(1) COMMENT '退款状态'  NULL,
	sub_order_amount	bigint(20) COMMENT '子订单金额'  NULL,
	real_price      	bigint(20) COMMENT '订单现金金额'  NULL,
	total_price     	bigint(20) COMMENT '订单总金额'  NULL,
	bank_point      	bigint(20) COMMENT '银行积分'  NULL,
	fft_point       	bigint(20) COMMENT '联盟积分'  NULL,
	point_rate      	varchar(16) COMMENT '积分比例'  NULL,
	product_id      	varchar(32) COMMENT '面对面商品ID'  NULL,
	pay_type      	 	varchar(1) COMMENT '支付方式'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '交易订单表' ;

CREATE TABLE cb_report_order_product  ( 
	id                 	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_date        	int(8) COMMENT '创建日期yyyymmdd'  NOT NULL,
	client_id          	varchar(32) COMMENT '客户端ID'  NOT NULL,
	product_id         	varchar(32) COMMENT '商品ID'  NULL,
	product_name       	varchar(64) COMMENT '商品名'  NULL,
	sub_order_id       	varchar(32) COMMENT '子订单号'  NOT NULL,
	order_id           	varchar(32) COMMENT '大订单号'  NOT NULL,
	member_code        	bigint(20) COMMENT '会员编号'  NOT NULL,
	merchant_id        	varchar(32) COMMENT '商户号'  NOT NULL,
	product_type       	varchar(1) COMMENT '商品类型'  NULL,
	delivery_money     	bigint(20) COMMENT '运费'  NOT NULL,
	money              	bigint(20) COMMENT '单价'  NOT NULL,
	quantity           	bigint(20) COMMENT '数量'  NOT NULL,
	vip_money          	bigint(20) COMMENT 'vip单价'  NOT NULL,
	vip_quantity       	bigint(20) COMMENT 'vip数量'  NOT NULL,
	order_status       	varchar(1) COMMENT '订单状态'  NULL,
	refund_state       	varchar(1) COMMENT '退款状态'  NULL,
	refund_quantity    	bigint(20) COMMENT '退款数量'  NOT NULL,
	vip_refund_quantity	bigint(20) COMMENT 'vip退款数量'  NOT NULL,
	PRIMARY KEY(id)
)
COMMENT = '订单商品表' ;

CREATE TABLE cb_report_order_refund  ( 
	id           	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_date  	int(8) COMMENT '创建日期yyyymmdd'  NOT NULL,
	client_id    	varchar(32) COMMENT '客户端ID'  NOT NULL,
	refund_id    	varchar(32) COMMENT '退款ID'  NOT NULL,
	sub_order_id 	varchar(32) NULL,
	order_id     	varchar(32) COMMENT '大订单号'  NOT NULL,
	order_type   	varchar(1) NULL,
	member_code  	bigint(20) COMMENT '会员编号'  NOT NULL,
	merchant_id  	varchar(32) NULL,
	refund_time  	datetime NULL,
	refund_amount	bigint(20) COMMENT '退款金额'  NULL,
	refund_point 	bigint(20) COMMENT '退还积分'  NULL,
	point_rate   	varchar(16) COMMENT '积分比例'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '订单退款表' ;

CREATE TABLE cb_report_sale_paymethod  ( 
	id            	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_time   	datetime COMMENT '日期'  NOT NULL,
	client_id     	varchar(32) COMMENT '客户端ID'  NULL,
	merchant_id   	varchar(32) COMMENT '商户号'  NULL,
	merchant_name 	varchar(64) COMMENT '商户名'  NULL,
	order_type    	varchar(1) COMMENT '订单类型'  NOT NULL,
	org_code      	varchar(16) COMMENT '商户机构码'  NULL,
	forg_code     	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name     	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code     	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name     	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code     	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name     	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code     	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name     	varchar(64) COMMENT '四级机构名'  NULL,
	payment_method	varchar(1) COMMENT '1.现金支付，2.联盟积分支付，3.银行积分支付，4.联盟积分+现金，5.银行积分+现金'  NULL,
	total_order   	bigint(20) COMMENT '订单数'  NULL,
	total_amount  	bigint(20) COMMENT '订单金额'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '订单支付方式表' ;

CREATE TABLE cb_report_sign_merchant  ( 
	id          	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_time 	datetime COMMENT '日期'  NOT NULL,
	type        	varchar(2) COMMENT '业务类型'  NULL,
	merchant_id 	varchar(32) COMMENT '商户号'  NULL,
	is_new      	int(11) COMMENT '是否新增  1.新增'  NULL,
	is_change   	int(11) COMMENT '是否动账   1.动账'  NULL,
	is_cancel   	int(11) COMMENT '是否解约   1.解约'  NULL,
	total_orders	bigint(20) COMMENT '订单总数'  NULL,
	total_amount	bigint(20) COMMENT '订单总金额'  NULL,
	client_id     	varchar(32) COMMENT '客户端ID'  NULL,
	org_code      	varchar(16) COMMENT '商户机构码'  NULL,
	org_name     	varchar(64) COMMENT '商户机构名'  NULL,
	forg_code     	varchar(16) COMMENT '一级机构号'  NULL,
	forg_name     	varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code     	varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name     	varchar(64) COMMENT '二级机构名'  NULL,
	torg_code     	varchar(16) COMMENT '三级机构号'  NULL,
	torg_name     	varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code     	varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name     	varchar(64) COMMENT '四级机构名'  NULL,	
	sign_user_name	varchar(64) COMMENT '签约人名'  NULL,
	user_org_code   varchar(16) COMMENT '签约人机构码'  NULL,
	user_org_name   varchar(64) COMMENT '签约人机构名'  NULL,
	user_forg_code  varchar(16) COMMENT '签约人一级机构号'  NULL,
	user_forg_name  varchar(64) COMMENT '签约人一级机构名'  NULL,
	user_sorg_code  varchar(16) COMMENT '签约人二级机构号'  NULL,
	user_sorg_name  varchar(64) COMMENT '签约人二级机构名'  NULL,
	user_torg_code  varchar(16) COMMENT '签约人三级机构号'  NULL,
	user_torg_name  varchar(64) COMMENT '签约人三级机构名'  NULL,
	user_lorg_code  varchar(16) COMMENT '签约人四级机构号'  NULL,
	user_lorg_name  varchar(64) COMMENT '签约人四级机构名'  NULL,	
	PRIMARY KEY(id)
)
COMMENT = '签约商户统计' ;

CREATE TABLE cb_report_sign_summary (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time datetime NOT NULL COMMENT '日期',
  client_id varchar(32) NOT NULL COMMENT '客户端',
  forg_code varchar(16) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(64) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(16) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(64) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(16) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(64) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(16) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(64) DEFAULT NULL COMMENT '四级机构名',
  sign_user_name varchar(64) DEFAULT NULL COMMENT '签约人名',
  total_merchants bigint(20) DEFAULT NULL COMMENT '商户总数',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='签约商户汇总';

CREATE TABLE cb_report_sign_summary_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time datetime NOT NULL COMMENT '日期',
  client_id varchar(32) NOT NULL COMMENT '客户端',
  forg_code varchar(16) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(64) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(16) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(64) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(16) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(64) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(16) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(64) DEFAULT NULL COMMENT '四级机构名',
  sign_user_name varchar(64) DEFAULT NULL COMMENT '签约人名',
  new_total_merchant bigint(20) DEFAULT NULL COMMENT '新增商户数',
  cancel_total_merchant bigint(20) DEFAULT NULL COMMENT '解约商户数',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='签约商户汇总流水';

CREATE TABLE cb_report_sign_type_summary (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time datetime NOT NULL COMMENT '日期',
  client_id varchar(32) NOT NULL COMMENT '客户端',
  forg_code varchar(16) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(64) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(16) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(64) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(16) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(64) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(16) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(64) DEFAULT NULL COMMENT '四级机构名',
  sign_user_name varchar(64) DEFAULT NULL COMMENT '签约人名',
  type varchar(1) DEFAULT NULL COMMENT '业务类型',
  total_merchants bigint(20) DEFAULT NULL COMMENT '商户总数',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='签约商户类型汇总';

CREATE TABLE cb_report_user (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time date DEFAULT NULL COMMENT '日期',
  client_id varchar(10) DEFAULT NULL COMMENT '客户端ID',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  bank_card_id char(2) DEFAULT NULL COMMENT '卡bin',
  user_id bigint(16) DEFAULT NULL COMMENT '用户号',
  login_id varchar(32) DEFAULT NULL COMMENT '用户登录名',
  user_name varchar(32) DEFAULT NULL COMMENT '客户姓名',
  mobile varchar(25) DEFAULT NULL COMMENT '注册手机号',
  reg_time date DEFAULT NULL COMMENT '注册日期',
  reg_type varchar(25) DEFAULT NULL COMMENT '注册类型. 如:手机,pc等',
  is_vip bit(1) DEFAULT NULL COMMENT '是否vip 1.vip 0.非vip',
  sign_time date DEFAULT NULL COMMENT '签约/解约时间',
  valid_status int(2) DEFAULT NULL COMMENT '1:签约 2:解约',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE cb_report_user_unique (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time date DEFAULT NULL COMMENT '日期',
  client_id varchar(10) DEFAULT NULL COMMENT '客户端ID',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  bank_card_id char(2) DEFAULT NULL COMMENT '卡bin',
  user_id bigint(16) DEFAULT NULL COMMENT '用户号',
  login_id varchar(32) DEFAULT NULL COMMENT '用户登录名',
  user_name varchar(32) DEFAULT NULL COMMENT '客户姓名',
  mobile varchar(25) DEFAULT NULL COMMENT '注册手机号',
  reg_time date DEFAULT NULL COMMENT '注册日期',
  reg_type varchar(25) DEFAULT NULL COMMENT '注册类型. 如:手机,pc等',
  is_vip bit(1) DEFAULT NULL COMMENT '是否vip 1.vip 0.非vip',
  sign_time date DEFAULT NULL COMMENT '签约/解约时间',
  valid_status int(2) DEFAULT NULL COMMENT '1:签约 2:解约',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='去重用户表';

CREATE TABLE cb_report_user_detail (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time date DEFAULT NULL COMMENT '日期',
  client_id varchar(10) DEFAULT NULL COMMENT '客户端ID',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  bank_card_id char(2) DEFAULT NULL COMMENT '卡bin',
  user_id bigint(16) DEFAULT NULL COMMENT '用户号',
  login_id varchar(32) DEFAULT NULL COMMENT '用户登录名',
  user_name varchar(32) DEFAULT NULL COMMENT '客户姓名',
  mobile varchar(25) DEFAULT NULL COMMENT '注册手机号',
  reg_time date DEFAULT NULL COMMENT '注册日期',
  reg_type varchar(25) DEFAULT NULL COMMENT '注册类型. 如:手机,pc等',
  is_vip bit(1) DEFAULT NULL COMMENT '是否vip 1.vip 0.非vip',
  sign_time date DEFAULT NULL COMMENT '签约/解约时间',
  valid_status int(2) DEFAULT NULL COMMENT '1:签约 2:解约',
  is_new varchar(1) DEFAULT NULL COMMENT '是否新增 1.为新增',
  is_change varchar(1) DEFAULT NULL COMMENT '是否动户 1为动户',
  total_order bigint(20) DEFAULT NULL COMMENT '订单总数',
  total_amount bigint(20) DEFAULT NULL COMMENT '订单总金额',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='用户详情';

CREATE TABLE cb_report_user_summary (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time date DEFAULT NULL COMMENT '日期',
  client_id varchar(32) DEFAULT NULL COMMENT '客户端ID',
  bank_card_id char(2) DEFAULT NULL COMMENT '卡Bin',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  total_user bigint(20) DEFAULT NULL COMMENT '用户总数',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='机构用户流水表';

CREATE TABLE cb_report_user_trans (
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  create_time date DEFAULT NULL COMMENT '日期',
  client_id varchar(10) DEFAULT NULL COMMENT '客户端ID',
  forg_code varchar(25) DEFAULT NULL COMMENT '一级机构号',
  forg_name varchar(25) DEFAULT NULL COMMENT '一级机构名',
  sorg_code varchar(25) DEFAULT NULL COMMENT '二级机构号',
  sorg_name varchar(25) DEFAULT NULL COMMENT '二级机构名',
  torg_code varchar(25) DEFAULT NULL COMMENT '三级机构号',
  torg_name varchar(25) DEFAULT NULL COMMENT '三级机构名',
  lorg_code varchar(25) DEFAULT NULL COMMENT '四级机构号',
  lorg_name varchar(25) DEFAULT NULL COMMENT '四级机构名',
  bank_card_id char(2) DEFAULT NULL,
  user_id bigint(16) DEFAULT NULL COMMENT '用户号',
  login_id varchar(32) DEFAULT NULL COMMENT '用户登录名',
  user_name varchar(32) DEFAULT NULL COMMENT '客户姓名',
  mobile varchar(25) DEFAULT NULL COMMENT '注册手机号',
  reg_time date DEFAULT NULL COMMENT '注册日期',
  reg_type varchar(25) DEFAULT NULL COMMENT '注册类型. 如:手机,pc等',
  is_vip bit(1) DEFAULT NULL COMMENT '是否vip 1.vip 0.非vip',
  sign_time date DEFAULT NULL COMMENT '签约/解约时间',
  valid_status int(2) DEFAULT NULL COMMENT '1:签约 2:解约',
  order_type varchar(1) DEFAULT NULL COMMENT '订单类型',
  total_order_number bigint(20) DEFAULT NULL COMMENT '订单数',
  total_order_amount bigint(20) DEFAULT NULL COMMENT '订单金额',
  total_product_number bigint(20) DEFAULT NULL COMMENT '购买商品数',
  total_product_amount bigint(20) DEFAULT NULL COMMENT '购买商品金额',
  total_refunds_amount bigint(20) DEFAULT NULL COMMENT '退款金额',
  total_point_number bigint(20) DEFAULT NULL COMMENT '积分支付笔数',
  total_point_amount bigint(20) DEFAULT NULL COMMENT '积分支付金额',
  total_quick_number bigint(20) DEFAULT NULL COMMENT '快捷支付笔数',
  total_quick_amount bigint(20) DEFAULT NULL COMMENT '快捷支付金额',
  total_film_number bigint(20) DEFAULT NULL COMMENT '贴膜卡支付笔数',
  total_film_amount bigint(20) DEFAULT NULL COMMENT '贴膜卡支付金额',
  total_point_film_number bigint(20) DEFAULT NULL COMMENT '积分+贴膜卡支付笔数',
  total_point_film_amount bigint(20) DEFAULT NULL COMMENT '积分+贴膜卡支付金额',
  total_point_quick_number bigint(20) DEFAULT NULL COMMENT '积分+快捷支付笔数',
  total_point_quick_amount bigint(20) DEFAULT NULL COMMENT '积分+快捷支付金额',
  PRIMARY KEY (id)
) DEFAULT CHARSET=utf8 COMMENT='用户交易表';

CREATE TABLE cb_report_merchant_outlet  ( 
	id            			bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_time   			datetime COMMENT '日期'  NOT NULL,
	client_id     			varchar(32) COMMENT '客户端ID'  NULL,
	org_code     			varchar(16) COMMENT '网点ID'  NULL,
	org_name     			varchar(64) COMMENT '网点名'  NULL,	
	org_address     		varchar(240) COMMENT '网点地址'  NULL,
	forg_code     			varchar(16) COMMENT '一级机构号'  NULL,
	forg_name     			varchar(64) COMMENT '一级机构名'  NULL,
	sorg_code     			varchar(16) COMMENT '二级机构号'  NULL,
	sorg_name     			varchar(64) COMMENT '二级机构名'  NULL,
	torg_code     			varchar(16) COMMENT '三级机构号'  NULL,
	torg_name     			varchar(64) COMMENT '三级机构名'  NULL,
	lorg_code     			varchar(16) COMMENT '四级机构号'  NULL,
	lorg_name     			varchar(64) COMMENT '四级机构名'  NULL,
	merchant_id   			varchar(32) COMMENT '商户ID'  NULL,
	merchant_name 			varchar(64) COMMENT '商户名'  NULL,
	merchant_outlet_id   	varchar(32) COMMENT '门店ID'  NULL,
	merchant_outlet_name 	varchar(64) COMMENT '门店名'  NULL,	
	new_outlet_count     	bigint(20) COMMENT '新增门店数'  NULL,
	cancel_outlet_count    	bigint(20) COMMENT '失效门店数'  NULL,
	disable_status		 	varchar(1) COMMENT '门店状态'  NULL,
	PRIMARY KEY(id)
) DEFAULT CHARSET=utf8 COMMENT = '商户门店表' ;

CREATE TABLE cb_report_product_summary  ( 
	id              	bigint(20) AUTO_INCREMENT COMMENT 'id'  NOT NULL,
	create_date     	int(8) COMMENT '创建日期yyyymmdd'  NOT NULL,
	client_id       	varchar(32) COMMENT '客户端ID'  NOT NULL,
	org_code        	varchar(16) COMMENT '商户机构码'  NOT NULL,
	forg_code       	varchar(16) COMMENT '一级机构号'  NULL,
	sorg_code       	varchar(16) COMMENT '二级机构号'  NULL,
	torg_code       	varchar(16) COMMENT '三级机构号'  NULL,
	lorg_code       	varchar(16) COMMENT '四级机构号'  NULL,
	total_products		bigint(20) COMMENT '商品总数'  NULL,
	PRIMARY KEY(id)
)
COMMENT = '商品统计表' ;

CREATE INDEX cb_report_sign_summary_idx1 ON cb_report_sign_summary (create_time, client_id, forg_code, sorg_code, torg_code, lorg_code, sign_user_name);
CREATE INDEX cb_report_sign_summary_detail_idx1 ON cb_report_sign_summary_detail (create_time, client_id, forg_code, sorg_code, torg_code, lorg_code);
CREATE INDEX cb_report_sign_type_summary_idx1 ON cb_report_sign_type_summary (create_time, client_id, forg_code, sorg_code, torg_code, lorg_code);
CREATE INDEX cb_report_merchant_product_idx1 ON cb_report_merchant_product(create_time, client_id, org_code, forg_code, sorg_code, torg_code, lorg_code);
CREATE INDEX cb_report_merchant_sale_idx1 ON cb_report_merchant_sale(create_time, client_id, org_code, forg_code, sorg_code, torg_code, lorg_code);
CREATE INDEX cb_report_sign_merchant_idx1 ON cb_report_sign_merchant(create_time, client_id, forg_code, sorg_code, torg_code, lorg_code, merchant_id);
CREATE INDEX cb_report_user_detail_idx2 ON cb_report_user_detail(forg_code, sorg_code, bank_card_id, user_id);
CREATE INDEX cb_report_sale_paymethod_idx1 ON cb_report_sale_paymethod (create_time, client_id, org_code, forg_code, sorg_code, torg_code, lorg_code);
CREATE INDEX cb_report_user_summary_idx1 ON cb_report_user_summary (client_id, forg_code, sorg_code);
CREATE INDEX cb_report_user_trans_idx1 ON cb_report_user_trans (create_time, forg_code, sorg_code);
CREATE INDEX cb_report_user_detail_idx1 ON cb_report_user_detail (create_time, client_id, forg_code, sorg_code);
CREATE INDEX cb_report_merchant_outlet_idx1 on cb_report_merchant_outlet (client_id,sorg_code,org_code,merchant_id);
CREATE INDEX cb_report_order_idx1 ON cb_report_order (client_id, merchant_id, member_code, create_date, order_type, order_status);
CREATE INDEX cb_report_order_refund_idx1 ON cb_report_order_refund (client_id, merchant_id, create_date, order_type);
CREATE INDEX cb_report_order_product_idx1 ON cb_report_order_product (client_id, merchant_id, create_date, product_type, product_id, member_code);
CREATE INDEX cb_report_product_summary_idx1	ON cb_report_product_summary(client_id, create_date);

/*
cb_report_batch_job
*/
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(1, 'TaskReportBankUser', 'cb_merchant', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(2, 'TaskReportSignSummary', 'cb_merchant', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(3, 'TaskReportSignSummaryDetail', 'cb_merchant', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(4, 'TaskReportSignTypeSummary', 'cb_merchant', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(5, 'TaskReportSignMerchant', 'cb_report_bank_user', 'R', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(6, 'TaskReportMerchantProduct', 'cb_product', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(7, 'TaskReportMerchantSale', 'cb_merchant', 'S', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(9, 'TaskReportUser', 'cb_report_bank_org', 'R', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(10, 'TaskReportUserDetail', 'cb_report_user', 'R', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(11, 'TaskReportUserTrans', 'cb_report_user', 'R', 1000, NULL);
INSERT INTO cb_report_batch_job(job_id, job_name, input_file, input_file_type, chunk_size, remark)
  VALUES(12, 'TaskReportUserSummary', 'cb_report_bank_org', 'R', 1000, '');

/*
cb_report_bank_org
*/
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(1, 'anhui', '01', '340000', '安徽省农村信用社', '340101', '合肥科技农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(2, 'anhui', '02', '340000', '安徽省农村信用社', '340102', '肥东农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(3, 'anhui', '03', '340000', '安徽省农村信用社', '340103', '肥西农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(4, 'anhui', '04', '340000', '安徽省农村信用社', '340104', '长丰农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(5, 'anhui', '72', '340000', '安徽省农村信用社', '341572', '庐江农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(6, 'anhui', '74', '340000', '安徽省农村信用社', '341574', '巢湖农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(7, 'anhui', '05', '340000', '安徽省农村信用社', '340205', '芜湖津盛农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(8, 'anhui', '06', '340000', '安徽省农村信用社', '340206', '繁昌农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(9, 'anhui', '07', '340000', '安徽省农村信用社', '340207', '南陵农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(10, 'anhui', '08', '340000', '安徽省农村信用社', '340208', '芜湖扬子农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(11, 'anhui', '73', '340000', '安徽省农村信用社', '341573', '无为农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(12, 'anhui', '09', '340000', '安徽省农村信用社', '340309', '固镇农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(13, 'anhui', '10', '340000', '安徽省农村信用社', '340310', '怀远农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(14, 'anhui', '11', '340000', '安徽省农村信用社', '340311', '蚌埠农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(15, 'anhui', '12', '340000', '安徽省农村信用社', '340312', '五河农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(16, 'anhui', '13', '340000', '安徽省农村信用社', '340413', '凤台农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(17, 'anhui', '14', '340000', '安徽省农村信用社', '340414', '淮南通商农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(18, 'anhui', '15', '340000', '安徽省农村信用社', '340415', '淮南淮河农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(19, 'anhui', '17', '340000', '安徽省农村信用社', '340517', '马鞍山农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(20, 'anhui', '76', '340000', '安徽省农村信用社', '341576', '和县农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(21, 'anhui', '75', '340000', '安徽省农村信用社', '341575', '含山农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(22, 'anhui', '18', '340000', '安徽省农村信用社', '340618', '淮北农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(23, 'anhui', '19', '340000', '安徽省农村信用社', '340619', '濉溪农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(24, 'anhui', '20', '340000', '安徽省农村信用社', '340720', '铜陵农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(25, 'anhui', '21', '340000', '安徽省农村信用社', '340721', '铜陵皖江农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(26, 'anhui', '22', '340000', '安徽省农村信用社', '340822', '安庆独秀农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(27, 'anhui', '23', '340000', '安徽省农村信用社', '340823', '怀宁农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(28, 'anhui', '24', '340000', '安徽省农村信用社', '340824', '枞阳农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(29, 'anhui', '25', '340000', '安徽省农村信用社', '340825', '桐城农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(30, 'anhui', '26', '340000', '安徽省农村信用社', '340826', '潜山农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(31, 'anhui', '27', '340000', '安徽省农村信用社', '340827', '太湖农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(32, 'anhui', '28', '340000', '安徽省农村信用社', '340828', '宿松农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(33, 'anhui', '29', '340000', '安徽省农村信用社', '340829', '望江农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(34, 'anhui', '30', '340000', '安徽省农村信用社', '340830', '岳西农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(35, 'anhui', '31', '340000', '安徽省农村信用社', '340931', '黄山屯溪农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(36, 'anhui', '32', '340000', '安徽省农村信用社', '340932', '黄山徽州农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(37, 'anhui', '33', '340000', '安徽省农村信用社', '340933', '黄山太平农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(38, 'anhui', '34', '340000', '安徽省农村信用社', '340934', '歙县农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(39, 'anhui', '35', '340000', '安徽省农村信用社', '340935', '休宁农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(40, 'anhui', '36', '340000', '安徽省农村信用社', '340936', '黟县农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(41, 'anhui', '37', '340000', '安徽省农村信用社', '340937', '祁门农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(42, 'anhui', '38', '340000', '安徽省农村信用社', '341038', '阜阳颍泉农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(43, 'anhui', '39', '340000', '安徽省农村信用社', '341039', '临泉县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(44, 'anhui', '40', '340000', '安徽省农村信用社', '341040', '太和县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(45, 'anhui', '41', '340000', '安徽省农村信用社', '341041', '颍上农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(46, 'anhui', '42', '340000', '安徽省农村信用社', '341042', '阜南县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(47, 'anhui', '43', '340000', '安徽省农村信用社', '341043', '界首农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(48, 'anhui', '44', '340000', '安徽省农村信用社', '341044', '阜阳颍东农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(49, 'anhui', '45', '340000', '安徽省农村信用社', '341045', '阜阳颍淮农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(50, 'anhui', '46', '340000', '安徽省农村信用社', '341146', '宿州农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(51, 'anhui', '47', '340000', '安徽省农村信用社', '341147', '灵璧农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(52, 'anhui', '48', '340000', '安徽省农村信用社', '341148', '泗县农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(53, 'anhui', '49', '340000', '安徽省农村信用社', '341149', '萧县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(54, 'anhui', '50', '340000', '安徽省农村信用社', '341150', '砀山县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(55, 'anhui', '51', '340000', '安徽省农村信用社', '341251', '滁州皖东农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(56, 'anhui', '52', '340000', '安徽省农村信用社', '341252', '天长农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(57, 'anhui', '53', '340000', '安徽省农村信用社', '341253', '来安农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(58, 'anhui', '54', '340000', '安徽省农村信用社', '341254', '全椒农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(59, 'anhui', '55', '340000', '安徽省农村信用社', '341255', '定远农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(60, 'anhui', '56', '340000', '安徽省农村信用社', '341256', '凤阳农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(61, 'anhui', '57', '340000', '安徽省农村信用社', '341257', '明光农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(62, 'anhui', '58', '340000', '安徽省农村信用社', '341358', '六安农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(63, 'anhui', '59', '340000', '安徽省农村信用社', '341359', '寿县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(64, 'anhui', '60', '340000', '安徽省农村信用社', '341360', '霍邱县农村信用合作联社');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(65, 'anhui', '61', '340000', '安徽省农村信用社', '341361', '舒城农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(66, 'anhui', '62', '340000', '安徽省农村信用社', '341362', '金寨农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(67, 'anhui', '63', '340000', '安徽省农村信用社', '341363', '霍山农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(68, 'anhui', '64', '340000', '安徽省农村信用社', '341364', '叶集农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(69, 'anhui', '65', '340000', '安徽省农村信用社', '341465', '宣城皖南农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(70, 'anhui', '66', '340000', '安徽省农村信用社', '341466', '郎溪农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(71, 'anhui', '67', '340000', '安徽省农村信用社', '341467', '广德农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(72, 'anhui', '68', '340000', '安徽省农村信用社', '341468', '宁国农村合作银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(73, 'anhui', '69', '340000', '安徽省农村信用社', '341469', '泾县农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(74, 'anhui', '70', '340000', '安徽省农村信用社', '341470', '绩溪农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(75, 'anhui', '71', '340000', '安徽省农村信用社', '341471', '旌德农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(76, 'anhui', '77', '340000', '安徽省农村信用社', '341677', '东至农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(77, 'anhui', '78', '340000', '安徽省农村信用社', '341678', '池州九华农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(78, 'anhui', '79', '340000', '安徽省农村信用社', '341679', '石台农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(79, 'anhui', '80', '340000', '安徽省农村信用社', '341680', '青阳农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(80, 'anhui', '81', '340000', '安徽省农村信用社', '341781', '涡阳农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(81, 'anhui', '82', '340000', '安徽省农村信用社', '341782', '蒙城农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(82, 'anhui', '83', '340000', '安徽省农村信用社', '341783', '亳州药都农村商业银行');
INSERT INTO cb_report_bank_org(id, client_id, bank_card_id, forg_code, forg_name, sorg_code, sorg_name)
  VALUES(83, 'anhui', '84', '340000', '安徽省农村信用社', '341784', '利辛农村商业银行');

/*
安徽菜单
*/
INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000047, 'anhui', '统计报表', false, 0, true, '', 'fa-calendar', '0', false, '', 47);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000048, 'anhui', '社区银行商户信息统计', false, 100000047, true, 'form_merchantInformation.html', '', '100000047', false, '/bank/report/merchant/detailList;/bank/report/merchant/trend;/bank/report/merchant/typePercent;/bank/report/merchant/bussinessPercent', 1);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000049, 'anhui', '社区银行商户签约人统计', false, 100000047, true, 'form_merchantContractors.html', '', '100000047', false, '/bank/report/merchantContract/detailList;/bank/report/merchantContract/rank;/bank/report/merchantContract/addPercent;/bank/report/merchantContract/addRank', 2);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000050, 'anhui', '社区银行用户统计', false, 100000047, true, 'form_userStatistics.html', '', '100000047', false, '/bank/report/user/userSummaryList;/bank/report/user/trend;/bank/report/user/userTradeTypePercent;/bank/report/user/userConsumeTypePercent', 3);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000051, 'anhui', '社区银行业务销售统计', false, 100000047, true, 'form_businessSales.html', '', '100000047', false, '/bank/report/bussiness/statisticList;/bank/report/bussiness/trend;/bank/report/bussiness/saleTypePercent;/bank/report/bussiness/payTypePercent', 4);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100000052, 'anhui', '社区银行商品统计', false, 100000047, true, 'form_product.html', '', '100000047', false, '/bank/report/product/saleDetailList;/bank/report/product/trend;/bank/report/product/typePercent;/bank/report/product/categoryTypePercent', 5);

/*
重庆菜单
*/
INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100001047, 'chongqing', '统计报表', false, 0, true, '', 'fa-calendar', '0', false, '', 47);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100001048, 'chongqing', '社区银行商户信息统计', false, 100001047, true, 'form_merchantInformation.html', '', '100001047', false, '/bank/report/merchant/detailList;/bank/report/merchant/trend;/bank/report/merchant/typePercent;/bank/report/merchant/bussinessPercent', 1);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100001049, 'chongqing', '社区银行商户签约人统计', false, 100001047, true, 'form_merchantContractors.html', '', '100001047', false, '/bank/report/merchantContract/detailList;/bank/report/merchantContract/rank;/bank/report/merchantContract/addPercent;/bank/report/merchantContract/addRank', 2);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100001051, 'chongqing', '社区银行业务销售统计', false, 100001047, true, 'form_businessSales.html', '', '100001047', false, '/bank/report/bussiness/statisticList;/bank/report/bussiness/trend;/bank/report/bussiness/saleTypePercent;/bank/report/bussiness/payTypePercent', 4);

INSERT INTO cb_bank_resource (id, client_id, resource_name, resource_type, parent_resource_id, status, resource_url, resource_icon, tree_path, is_delete, api, order_value) 
VALUES (100001052, 'chongqing', '社区银行商品统计', false, 100001047, true, 'form_product.html', '', '100001047', false, '/bank/report/product/saleDetailList;/bank/report/product/trend;/bank/report/product/typePercent;/bank/report/product/categoryTypePercent', 5);
  
/*
cb_report_batch_cycle
 */
INSERT INTO cb_report_batch_cycle (batch_id, batch_date, last_batch_date, last_last_batch_date, start_time, end_time, status) 
VALUES (1, 20141111, 20141110, 20141109, null, null, '0');
UPDATE cb_report_batch_cycle SET batch_date = 20141111, last_batch_date = 20141110, last_last_batch_date = 20141109, start_time = null, end_time = null, status = '0'

/*
cb_report_batch_node
*/
INSERT INTO cb_report_batch_node(node_id, node_name, node_ip, node_port, master_node, remark)
  VALUES(1, 'node 1', '10.43.1.9', 16101, 'S', NULL);
INSERT INTO cb_report_batch_node(node_id, node_name, node_ip, node_port, master_node, remark)
  VALUES(2, 'node 2', '10.43.1.105', 16101, 'M', NULL);