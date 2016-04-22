/**
 * 用户角色表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_user_role;
CREATE TABLE froadfft.fft_sys_user_role (
	user_id bigint(20) NOT NULL COMMENT '主键ID',
	role_id bigint(20) NOT NULL COMMENT '主键ID'
) DEFAULT CHARSET=utf8  COMMENT='用户角色';
