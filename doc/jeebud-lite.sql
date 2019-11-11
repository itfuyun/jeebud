/*
SQLyog Enterprise v12.5.0 (64 bit)
MySQL - 5.7.18-20170830-log : Database - jeebud-lite
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`jeebud-lite` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `jeebud-lite`;

/*Table structure for table `qrtz_job` */

DROP TABLE IF EXISTS `qrtz_job`;

CREATE TABLE `qrtz_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT '' COMMENT '任务组名',
  `method_name` varchar(500) DEFAULT '' COMMENT '任务方法',
  `method_params` varchar(50) DEFAULT NULL COMMENT '方法参数',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（1允许 0禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（1正常 0暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`id`,`job_name`,`job_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='定时任务调度表';

/*Data for the table `qrtz_job` */

insert  into `qrtz_job`(`id`,`job_name`,`job_group`,`method_name`,`method_params`,`cron_expression`,`misfire_policy`,`concurrent`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values 
(1,'jeebudJob','测试任务','runTask','test','0/5 * * * * ? *','2','0','0','admin','2018-03-16 11:33:00',NULL,NULL,NULL);

/*Table structure for table `qrtz_job_log` */

DROP TABLE IF EXISTS `qrtz_job_log`;

CREATE TABLE `qrtz_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `method_name` varchar(500) DEFAULT NULL COMMENT '任务方法',
  `method_params` varchar(50) DEFAULT NULL COMMENT '方法参数',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5324 DEFAULT CHARSET=utf8 COMMENT='定时任务调度日志表';

/*Data for the table `qrtz_job_log` */

/*Table structure for table `sys_login_log` */

DROP TABLE IF EXISTS `sys_login_log`;

CREATE TABLE `sys_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `ip` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_login_log` */

/*Table structure for table `sys_operation_log` */

DROP TABLE IF EXISTS `sys_operation_log`;

CREATE TABLE `sys_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(255) DEFAULT NULL,
  `req_param` text,
  `req_method` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `op_type` varchar(32) DEFAULT NULL,
  `operator` varchar(64) DEFAULT NULL,
  `ip` varchar(32) DEFAULT NULL,
  `info` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_operation_log` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `code` varchar(128) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `type` int(4) DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL,
  `icon` varchar(128) DEFAULT NULL,
  `create_time` date DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`code`,`pid`,`url`,`type`,`sort_num`,`icon`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(1,'控制面板','',0,'javascript:;',0,10,'layui-icon-home',NULL,NULL,'2019-11-07 05:38:02','admin'),
(2,'欢迎页',NULL,1,'/welcome',0,10,NULL,NULL,NULL,NULL,NULL),
(4,'系统管理',NULL,0,'javascript:;',0,10,'layui-icon-set',NULL,NULL,NULL,NULL),
(5,'用户管理','p:sys:user:list',4,'/sys/user/pageList',0,10,NULL,NULL,NULL,NULL,NULL),
(6,'角色管理','p:sys:role:list',4,'/sys/role/pageList',0,10,NULL,NULL,NULL,NULL,NULL),
(7,'权限管理','p:sys:permission:list',4,'/sys/permission/pageList',0,10,NULL,NULL,NULL,NULL,NULL),
(8,'列表','i:sys:role:list',6,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(9,'修改','i:sys:role:update',6,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(10,'新增','i:sys:role:add',6,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(11,'删除','i:sys:role:delete',6,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(12,'列表','i:sys:user:list',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(13,'修改','i:sys:user:update',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(14,'新增','i:sys:user:add',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(15,'删除','i:sys:user:delete',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(16,'锁定','i:sys:user:lock',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(17,'列表','i:sys:permission:list',7,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(18,'修改','i:sys:permission:update',7,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(19,'新增','i:sys:permission:add',7,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(20,'删除','i:sys:permission:delete',7,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(21,'授权','i:sys:role:permission',6,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(22,'重置密码','i:sys:user:resetPwd',5,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(23,'操作日志','p:sys:log:operation:list',31,'/sys/log/operation/pageList',0,10,'',NULL,NULL,'2019-09-07 21:30:51','admin'),
(24,'列表','i:sys:log:operation:list',23,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(25,'登录日志','p:sys:log:login:list',31,'/sys/log/login/pageList',0,10,'',NULL,NULL,'2019-09-07 21:30:38','admin'),
(26,'列表','i:sys:log:login:list',25,NULL,1,10,NULL,NULL,NULL,NULL,NULL),
(28,'系统工具','',0,'javascript:;',0,1,'layui-icon-util','2019-09-07','admin','2019-11-07 05:30:54','admin'),
(31,'日志管理','',4,'javascript:;',0,10,'','2019-09-07','admin',NULL,NULL),
(32,'定时任务','p:quartz:job:list',28,'/quartz/job/pageList',0,10,'','2019-09-08','admin','2019-09-29 20:56:36','admin'),
(33,'列表','i:quartz:job:list',32,'',1,10,'','2019-09-08','admin',NULL,NULL),
(34,'新增','i:quartz:job:add',32,'',1,10,'','2019-09-08','admin',NULL,NULL),
(35,'删除','i:quartz:job:delete',32,'',1,10,'','2019-09-08','admin','2019-09-08 03:02:27','admin'),
(36,'修改','i:quartz:job:update',32,'',1,10,'','2019-09-08','admin',NULL,NULL),
(37,'执行','i:quartz:job:run',32,'',1,10,'','2019-09-08','admin',NULL,NULL),
(38,'修改状态','i:quartz:job:updateStatus',32,'',1,10,'','2019-09-08','admin',NULL,NULL),
(39,'任务日志','p:quartz:log:list',31,'/quartz/log/pageList',0,10,'','2019-09-08','admin',NULL,NULL),
(40,'列表','i:quartz:log:list',39,'',1,10,'','2019-09-08','admin',NULL,NULL),
(41,'清空','i:quartz:log:clear',39,'',1,10,'','2019-09-08','admin',NULL,NULL),
(42,'清空','i:sys:log:operation:clear',23,'',1,10,'','2019-09-08','admin',NULL,NULL),
(43,'清空','i:sys:log:login:clear',25,'',1,10,'','2019-09-08','admin',NULL,NULL),
(44,'代码生成','p:gen:table:list',28,'/gen/pageList',0,10,'','2019-09-09','admin',NULL,NULL),
(45,'列表','i:gen:table:list',44,'',1,10,'','2019-09-09','admin',NULL,NULL),
(46,'生成','i:gen:table:create',44,'',1,10,'','2019-09-09','admin',NULL,NULL),
(52,'介绍页','',1,'/introduction',0,10,'','2019-09-09','admin','2019-09-09 21:47:13','admin'),
(61,'WebSocket','',28,'/websocket',0,10,'','2019-09-24','admin','2019-09-24 08:02:24','admin'),
(62,'Markdown','',28,'/markdown',0,10,'','2019-10-11','admin','2019-10-11 03:58:09','admin'),
(63,'富文本','',28,'/editor',0,10,'','2019-10-11','admin',NULL,NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `remarks` varchar(500) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`remarks`,`create_time`,`create_by`,`update_time`,`update_by`) values 
(1,'系统管理员','系统管理员','2019-09-05 19:37:10',NULL,'2019-09-06 03:36:27','test'),
(4,'普通管理员','普通管理员','2019-09-05 19:37:08',NULL,NULL,NULL),
(9,'测试用户','测试用户','2019-09-05 19:31:13','admin','2019-10-12 08:35:01','admin');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=436 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`permission_id`) values 
(3,8,1),
(4,8,3),
(5,4,1),
(6,4,2),
(7,4,3),
(8,4,4),
(9,4,5),
(10,4,12),
(11,4,13),
(12,4,14),
(13,4,15),
(14,4,16),
(15,4,22),
(16,4,6),
(17,4,8),
(18,4,9),
(19,4,10),
(20,4,11),
(21,4,21),
(22,4,7),
(23,4,17),
(24,4,18),
(25,4,19),
(26,4,20),
(341,1,1),
(342,1,2),
(343,1,52),
(344,1,4),
(345,1,5),
(346,1,12),
(347,1,13),
(348,1,14),
(349,1,15),
(350,1,16),
(351,1,22),
(352,1,6),
(353,1,8),
(354,1,9),
(355,1,10),
(356,1,11),
(357,1,21),
(358,1,7),
(359,1,17),
(360,1,18),
(361,1,19),
(362,1,20),
(363,1,31),
(364,1,23),
(365,1,24),
(366,1,42),
(367,1,25),
(368,1,26),
(369,1,43),
(370,1,39),
(371,1,40),
(372,1,41),
(373,1,28),
(374,1,32),
(375,1,33),
(376,1,34),
(377,1,35),
(378,1,36),
(379,1,37),
(380,1,38),
(381,1,44),
(382,1,45),
(383,1,46),
(384,1,61),
(385,1,62),
(386,1,63),
(411,9,1),
(412,9,2),
(413,9,52),
(414,9,4),
(415,9,5),
(416,9,12),
(417,9,6),
(418,9,8),
(419,9,7),
(420,9,17),
(421,9,31),
(422,9,23),
(423,9,24),
(424,9,25),
(425,9,26),
(426,9,39),
(427,9,40),
(428,9,28),
(429,9,32),
(430,9,33),
(431,9,44),
(432,9,45),
(433,9,61),
(434,9,62),
(435,9,63);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(128) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `status` int(4) DEFAULT NULL COMMENT '状态',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `admin_flag` int(4) DEFAULT '0' COMMENT '超级管理员',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  `create_time` date DEFAULT NULL,
  `create_by` varchar(64) DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_by` varchar(64) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`status`,`name`,`admin_flag`,`role_id`,`create_time`,`create_by`,`update_time`,`update_by`,`avatar`,`email`,`mobile`,`profile`) values 
(7,'admin','e10adc3949ba59abbe56e057f20f883e',0,'超级管理员',1,1,'2019-09-05',NULL,'2019-11-10','admin','https://jeebud-lite.oss-cn-shenzhen.aliyuncs.com/20190907/97l9nb2swhh0l6phnvoz.png','15774124@qq.com','18866666666','一切都是浮云'),
(12,'test','e10adc3949ba59abbe56e057f20f883e',0,'测试人员',0,9,'2019-09-05','admin','2019-11-07','admin',NULL,'18888888888@qq.com','18888888888','一枚小鲜肉');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
