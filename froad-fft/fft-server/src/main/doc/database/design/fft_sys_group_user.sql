/**
 * 组角色表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_group_user;
CREATE TABLE froadfft.fft_sys_group_user (
	group_id bigint(20) NOT NULL COMMENT '组ID',
	user_id bigint(20) NOT NULL COMMENT '用户ID'
) DEFAULT CHARSET=utf8  COMMENT='组用户';
