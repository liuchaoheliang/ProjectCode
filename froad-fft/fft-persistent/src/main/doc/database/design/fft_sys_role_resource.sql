/**
 * 角色资源表设计
 */
DROP TABLE IF EXISTS `fft_sys_role_resource`;
CREATE TABLE `fft_sys_role_resource` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `resource_id` bigint(20) NOT NULL COMMENT '资源ID'
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='角色资源';

