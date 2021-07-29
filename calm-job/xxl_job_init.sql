/*
 Navicat Premium Data Transfer

 Source Server         : 本地MYSQL数据库-123456
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : 127.0.0.1:3306
 Source Schema         : calm

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 25/07/2021 18:14:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for xxl_job_group
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_group`;
CREATE TABLE `xxl_job_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器AppName',
  `title` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行器名称',
  `address_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '执行器地址类型：0=自动注册、1=手动录入',
  `address_list` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行器地址列表，多地址逗号分隔',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_group
-- ----------------------------
INSERT INTO `xxl_job_group` VALUES (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL, '2021-07-18 14:41:31');
INSERT INTO `xxl_job_group` VALUES (2, 'calm-job-executor', 'calm-执行器', 0, NULL, '2021-07-18 14:41:31');

-- ----------------------------
-- Table structure for xxl_job_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_info`;
CREATE TABLE `xxl_job_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `author` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `alarm_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报警邮件',
  `schedule_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NONE' COMMENT '调度类型',
  `schedule_conf` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调度配置，值含义取决于调度类型',
  `misfire_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DO_NOTHING' COMMENT '调度过期策略',
  `executor_route_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器路由策略',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_block_strategy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '阻塞处理策略',
  `executor_timeout` int(11) NOT NULL DEFAULT 0 COMMENT '任务执行超时时间，单位秒',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE备注',
  `glue_updatetime` datetime(0) NULL DEFAULT NULL COMMENT 'GLUE更新时间',
  `child_jobid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子任务ID，多个逗号分隔',
  `trigger_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '调度状态：0-停止，1-运行',
  `trigger_last_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '上次调度时间',
  `trigger_next_time` bigint(13) NOT NULL DEFAULT 0 COMMENT '下次调度时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_info
-- ----------------------------
INSERT INTO `xxl_job_info` VALUES (1, 1, '测试任务1', '2018-11-03 22:21:31', '2018-11-03 22:21:31', 'XXL', '', 'CRON', '0 0 0 * * ? *', 'DO_NOTHING', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2018-11-03 22:21:31', '', 0, 0, 0);
INSERT INTO `xxl_job_info` VALUES (2, 2, '测试生成序列号', '2021-07-18 13:28:39', '2021-07-18 14:03:41', 'qjyn1314@163.com', 'qjyn1314@163.com', 'CRON', '0,5 * * * * ?', 'DO_NOTHING', 'FIRST', 'SequenceHandle', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', '2021-07-18 13:28:39', '', 0, 0, 0);

-- ----------------------------
-- Table structure for xxl_job_lock
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_lock`;
CREATE TABLE `xxl_job_lock`  (
  `lock_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '锁名称',
  PRIMARY KEY (`lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_lock
-- ----------------------------
INSERT INTO `xxl_job_lock` VALUES ('schedule_lock');

-- ----------------------------
-- Table structure for xxl_job_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log`;
CREATE TABLE `xxl_job_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `job_group` int(11) NOT NULL COMMENT '执行器主键ID',
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `executor_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器地址，本次执行的地址',
  `executor_handler` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务handler',
  `executor_param` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务参数',
  `executor_sharding_param` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行器任务分片参数，格式如 1/2',
  `executor_fail_retry_count` int(11) NOT NULL DEFAULT 0 COMMENT '失败重试次数',
  `trigger_time` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `trigger_code` int(11) NOT NULL COMMENT '调度-结果',
  `trigger_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调度-日志',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '执行-时间',
  `handle_code` int(11) NOT NULL COMMENT '执行-状态',
  `handle_msg` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行-日志',
  `alarm_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `I_trigger_time`(`trigger_time`) USING BTREE,
  INDEX `I_handle_code`(`handle_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log
-- ----------------------------
INSERT INTO `xxl_job_log` VALUES (1, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:28:59', 200, '任务触发类型：手动触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:28:59', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (2, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:30:05', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:30:05', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (3, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:31:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:31:00', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (4, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:31:05', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:31:05', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (5, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:32:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:32:00', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (6, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:32:05', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:32:05', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (7, 2, 2, 'http://127.0.0.1:10085/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:33:00', -1, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：手动录入<br>执行器-地址列表：[http://127.0.0.1:10085/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://127.0.0.1:10085/<br>code：-1<br>msg：null', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (8, 2, 2, 'http://127.0.0.1:10085/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:33:05', -1, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：手动录入<br>执行器-地址列表：[http://127.0.0.1:10085/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://127.0.0.1:10085/<br>code：-1<br>msg：null', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (9, 2, 2, 'http://127.0.0.1:10085/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:34:00', -1, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：手动录入<br>执行器-地址列表：[http://127.0.0.1:10085/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://127.0.0.1:10085/<br>code：-1<br>msg：null', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (10, 2, 2, 'http://127.0.0.1:10085/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:34:05', -1, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：手动录入<br>执行器-地址列表：[http://127.0.0.1:10085/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://127.0.0.1:10085/<br>code：-1<br>msg：null', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (11, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:35:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (12, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:35:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (13, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:36:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (14, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:36:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (15, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:37:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (16, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:37:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (17, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:38:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:38:00', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (18, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:38:05', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:38:05', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (19, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:39:00', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:39:00', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (20, 2, 2, 'http://192.168.0.115:9999/', 'sequenceHandle', '', NULL, 0, '2021-07-18 13:39:05', 200, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 13:39:05', 500, 'java.lang.NullPointerException\r\n	at com.calm.execute.sequence.SequenceHandle.execute(SequenceHandle.java:28)\r\n	at com.xxl.job.core.thread.JobThread.run(JobThread.java:163)\r\n', 2);
INSERT INTO `xxl_job_log` VALUES (21, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:40:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (22, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:40:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (23, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:41:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (24, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:41:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (25, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:42:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (26, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:42:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (27, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:43:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (28, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:43:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (29, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:44:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (30, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:44:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (31, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:45:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (32, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:45:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (33, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:46:00', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (34, 2, 2, NULL, 'sequenceHandle', '', NULL, 0, '2021-07-18 13:46:05', 500, '任务触发类型：Cron触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：null<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>调度失败：执行器地址为空<br><br>', NULL, 0, NULL, 2);
INSERT INTO `xxl_job_log` VALUES (35, 2, 2, 'http://192.168.0.115:9999/', 'SequenceHandle', '', NULL, 0, '2021-07-18 14:03:48', 200, '任务触发类型：手动触发<br>调度机器：192.168.0.115<br>执行器-注册方式：自动注册<br>执行器-地址列表：[http://192.168.0.115:9999/]<br>路由策略：第一个<br>阻塞处理策略：单机串行<br>任务超时时间：0<br>失败重试次数：0<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>触发调度<<<<<<<<<<< </span><br>触发调度：<br>address：http://192.168.0.115:9999/<br>code：200<br>msg：null', '2021-07-18 14:03:49', 200, '', 0);

-- ----------------------------
-- Table structure for xxl_job_log_report
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_log_report`;
CREATE TABLE `xxl_job_log_report`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trigger_day` datetime(0) NULL DEFAULT NULL COMMENT '调度-时间',
  `running_count` int(11) NOT NULL DEFAULT 0 COMMENT '运行中-日志数量',
  `suc_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行成功-日志数量',
  `fail_count` int(11) NOT NULL DEFAULT 0 COMMENT '执行失败-日志数量',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_trigger_day`(`trigger_day`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_log_report
-- ----------------------------
INSERT INTO `xxl_job_log_report` VALUES (1, '2021-07-16 16:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (2, '2021-07-15 16:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (3, '2021-07-14 16:00:00', 0, 0, 0, NULL);
INSERT INTO `xxl_job_log_report` VALUES (4, '2021-07-17 16:00:00', 0, 1, 34, NULL);

-- ----------------------------
-- Table structure for xxl_job_logglue
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_logglue`;
CREATE TABLE `xxl_job_logglue`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_id` int(11) NOT NULL COMMENT '任务，主键ID',
  `glue_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'GLUE类型',
  `glue_source` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'GLUE源代码',
  `glue_remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'GLUE备注',
  `add_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_registry
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_registry`;
CREATE TABLE `xxl_job_registry`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registry_group` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `registry_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `i_g_k_v`(`registry_group`, `registry_key`, `registry_value`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for xxl_job_user
-- ----------------------------
DROP TABLE IF EXISTS `xxl_job_user`;
CREATE TABLE `xxl_job_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限：执行器ID列表，多个逗号分割',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `i_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of xxl_job_user
-- ----------------------------
INSERT INTO `xxl_job_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
