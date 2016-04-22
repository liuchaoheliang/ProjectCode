/**
 * 用户角色表设计
 */
DROP TABLE IF EXISTS `fft_sys_user_role`;
CREATE TABLE `fft_sys_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '主键ID',
  `role_id` bigint(20) NOT NULL COMMENT '主键ID'
) ENGINE=InnoDB AUTO_INCREMENT=100000000 DEFAULT CHARSET=utf8 COMMENT='用户角色';
