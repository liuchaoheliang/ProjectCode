create table `tb_login` (
    `l_id` varchar(36),
    `l_name` varchar(10) unique not null  comment '帐号',
    `l_pwd` varchar(32) not null  comment '密码 md5',
    `l_last_try_time` varchar(32) default null comment '最后尝试登录时间',
    `l_state` char(1) default '0' comment '帐号状态     0 正常 1 锁定',
    `l_login_fail_times` int(2) default 0 comment '连续错误次数',
    `l_unlock_time` varchar(32) default null comment '解锁时间',
    `create_time` varchar(32) default null comment '创建时间',
    `update_time` varchar(32) default null comment '更新时间',
    primary key  (`l_id`)
) default CHARSET=utf8;

create table `tb_userinfo` (
	`u_id` varchar(36),
	`l_id` varchar(36) comment '关联tb_login',
	`u_mail` varchar(64) unique comment '邮箱地址',
 	`u_mailactiva` char(1) default '0' comment '0-未激活,1-激活',
	`u_sex` char(1) not null default '0' comment '0-男,1-女',
	`u_nickname` varchar(32) not null default '新用户' comment '昵称',
	`u_type` varchar(2) not null default '01' comment '用户类型',
	`u_autograph` varchar(255) default '此人很懒，什么都没留下..' comment '个性签名',
  	`create_time` varchar(32) default null comment '创建时间',
  	`update_time` varchar(32) default null comment '更新时间',
	primary key  (`u_id`),
  	foreign key (`l_id`) references `tb_login`(`l_id`)    
) default CHARSET=utf8;

create table `tb_friend` (
	`f_id` varchar(36),
	`f_iid` varchar(36) comment '创建人 关联tb_info',
	`f_fiid`varchar(36) comment '好友 关联tb_info',
	`f_state` varchar(2) default '0' comment '0:启用 1:删除',
	`create_time` varchar(32) default null comment '创建时间',
    `update_time` varchar(32) default null comment '更新时间',
	primary key (`f_id`),
	foreign key (`f_iid`) references `tb_userinfo`(`u_id`),
	foreign key (`f_fiid`) references `tb_userinfo`(`u_id`)
) default CHARSET=utf8;