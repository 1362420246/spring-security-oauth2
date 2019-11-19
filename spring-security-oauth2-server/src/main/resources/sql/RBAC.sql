CREATE TABLE `tb_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父权限',
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `enname` varchar(64) NOT NULL COMMENT '权限英文名称',
  `url` varchar(255) NOT NULL COMMENT '授权路径',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='权限表';

CREATE TABLE `tb_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父角色',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `enname` varchar(64) NOT NULL COMMENT '角色英文名称',
  `description` varchar(200) DEFAULT NULL COMMENT '备注',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `tb_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  `permission_id` bigint(20) NOT NULL COMMENT '权限 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='角色权限表';

CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '注册手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '注册邮箱',
  `created` datetime NOT NULL,
  `updated` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `tb_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户 ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色 ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

INSERT INTO `tb_user` VALUES (1, 'admin', '$2a$10$kErNoAjde6BKnv6/54GRoe6Okn2r4YUH88N1Hl2SARcn9JRunux6a', '18610790414', '1362420246@qq.com', '2019-11-19 16:38:57', '2019-11-19 16:39:00');

INSERT INTO `tb_role` VALUES (1, 0, '超级管理员', 'admin', NULL, '2019-11-19 16:40:55', '2019-11-19 16:40:59');

INSERT INTO `tb_permission` VALUES (1, 0, '系统管理员', 'System', '/', NULL, '2019-11-19 16:42:10', '2019-11-19 16:42:11');
INSERT INTO `tb_permission` VALUES (38, 1, '用户管理', 'SystemUser', '/user/', NULL, '2019-11-19 16:43:04', '2019-11-19 16:43:08');
INSERT INTO `tb_permission` VALUES (39, 38, '查看用户', 'SystemUserVIew', '/user/view/', NULL, '2019-11-19 16:44:41', '2019-11-19 16:44:42');
INSERT INTO `tb_permission` VALUES (40, 38, '新增用户', 'SystemUserInsert', '/user/insert/', NULL, '2019-11-19 16:46:23', '2019-11-19 16:46:26');
INSERT INTO `tb_permission` VALUES (41, 38, '编辑用户', 'SystemUserUpdate', '/user/update/', NULL, '2019-11-19 16:47:30', '2019-11-19 16:47:33');
INSERT INTO `tb_permission` VALUES (42, 38, '删除用户', 'SystemUserDelete', '/user/delete/', NULL, '2019-11-19 16:48:34', '2019-11-19 16:48:36');

INSERT INTO `tb_role_permission` VALUES (1, 1, 1);
INSERT INTO `tb_role_permission` VALUES (3, 1, 38);
INSERT INTO `tb_role_permission` VALUES (4, 1, 39);
INSERT INTO `tb_role_permission` VALUES (5, 1, 40);
INSERT INTO `tb_role_permission` VALUES (6, 1, 41);
INSERT INTO `tb_role_permission` VALUES (7, 1, 42);

INSERT INTO `tb_user_role` VALUES (1, 1, 1);
