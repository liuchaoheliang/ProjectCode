/**
 * 角色资源表设计
 */
DROP TABLE IF EXISTS froadfft.fft_sys_role_resource;
CREATE TABLE froadfft.fft_sys_role_resource (
	role_id bigint(20) NOT NULL COMMENT '角色ID',
	resource_id bigint(20) NOT NULL COMMENT '资源ID'
) DEFAULT CHARSET=utf8  COMMENT='角色资源';
