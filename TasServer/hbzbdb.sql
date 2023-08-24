/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50634
 Source Host           : localhost:3306
 Source Schema         : hbzbdb

 Target Server Type    : MySQL
 Target Server Version : 50634
 File Encoding         : 65001

 Date: 10/07/2020 18:05:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for announce
-- ----------------------------
DROP TABLE IF EXISTS `announce`;
CREATE TABLE `announce`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告uid',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告标题',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告类型（1招标公告，2资格预审公告，9其他）',
  `attribute` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告性质（1正常公告、2变更公告、9其他）',
  `orgin_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原公告uid',
  `media` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布媒体',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '公告内容文本',
  `section_uids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联标段uids',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `publish` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '发布状态',
  `publish_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发布时间',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `agency_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态（编辑中，待审核，已审核，已退回，已发布）',
  `approver_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公告发布责任人',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人id',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of announce
-- ----------------------------
INSERT INTO `announce` VALUES (15, 'a44d7a2b7c0334fdb897bb187fbdcddb7', 'test公告', '采购公告', '正常公告', '', 'E招冀成,河北省政府采购网', '<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>\n<p>test公告test公告test公告test公告test公告test公告</p>', '46614a8c6b894cd8beda65e7451a42fe,043445bfc575473abfc57bb2e6608e68', '', '未发布', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 11:19:08', '2020-06-28 15:49:09');
INSERT INTO `announce` VALUES (16, 'a9653453590bf41e499ed11b9c7203b52', 'test公告---变更公告', '采购公告', '变更公告', 'a44d7a2b7c0334fdb897bb187fbdcddb7', '河北省公共资源交易中心,河北省政府采购网', '<p>test公告---变更公告test公告---变更公告test公告---变更公告test公告---变更公告test公告---变更公告</p>', '46614a8c6b894cd8beda65e7451a42fe,043445bfc575473abfc57bb2e6608e68', '', '已发布', '2020-06-28 17:35:48', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'CHANGE', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 17:47:46', '2020-06-28 17:12:09');
INSERT INTO `announce` VALUES (17, 'a14d345efd7434691801a1136e356de1f', 'test公告---变更公告2', '采购公告', '变更公告', 'a9653453590bf41e499ed11b9c7203b52', 'E招冀成', '<p>test公告---变更公告2test公告---变更公告2test公告---变更公告2test公告---变更公告2</p>', '46614a8c6b894cd8beda65e7451a42fe,043445bfc575473abfc57bb2e6608e68', '', '未发布', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 17:47:46', '2020-06-28 17:47:46');
INSERT INTO `announce` VALUES (20, 'a787da2f2dbe84ff986c5c545bff51824', 't1公告', '采购公告', '正常公告', NULL, 'E招冀成', '<p>t1公告t1公告t1公告</p>', '69e70d7c6ebe47d09c803816433eb7bd', '', '未发布', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:40:27', '2020-06-30 14:40:27');

-- ----------------------------
-- Table structure for announce_material
-- ----------------------------
DROP TABLE IF EXISTS `announce_material`;
CREATE TABLE `announce_material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `announce_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属公告uid',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材料名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材料电子件',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of announce_material
-- ----------------------------
INSERT INTO `announce_material` VALUES (2, 'p22045793-427d-4f38-9b63-08dbc96a9fc7', '委托合同', 'c9ffe0cbaba94e058efaccf74d310684.pdf', '698e0138-b8ef-42a2-aac1-5ba16285f56c', NULL, '2020-01-21 17:14:42');
INSERT INTO `announce_material` VALUES (3, 'a44d7a2b7c0334fdb897bb187fbdcddb7', '3.5M大文件.pdf', '3cccd9a4e60a4a2b9e2c8224efcfcc48.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 16:20:36', '2020-06-28 16:20:36');

-- ----------------------------
-- Table structure for approval_record
-- ----------------------------
DROP TABLE IF EXISTS `approval_record`;
CREATE TABLE `approval_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核项（project项目,tenderproject招标项目,announce公告,place场地）',
  `item_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核项uid',
  `step` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '步骤（提交审核、转审核、审核通过、审核不通过）',
  `opinion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理意见',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理人姓名',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '处理人uid',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of approval_record
-- ----------------------------
INSERT INTO `approval_record` VALUES (1, '项目', 'p22045793-427d-4f38-9b63-08dbc96a9fc7', '审核', NULL, 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-01-21 16:37:26');
INSERT INTO `approval_record` VALUES (2, '项目', 'p22045793-427d-4f38-9b63-08dbc96a9fc7', '审核', 'EDIT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-01-21 17:03:47');
INSERT INTO `approval_record` VALUES (3, '项目', 'p4a835869b3424dd996b3e6834a620fc7', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 13:36:35');
INSERT INTO `approval_record` VALUES (4, '招标项目', 't8c19f2f81ac4422cba52e880b0a068bf', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 17:10:46');
INSERT INTO `approval_record` VALUES (5, '招标项目', 't8c19f2f81ac4422cba52e880b0a068bf', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 17:10:48');
INSERT INTO `approval_record` VALUES (6, '分包', 's1da168e4271949a9ba08fb2e88a01b4c', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:11');
INSERT INTO `approval_record` VALUES (7, '分包', 's1da168e4271949a9ba08fb2e88a01b4c  ', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14');
INSERT INTO `approval_record` VALUES (8, '公告', 'ac645b2ad212f4d04ba888a4ae2801dd8', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-05 16:38:13');
INSERT INTO `approval_record` VALUES (9, '公告', 'ac645b2ad212f4d04ba888a4ae2801dd8', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-05 16:38:14');
INSERT INTO `approval_record` VALUES (10, '交易文件', 'd7783f9edbb9742849535bc5d8072741a', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-06 15:12:11');
INSERT INTO `approval_record` VALUES (11, '邀请函', 'a9a8ee94cc94d4c958eeeb0495ce364bc', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-07 11:11:34');
INSERT INTO `approval_record` VALUES (12, '邀请投标人', 'i6bb6382337574191a4bb69583d166081', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-07 14:56:07');
INSERT INTO `approval_record` VALUES (13, '项目', 'p475172c5179a42eab84f662395259e66', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 16:47:15');
INSERT INTO `approval_record` VALUES (14, '项目', 'p475172c5179a42eab84f662395259e66', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 16:47:17');

-- ----------------------------
-- Table structure for bid_file
-- ----------------------------
DROP TABLE IF EXISTS `bid_file`;
CREATE TABLE `bid_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段uid',
  `bidder_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投标人uid',
  `bidder_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标人名称',
  `origin_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标文件',
  `decrypt_pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解密密码',
  `uploaded_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '上传完成时间',
  `decrypt_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解密文件',
  `decrypt_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解密时间',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人ip',
  `creator_mac` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人mac地址',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bid_file
-- ----------------------------
INSERT INTO `bid_file` VALUES (1, 's1da168e4271949a9ba08fb2e88a01b4c', '1', NULL, NULL, NULL, NULL, '2222', NULL, NULL, NULL, NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14', '2020-02-04 17:59:37');
INSERT INTO `bid_file` VALUES (2, 's4d8f31cc5da4493cb64943916deb45ba', '2', NULL, NULL, NULL, NULL, '2222', NULL, '', NULL, NULL, '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `bid_file` VALUES (4, 's41eeaab1f07249f2975b0ab54b30b2b2', '3', NULL, NULL, NULL, NULL, '2222', NULL, 'xxx', NULL, NULL, '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:02:47');
INSERT INTO `bid_file` VALUES (5, 'scbdfa952a0db4b8b99b20cd600f8c181', '4', NULL, NULL, NULL, NULL, '333', NULL, 'xxx', NULL, NULL, '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `bid_file` VALUES (6, 's7dacf11e0bea4262969f3c8a9122b25f', '666', '2020-05-09 00:00:00', NULL, NULL, '2020-05-31 00:00:00', '6666', NULL, '', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 17:03:13', '2020-02-13 16:03:35');
INSERT INTO `bid_file` VALUES (10, '2d3f5da4b9e94574af1af7fe07c58fd0', '6', NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:41:29');
INSERT INTO `bid_file` VALUES (11, 'ef14c5cd5fca45c5ba11a64f04cb2905', '911023123123123', '河北投标单位', '1.tb', '123456', '2020-05-25 01:20:00', '1.td.pdf', '2020-05-28 10:10:20', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `bid_file` VALUES (12, 'ef14c5cd5fca45c5ba11a64f04cb2905', 'xxxx911023123123123', '投标测试采购单位2', '2.tb', '456789', '2020-05-26 10:00:09', '2.tb.pdf', '2020-05-28 10:15:20', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `bid_file` VALUES (13, 'ef14c5cd5fca45c5ba11a64f04cb2905', '3333xx000000000123', '河北测试投标采购单位TTT', '3.tb', '789123', '2020-05-27 22:10:20', '', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 10:16:46', '2020-04-26 17:28:45');

-- ----------------------------
-- Table structure for bid_record
-- ----------------------------
DROP TABLE IF EXISTS `bid_record`;
CREATE TABLE `bid_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投标标段uid',
  `bidder_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标人uid',
  `bidder_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '投标人名称',
  `contact_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `bid_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标时间',
  `bid_price` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标报价（元）',
  `col1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用字段1',
  `col2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用字段2',
  `col3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用字段3',
  `col4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备用字段4',
  `fee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '已交工本费',
  `guarantee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '已交保证金',
  `sign_in` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否签到',
  `sign_in_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '签到时间',
  `decrypted` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否解密',
  `decrypted_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '解密时间',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of bid_record
-- ----------------------------
INSERT INTO `bid_record` VALUES (2, '205af479b38e4f0390693086116f47bc', '3333xxxx911023123123123', '河北测试投标单位333', '张三333', '13912311123', '2020-02-11 09:55:55', '120000', '30', '一级', NULL, NULL, '是', '否', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:55:55', '2020-01-23 10:42:40');
INSERT INTO `bid_record` VALUES (4, '205af479b38e4f0390693086116f47bc', '911023123123123', '河北投标单位', '张三', '13912348665', '2020-02-11 09:52:12', '110000', '40', '一级', NULL, NULL, '是', '是', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `bid_record` VALUES (5, '205af479b38e4f0390693086116f47bc', 'xxxx911023123123123', '投标测试采购单位2', '张三2', '13912348876', '2020-02-11 09:52:37', '105000', '28', '一级', NULL, NULL, '是', '是', '未签到', NULL, '否', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:37', '2020-02-11 09:52:37');
INSERT INTO `bid_record` VALUES (6, '205af479b38e4f0390693086116f47bc', '3333xx000000000123', '河北测试投标采购单位TTT', '张三00', '13912348877', '2020-02-13 08:54:32', '122000', '35', '一级', NULL, NULL, '否', '是', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 08:54:32', '2020-02-13 08:52:48');
INSERT INTO `bid_record` VALUES (7, 'b17bf185f4f04798a195c3594492ed44', '3333xxxx911023123123123', '河北测试投标单位333', '张三333', '13912311123', '2020-02-11 09:55:55', '120000', '30', '一级', NULL, NULL, '是', '否', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:55:55', '2020-01-23 10:42:40');
INSERT INTO `bid_record` VALUES (8, 'b17bf185f4f04798a195c3594492ed44', '911023123123123', '河北投标单位', '张三', '13912348665', '2020-02-11 09:52:12', '110000', '40', '一级', NULL, NULL, '是', '是', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `bid_record` VALUES (9, 'b17bf185f4f04798a195c3594492ed44', 'xxxx911023123123123', '投标测试采购单位2', '张三2', '13912348876', '2020-02-11 09:52:37', '105000', '28', '一级', NULL, NULL, '是', '是', '未签到', NULL, '否', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:37', '2020-02-11 09:52:37');
INSERT INTO `bid_record` VALUES (10, 'b17bf185f4f04798a195c3594492ed44', '3333xx000000000123', '河北测试投标采购单位TTT', '张三00', '13912348877', '2020-02-13 08:54:32', '122000', '35', '一级', NULL, NULL, '否', '是', '已签到', '2020-02-11 09:52:37', '是', '2020-02-11 09:52:37', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 08:54:32', '2020-02-13 08:52:48');

-- ----------------------------
-- Table structure for eval_criteria
-- ----------------------------
DROP TABLE IF EXISTS `eval_criteria`;
CREATE TABLE `eval_criteria`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sort_id` int(11) DEFAULT NULL COMMENT '排序id',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标段（包）uid',
  `eval_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标方法',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父类编码',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评分点',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `score_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '打分方式（符合性打分、直接打分、自动打分）',
  `min_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最低分',
  `max_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最高分',
  `total_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '总分',
  `basic_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '基准值公式',
  `deduct_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扣分公式',
  `to_page` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转页码',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 262 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eval_criteria
-- ----------------------------
INSERT INTO `eval_criteria` VALUES (78, 1, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '', '23', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:09', '2020-05-13 09:54:09');
INSERT INTO `eval_criteria` VALUES (79, 2, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '', '24', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:09', '2020-05-13 09:54:09');
INSERT INTO `eval_criteria` VALUES (80, 3, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '23', '25', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:09', '2020-05-13 09:54:09');
INSERT INTO `eval_criteria` VALUES (81, 4, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '23', '26', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:09', '2020-05-13 09:54:09');
INSERT INTO `eval_criteria` VALUES (82, 5, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '24', '27', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:09', '2020-05-13 09:54:09');
INSERT INTO `eval_criteria` VALUES (83, 1, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '25', '1589334892790', '资质证照', '', '符合性打分', '', '', '', '', '', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:54:52', '2020-05-13 09:54:52');
INSERT INTO `eval_criteria` VALUES (84, 1, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '26', '1589334917757', '投标文件', '投标文件', '符合性打分', '', '', '', '', '', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:55:17', '2020-05-13 09:55:17');
INSERT INTO `eval_criteria` VALUES (85, 1, 's7dacf11e0bea4262969f3c8a9122b25f', '最低评标价法', '27', '1589334983703', '投标报价', '投标报价投标报价', '自动打分', '', '', '80', 'A22f3B', 'avg(A1)', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-13 09:56:23', '2020-05-13 09:56:23');
INSERT INTO `eval_criteria` VALUES (110, 1, 'ef14c5cd5fca45c5ba11a64f04cb2905', '最低评标价法', '', '23', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:47:47', '2020-05-18 11:47:47');
INSERT INTO `eval_criteria` VALUES (111, 2, 'ef14c5cd5fca45c5ba11a64f04cb2905', '最低评标价法', '', '24', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:47:47', '2020-05-18 11:47:47');
INSERT INTO `eval_criteria` VALUES (112, 3, 'ef14c5cd5fca45c5ba11a64f04cb2905', '最低评标价法', '23', '25', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:47:47', '2020-05-18 11:47:47');
INSERT INTO `eval_criteria` VALUES (113, 4, 'ef14c5cd5fca45c5ba11a64f04cb2905', '最低评标价法', '23', '26', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:47:47', '2020-05-18 11:47:47');
INSERT INTO `eval_criteria` VALUES (114, 5, 'ef14c5cd5fca45c5ba11a64f04cb2905', '最低评标价法', '24', '27', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:47:47', '2020-05-18 11:47:47');
INSERT INTO `eval_criteria` VALUES (123, 1, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '', '1', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (124, 2, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '', '2', '详细评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (125, 3, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '', '3', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (126, 4, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '1', '4', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (127, 5, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '1', '5', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (128, 6, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '2', '6', '商务部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (129, 7, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '2', '7', '技术部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (130, 8, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '3', '8', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (131, 9, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '4', '9', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (132, 10, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '4', '10', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (133, 11, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '4', '11', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (134, 12, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '4', '12', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (135, 13, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '5', '13', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (136, 14, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '6', '14', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '0.00', '10.00', '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (137, 15, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '6', '15', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', '0.00', '10.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (138, 16, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '6', '16', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', '0.00', '4.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (139, 17, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '7', '17', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (140, 18, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '7', '18', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', '0.00', '13.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (141, 19, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '7', '19', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (142, 20, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '7', '20', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (143, 21, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '7', '21', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (144, 22, '127b77675f5d4bd8b131be67f9d2fdc9', '综合评分法', '8', '22', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '', '', '30.00', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:30', '2020-05-26 14:04:30');
INSERT INTO `eval_criteria` VALUES (146, 1, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '', '1', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (147, 2, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '', '2', '详细评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (148, 3, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '', '3', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (149, 4, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '1', '4', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (150, 5, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '1', '5', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (151, 6, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '2', '6', '商务部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (152, 7, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '2', '7', '技术部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (153, 8, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '3', '8', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (154, 9, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '4', '9', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (155, 10, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '4', '10', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (156, 11, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '4', '11', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (157, 12, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '4', '12', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (158, 13, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '5', '13', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (159, 14, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '6', '14', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '0.00', '10.00', '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (160, 15, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '6', '15', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', '0.00', '10.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (161, 16, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '6', '16', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', '0.00', '4.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (162, 17, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '7', '17', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (163, 18, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '7', '18', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', '0.00', '13.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (164, 19, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '7', '19', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (165, 20, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '7', '20', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (166, 21, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '7', '21', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (167, 22, 'b17bf185f4f04798a195c3594492ed44', '综合评分法', '8', '22', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '', '', '30.00', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-28 11:04:40', '2020-05-28 11:04:40');
INSERT INTO `eval_criteria` VALUES (217, 1, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '', '1', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (218, 2, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '', '2', '详细评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (219, 3, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '', '3', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (220, 4, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '1', '4', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (221, 5, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '1', '5', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (222, 6, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '2', '6', '商务部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (223, 7, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '2', '7', '技术部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (224, 8, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '3', '8', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (225, 9, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '4', '9', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (226, 10, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '4', '10', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (227, 11, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '4', '11', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (228, 12, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '4', '12', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (229, 13, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '5', '13', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (230, 14, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '6', '14', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '0.00', '10.00', '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (231, 15, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '6', '15', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', '0.00', '10.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (232, 16, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '6', '16', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', '0.00', '4.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (233, 17, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '7', '17', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (234, 18, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '7', '18', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', '0.00', '13.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (235, 19, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '7', '19', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (236, 20, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '7', '20', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (237, 21, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '7', '21', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (238, 22, '423e0526b2e542e789ed3a8037fbc846', '综合评分法', '8', '22', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '', '', '30.00', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 16:41:52', '2020-06-15 16:41:52');
INSERT INTO `eval_criteria` VALUES (239, 1, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '', '1', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (240, 2, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '', '2', '详细评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (241, 3, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '', '3', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (242, 4, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '1', '4', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (243, 5, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '1', '5', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (244, 6, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '2', '6', '商务部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (245, 7, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '2', '7', '技术部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (246, 8, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '3', '8', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (247, 9, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '4', '9', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (248, 10, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '4', '10', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (249, 11, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '4', '11', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (250, 12, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '4', '12', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (251, 13, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '5', '13', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (252, 14, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '6', '14', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '0.00', '10.00', '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (253, 15, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '6', '15', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', '0.00', '10.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (254, 16, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '6', '16', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', '0.00', '4.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (255, 17, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '7', '17', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (256, 18, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '7', '18', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', '0.00', '13.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (257, 19, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '7', '19', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', '0.00', '8.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (258, 20, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '7', '20', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (259, 21, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '7', '21', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', '0.00', '7.00', NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (260, 22, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '8', '22', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '', '', '30.00', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:04', '2020-06-24 16:35:04');
INSERT INTO `eval_criteria` VALUES (261, 1, '46614a8c6b894cd8beda65e7451a42fe', '综合评分法', '4', '1592987719229', '1', '1', '符合性打分', '1', '1', '1', '1', '1', '1', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:35:19', '2020-06-24 16:35:19');

-- ----------------------------
-- Table structure for eval_criteria_old
-- ----------------------------
DROP TABLE IF EXISTS `eval_criteria_old`;
CREATE TABLE `eval_criteria_old`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sort_id` int(11) DEFAULT NULL COMMENT '排序id',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属标段（包）uid',
  `eval_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标方法',
  `category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '一级类',
  `sub_category` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '二级类',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '打分项',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `score_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '打分方式（直接打分、自动打分）',
  `total_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '总分',
  `min_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最低分',
  `max_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最高分',
  `base_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '基准值公式',
  `deduct_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扣分公式',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eval_criteria_old
-- ----------------------------
INSERT INTO `eval_criteria_old` VALUES (1, NULL, NULL, '综合评分法', '初步评审', '资格评审', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (2, NULL, NULL, '综合评分法', '初步评审', '资格评审', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (3, NULL, NULL, '综合评分法', '初步评审', '资格评审', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (4, NULL, NULL, '综合评分法', '初步评审', '资格评审', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (5, NULL, NULL, '综合评分法', '初步评审', '符合性评审', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (6, NULL, NULL, '综合评分法', '详细评审', '商务部分', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '', '0.00', '10.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (7, NULL, NULL, '综合评分法', '详细评审', '商务部分', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', NULL, '0.00', '10.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (8, NULL, NULL, '综合评分法', '详细评审', '商务部分', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', NULL, '0.00', '4.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (9, NULL, NULL, '综合评分法', '详细评审', '技术部分', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', NULL, '0.00', '8.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (10, NULL, NULL, '综合评分法', '详细评审', '技术部分', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', NULL, '0.00', '13.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (11, NULL, NULL, '综合评分法', '详细评审', '技术部分', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', NULL, '0.00', '8.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (12, NULL, NULL, '综合评分法', '详细评审', '技术部分', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', NULL, '0.00', '7.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (13, NULL, NULL, '综合评分法', '详细评审', '技术部分', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', NULL, '0.00', '7.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_old` VALUES (14, NULL, NULL, '综合评分法', '价格评审', '价格评分', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '30.00', '', '', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');

-- ----------------------------
-- Table structure for eval_criteria_template
-- ----------------------------
DROP TABLE IF EXISTS `eval_criteria_template`;
CREATE TABLE `eval_criteria_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sort_id` int(11) DEFAULT NULL COMMENT '排序id',
  `eval_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标方法',
  `parent_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '父级编码',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评分点',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `score_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '打分方式（符合性打分、直接打分、自动打分）',
  `total_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '总分',
  `min_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最低分',
  `max_score` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最高分',
  `base_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '基准值公式',
  `deduct_formula` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扣分公式',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eval_criteria_template
-- ----------------------------
INSERT INTO `eval_criteria_template` VALUES (1, 1, '综合评分法', '', '1', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (2, 2, '综合评分法', '', '2', '详细评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (3, 3, '综合评分法', '', '3', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (4, 4, '综合评分法', '1', '4', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (5, 5, '综合评分法', '1', '5', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (6, 6, '综合评分法', '2', '6', '商务部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `eval_criteria_template` VALUES (7, 7, '综合评分法', '2', '7', '技术部分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `eval_criteria_template` VALUES (8, 8, '综合评分法', '3', '8', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `eval_criteria_template` VALUES (9, 9, '综合评分法', '4', '9', '营业执照', '营业执照原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (10, 10, '综合评分法', '4', '10', '资质证书', '资质证书原件或有效复印件', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (11, 11, '综合评分法', '4', '11', '授权委托书及身份证明', '投标人身份证及授权委托书原件（法定代表人本人无须提供授权委托书）', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (12, 12, '综合评分法', '4', '12', '其他相关材料', '其他相关材料', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (13, 13, '综合评分法', '5', '13', '招标文件符合性', '由评标委员会依据招标文件的规定，从投标文件的有效性、完整性和对招标文件的响应程度进行审查，以确定是否对招标文件的实质性要求作出响应。', '符合性打分', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (14, 14, '综合评分法', '6', '14', '投标人的综合实力', '根据投标企业综合实力，包括经营范围、人员、软硬件设施条件、财务情况等，进行综合评价：优的得7-10分、良的得3-6分、一般的得0-2分', '直接打分', '', '0.00', '10.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (15, 15, '综合评分法', '6', '15', '投标人业绩', '具有合同金额50万元（含）以上类似的财务核算系统建设的业绩，每有1份合同加0.5分，最多得10分。投标人业绩整项最高得10分。说明：投标人业绩是指2014年11月1日至投标截止时间前1日的业绩，以合同签订日期为准。投标人需提供合同复印件（包括签约时间、项目名称、服务内容、合同金额、合同签字页等关键内容）。', '直接打分', NULL, '0.00', '10.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (16, 16, '综合评分法', '6', '16', '资质', '1、ISO9001质量管理体系认证（1分）投标人具有有效期内的ISO9001认证得1分，否则得0分。2、计算机信息系统集成与服务资质（1分）投标人具有计算机信息系统集成与服务资质的得1分，否则得0分。3、CMMI3及以上认证证书（2分）软件开发原厂商具有CMMI3证书得1分，CMMI3以上证书得2分，其它情况得0分。', '直接打分', NULL, '0.00', '4.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (17, 17, '综合评分法', '7', '17', '项目需求分析，相应重点难点分析及应对措施', '根据投标人对于项目的定位、建设目标以及业务需求的理解和分析是否深入、详细、到位，响应是否完整、业务功能划分是否科学合理，是否有针对性等综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', NULL, '0.00', '8.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (18, 18, '综合评分法', '7', '18', '系统架构及功能设计', '设计方案应包含完整的系统架构设计、软件功能设计、数据库设计方案；从方案完整性、可操作性，架构图、流程图的合理性，总体设计原则与招标文件要求符合性综合比较打分。优的得11-13分，良的得6-10分，一般的得0-5分。', '直接打分', NULL, '0.00', '13.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (19, 19, '综合评分法', '7', '19', '安全保护方案', '从安全保护方案设计的科学性、合理性、针对性、可行性综合比较打分。优的得7-8分，良的得4-6分，一般的得0-3分。', '直接打分', NULL, '0.00', '8.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (20, 20, '综合评分法', '7', '20', '售后服务方案', '根据服务流程、服务制度、服务人员及组织保障措施等进行综合比较打分。优的得6-7分，良的得3-5分，一般的得0-2分。', '直接打分', NULL, '0.00', '7.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (21, 21, '综合评分法', '7', '21', '培训方案', '根据培训方案的合理性、可行性进行综合比较打分。优的得4分；良的得2-3分；一般的得0-1分。', '直接打分', NULL, '0.00', '7.00', NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (22, 22, '综合评分法', '8', '22', '投标报价', '评标基准价=通过初步评审的投标人评标价格之和/通过初步评审的投标人数量；投标人评标价格的偏差率δ=（投标人评标价格-评标基准价）/评标基准价×100%确定投标人报价得分如下：当δ=0时，得15分；当δ＞0时，则投标报价得分在15分的基础上，δ每增加1个百分点，则扣减0.2分，当δ＜0时，则投标报价得分在15分的基础上，δ每减少1个百分点，则扣减0.1分，最多扣减至10分；其他情形，按中间插入法计算投标报价得分。', '自动打分', '30.00', '', '', 'avg1([A1],[A2],[A3],[A4])', 'if([A1],[A2])', '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (23, 1, '最低评标价法', '', '23', '初步评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (24, 2, '最低评标价法', '', '24', '价格评审', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (25, 3, '最低评标价法', '23', '25', '资格评审', '', '', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (26, 4, '最低评标价法', '23', '26', '符合性评审', '', '', NULL, NULL, NULL, NULL, NULL, '1', '2020-04-29 15:50:00', '2020-04-29 15:50:00');
INSERT INTO `eval_criteria_template` VALUES (27, 5, '最低评标价法', '24', '27', '价格评分', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for eval_member
-- ----------------------------
DROP TABLE IF EXISTS `eval_member`;
CREATE TABLE `eval_member`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sort_id` int(11) NOT NULL COMMENT '序号',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段uid',
  `user_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户uid',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '姓名',
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户角色',
  `id_card_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '电话',
  `profession` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '专业类别',
  `work` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '工作单位',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态(EDIT编辑中、SUBMIT待认证、REJECT已退回、CERTIFIED已认证)',
  `vote` int(11) DEFAULT NULL COMMENT '得票数',
  `voted` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否已投票',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标组长',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eval_member
-- ----------------------------
INSERT INTO `eval_member` VALUES (1, 0, 's7dacf11e0bea4262969f3c8a9122b25f', '1', '杜思众', 'ROLE_AGENCY', '130312519654551636', '18603114558', '55663@qq.com', '石家庄市鹿泉区', NULL, 'CERTIFIED', NULL, NULL, NULL, '1', '2020-02-03 12:58:00', '2020-02-03 12:58:00');
INSERT INTO `eval_member` VALUES (9, 0, 'ef14c5cd5fca45c5ba11a64f04cb2905', '2', '老表', 'ROLE_TENDEREE', NULL, '13931139646', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:28', '2020-05-18 17:22:54');
INSERT INTO `eval_member` VALUES (10, 0, 'ef14c5cd5fca45c5ba11a64f04cb2905', '3', 'tommy', 'ROLE_USER', NULL, '13112345678', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-19 10:23:07', '2020-05-18 17:23:02');
INSERT INTO `eval_member` VALUES (11, 0, 'ef14c5cd5fca45c5ba11a64f04cb2905', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '张三', 'ROLE_EXPERT', NULL, '15825880235', NULL, '', NULL, NULL, NULL, NULL, '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:46', '2020-05-18 17:23:08');
INSERT INTO `eval_member` VALUES (12, 0, 'ef14c5cd5fca45c5ba11a64f04cb2905', '0c89b4f7-0265-45f9-84eb-4b34278331db', '杜代理', 'ROLE_EXPERT', NULL, '13131123123', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:23', '2020-05-18 17:23:10');
INSERT INTO `eval_member` VALUES (13, 0, 'ef14c5cd5fca45c5ba11a64f04cb2905', 'df08b1d9-b29b-4bc8-a65b-cd31e7d652da', '李四', 'ROLE_EXPERT', NULL, '18978552588', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 17:23:14', '2020-05-18 17:23:14');
INSERT INTO `eval_member` VALUES (14, 0, '2d3f5da4b9e94574af1af7fe07c58fd0', 'u428b238826764e50b674c3adc88e8b84', '嘟嘟', 'ROLE_AGENCY', NULL, '13131197776', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 17:23:14', '2020-05-18 17:23:14');
INSERT INTO `eval_member` VALUES (15, 0, '2d3f5da4b9e94574af1af7fe07c58fd0', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '张三', 'ROLE_EXPERT', NULL, '15825880235', NULL, '', NULL, NULL, NULL, NULL, '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:46', '2020-05-18 17:23:08');
INSERT INTO `eval_member` VALUES (16, 0, '2d3f5da4b9e94574af1af7fe07c58fd0', '0c89b4f7-0265-45f9-84eb-4b34278331db', '杜代理', 'ROLE_EXPERT', NULL, '13131123123', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:23', '2020-05-18 17:23:10');
INSERT INTO `eval_member` VALUES (17, 0, '2d3f5da4b9e94574af1af7fe07c58fd0', 'df08b1d9-b29b-4bc8-a65b-cd31e7d652da', '李四', 'ROLE_EXPERT', NULL, '18978552588', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 17:23:14', '2020-05-18 17:23:14');
INSERT INTO `eval_member` VALUES (18, 0, '2d3f5da4b9e94574af1af7fe07c58fd0', '2', '老表', 'ROLE_TENDEREE', NULL, '13931139646', NULL, '', NULL, NULL, NULL, NULL, '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:14:28', '2020-05-18 17:22:54');
INSERT INTO `eval_member` VALUES (19, 0, 'b17bf185f4f04798a195c3594492ed44', 'u428b238826764e50b674c3adc88e8b84', '嘟嘟', 'ROLE_AGENCY', NULL, '13131197776', '材料工程', '招标集团', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:01:18', '2020-05-26 14:01:18');
INSERT INTO `eval_member` VALUES (20, 0, 'b17bf185f4f04798a195c3594492ed44', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '张三', 'ROLE_EXPERT', NULL, '15825880235', '经济', '发改委', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:01:22', '2020-05-26 14:01:22');
INSERT INTO `eval_member` VALUES (21, 0, '127b77675f5d4bd8b131be67f9d2fdc9', 'u428b238826764e50b674c3adc88e8b84', '嘟嘟', 'ROLE_AGENCY', NULL, '13131197776', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:37', '2020-05-26 14:04:37');
INSERT INTO `eval_member` VALUES (22, 0, '127b77675f5d4bd8b131be67f9d2fdc9', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '张三', 'ROLE_EXPERT', NULL, '15825880235', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:40', '2020-05-26 14:04:40');
INSERT INTO `eval_member` VALUES (23, 0, '127b77675f5d4bd8b131be67f9d2fdc9', 'df08b1d9-b29b-4bc8-a65b-cd31e7d652da', '李四', 'ROLE_EXPERT', NULL, '18978552588', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:43', '2020-05-26 14:04:43');
INSERT INTO `eval_member` VALUES (24, 0, '127b77675f5d4bd8b131be67f9d2fdc9', 'd62aab7a-9e1c-45b9-928d-3a893a8a7606', '王五', 'ROLE_AGENCY', NULL, '15525447136', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-26 14:04:45', '2020-05-26 14:04:45');
INSERT INTO `eval_member` VALUES (25, 0, 'b17bf185f4f04798a195c3594492ed44', '0c89b4f7-0265-45f9-84eb-4b34278331db', '杜代理', 'ROLE_EXPERT', NULL, '13131123123', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-27 09:49:29', '2020-05-27 09:49:29');
INSERT INTO `eval_member` VALUES (26, 0, 'b17bf185f4f04798a195c3594492ed44', 'df08b1d9-b29b-4bc8-a65b-cd31e7d652da', '李四', 'ROLE_EXPERT', NULL, '18978552588', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-27 09:49:32', '2020-05-27 09:49:32');
INSERT INTO `eval_member` VALUES (28, 0, 'b17bf185f4f04798a195c3594492ed44', '2', '老表', 'ROLE_TENDEREE', NULL, '13931139646', NULL, '', NULL, NULL, 0, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-27 09:49:55', '2020-05-27 09:49:55');
INSERT INTO `eval_member` VALUES (29, 0, '423e0526b2e542e789ed3a8037fbc846', 'u428b238826764e50b674c3adc88e8b84', '嘟嘟', 'ROLE_AGENCY', NULL, '13131197776', NULL, '', NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:35:50', '2020-06-02 17:35:50');
INSERT INTO `eval_member` VALUES (30, 0, '423e0526b2e542e789ed3a8037fbc846', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '张三', 'ROLE_EXPERT', NULL, '15825880235', NULL, '', NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:35:53', '2020-06-02 17:35:53');
INSERT INTO `eval_member` VALUES (31, 0, '423e0526b2e542e789ed3a8037fbc846', '0c89b4f7-0265-45f9-84eb-4b34278331db', '杜代理', 'ROLE_EXPERT', NULL, '13131123123', NULL, '', NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:35:59', '2020-06-02 17:35:59');
INSERT INTO `eval_member` VALUES (32, 0, '46614a8c6b894cd8beda65e7451a42fe', 'u428b238826764e50b674c3adc88e8b84', '嘟嘟', 'ROLE_AGENCY', NULL, '13131197776', NULL, '', NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:43:04', '2020-06-24 16:43:04');
INSERT INTO `eval_member` VALUES (33, 0, '46614a8c6b894cd8beda65e7451a42fe', '0c89b4f7-0265-45f9-84eb-4b34278331db', '杜代理', 'ROLE_EXPERT', NULL, '13131123123', NULL, '', NULL, NULL, NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:43:07', '2020-06-24 16:43:07');

-- ----------------------------
-- Table structure for eval_record
-- ----------------------------
DROP TABLE IF EXISTS `eval_record`;
CREATE TABLE `eval_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `bidder_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `criteria_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `score` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of eval_record
-- ----------------------------
INSERT INTO `eval_record` VALUES (1, 'b17bf185f4f04798a195c3594492ed44', '3333xxxx911023123123123', '4', '9', '通过1', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `eval_record` VALUES (2, 'b17bf185f4f04798a195c3594492ed44', '911023123123123', '4', '9', '通过', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `eval_record` VALUES (3, 'b17bf185f4f04798a195c3594492ed44', '3333xxxx911023123123123', '4', '10', '通过1', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `eval_record` VALUES (4, 'b17bf185f4f04798a195c3594492ed44', '911023123123123', '4', '10', '通过', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `eval_record` VALUES (5, 'b17bf185f4f04798a195c3594492ed44', '3333xxxx911023123123123', '4', '11', '不通过1', '资质不符合', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `eval_record` VALUES (6, 'b17bf185f4f04798a195c3594492ed44', '911023123123123', '4', '11', '不通过', '证件信息有误', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');

-- ----------------------------
-- Table structure for invitation
-- ----------------------------
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE `invitation`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `section_uids` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段编号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '邀请函内容',
  `reply_deadline` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '回复截止时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（编辑中，待审核，已审核，已退回，已发出）',
  `send_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标邀请发出时间',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `approver_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人id',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of invitation
-- ----------------------------
INSERT INTO `invitation` VALUES (1, 'a9a8ee94cc94d4c958eeeb0495ce364bc', 's1da168e4271949a9ba08fb2e88a01b4c', '888', '777', '2020-02-15 15:30:30', 'REJECT', NULL, '1', '', 'u428b238826764e50b674c3adc88e8b84', '2020-02-07 11:11:34', '2020-02-07 11:08:45');
INSERT INTO `invitation` VALUES (2, 'it5900a2aa58df4ed8addffc127ccfc971', '46614a8c6b894cd8beda65e7451a42fe', 'test-1', '<p>test投标邀请test投标邀请test投标邀请test投标邀请test投标邀请</p>', '2020-07-31 00:00:00', 'EDIT', NULL, '033239197e1b40f8ab4890beced34722', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-07-01 11:56:16', '2020-07-01 11:56:16');

-- ----------------------------
-- Table structure for invitation_template
-- ----------------------------
DROP TABLE IF EXISTS `invitation_template`;
CREATE TABLE `invitation_template`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '模板内容',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '状态（正常，停用）',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '代理uid',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of invitation_template
-- ----------------------------
INSERT INTO `invitation_template` VALUES (1, 'a9a8ee94cc94d4c958eeeb0495ce364bc', '777', 'REJECT', '1', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-07 11:11:34', '2020-02-07 11:08:45');

-- ----------------------------
-- Table structure for invite_record
-- ----------------------------
DROP TABLE IF EXISTS `invite_record`;
CREATE TABLE `invite_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `invitation_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bidder_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `bidder_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `bidder_uniform_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `invite_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `invite_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reply_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `reply_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of invite_record
-- ----------------------------
INSERT INTO `invite_record` VALUES (1, 'i6bb6382337574191a4bb69583d166081', 'a9a8ee94cc94d4c958eeeb0495ce364bc', '444', '4444', NULL, NULL, NULL, NULL, '', 'REJECT', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-07 14:56:07', '2020-02-07 14:53:41');
INSERT INTO `invite_record` VALUES (23, '9487d6ee1964414187ae535ee8ef3aa1', 'it5900a2aa58df4ed8addffc127ccfc971', '57', '河北遵守交通规则公司', '123456789011567894', NULL, NULL, NULL, NULL, '待处理', '033239197e1b40f8ab4890beced34722', 'u428b238826764e50b674c3adc88e8b84', '2020-07-02 10:31:29', '2020-07-02 10:31:29');
INSERT INTO `invite_record` VALUES (25, 'b36f0f3e4ed549f8b464f97a74c39c31', 'it5900a2aa58df4ed8addffc127ccfc971', '56', '大禹治水', '123456789032165487', NULL, NULL, NULL, NULL, '已发出', '033239197e1b40f8ab4890beced34722', 'u428b238826764e50b674c3adc88e8b84', '2020-07-02 10:31:34', '2020-07-02 10:31:34');
INSERT INTO `invite_record` VALUES (26, 'a6ab90b0bd024fd8bbc028087570e11f', 'it5900a2aa58df4ed8addffc127ccfc971', '40', '北京瑞亿斯科技有限公司', '911101143063960790', NULL, NULL, NULL, NULL, '待处理', '033239197e1b40f8ab4890beced34722', 'u428b238826764e50b674c3adc88e8b84', '2020-07-02 10:31:50', '2020-07-02 10:31:50');

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `belong` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属项',
  `belong_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属项uid',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '材料名称',
  `origin_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原始名称',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名',
  `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件大小',
  `sha512` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件校验码',
  `hits` int(255) NOT NULL COMMENT '点击次数',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人姓名',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for op_record
-- ----------------------------
DROP TABLE IF EXISTS `op_record`;
CREATE TABLE `op_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作项',
  `item_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作项uid',
  `operate` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作内容（步骤）',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注（处理意见）',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '操作人（办理人员）',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作人uid（处理人uid）',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间（处理时间）',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间（收到时间）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of op_record
-- ----------------------------
INSERT INTO `op_record` VALUES (1, '项目', 'p22045793-427d-4f38-9b63-08dbc96a9fc7', '审核', NULL, 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-01-21 16:37:26');
INSERT INTO `op_record` VALUES (2, '项目', 'p22045793-427d-4f38-9b63-08dbc96a9fc7', '审核', 'EDIT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-01-21 17:03:47');
INSERT INTO `op_record` VALUES (3, '项目', 'p4a835869b3424dd996b3e6834a620fc7', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-04 13:36:35');
INSERT INTO `op_record` VALUES (4, '招标项目', 't8c19f2f81ac4422cba52e880b0a068bf', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-04 17:10:46');
INSERT INTO `op_record` VALUES (5, '招标项目', 't8c19f2f81ac4422cba52e880b0a068bf', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-04 17:10:48');
INSERT INTO `op_record` VALUES (6, '分包', 's1da168e4271949a9ba08fb2e88a01b4c', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-04 18:02:11');
INSERT INTO `op_record` VALUES (7, '分包', 's1da168e4271949a9ba08fb2e88a01b4c  ', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-04 18:02:14');
INSERT INTO `op_record` VALUES (8, '公告', 'ac645b2ad212f4d04ba888a4ae2801dd8', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-05 16:38:13');
INSERT INTO `op_record` VALUES (9, '公告', 'ac645b2ad212f4d04ba888a4ae2801dd8', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-05 16:38:14');
INSERT INTO `op_record` VALUES (10, '交易文件', 'd7783f9edbb9742849535bc5d8072741a', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-06 15:12:11');
INSERT INTO `op_record` VALUES (11, '邀请函', 'a9a8ee94cc94d4c958eeeb0495ce364bc', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-07 11:11:34');
INSERT INTO `op_record` VALUES (12, '邀请投标人', 'i6bb6382337574191a4bb69583d166081', '审核', 'REJECT', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-07 14:56:07');
INSERT INTO `op_record` VALUES (13, '项目', 'p475172c5179a42eab84f662395259e66', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-11 16:47:15');
INSERT INTO `op_record` VALUES (14, '项目', 'p475172c5179a42eab84f662395259e66', '审核', 'APPROVAL', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '', '2020-02-11 16:47:17');
INSERT INTO `op_record` VALUES (15, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '新增项目', '', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-12 17:00:55', '2020-02-12 17:00:55');
INSERT INTO `op_record` VALUES (16, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '更新项目', '', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-12 17:02:56', '2020-02-12 17:02:56');
INSERT INTO `op_record` VALUES (17, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '提交审核', 'aaaa', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-12 17:16:35', '2020-02-12 17:16:35');
INSERT INTO `op_record` VALUES (18, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '转审核', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-12 17:20:50', '2020-02-12 17:20:50');
INSERT INTO `op_record` VALUES (19, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '转审核', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-12 17:20:53', '2020-02-12 17:20:53');
INSERT INTO `op_record` VALUES (20, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '转审核', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-12 17:21:02', '2020-02-12 17:21:02');
INSERT INTO `op_record` VALUES (21, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '审核不通过', 'XXXXX', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-12 17:21:39', '2020-02-12 17:21:39');
INSERT INTO `op_record` VALUES (22, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '更新项目', '', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-12 17:22:35', '2020-02-12 17:22:35');
INSERT INTO `op_record` VALUES (23, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '提交审核', 'aaaa', 'jackson', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-12 17:22:39', '2020-02-12 17:22:39');
INSERT INTO `op_record` VALUES (24, '项目', 'pc55db71227104abdb535c27d0e39e9f6', '审核通过', 'XXXXX', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-12 17:22:51', '2020-02-12 17:22:51');
INSERT INTO `op_record` VALUES (25, '招标项目', 't783e04ab94e94f37a7c235977ad11321', '新增招标项目', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 11:27:49', '2020-02-13 11:27:49');
INSERT INTO `op_record` VALUES (26, '招标项目', 't783e04ab94e94f37a7c235977ad11321', '提交审核', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 11:31:06', '2020-02-13 11:31:06');
INSERT INTO `op_record` VALUES (27, '招标项目', 't783e04ab94e94f37a7c235977ad11321', '审核通过', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 11:31:37', '2020-02-13 11:31:37');
INSERT INTO `op_record` VALUES (28, '分包', 's4d8f31cc5da4493cb64943916deb45ba', '新增分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `op_record` VALUES (29, '分包', 'sea85280bf9934122ab0ba1923d4be317', '新增分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:58', '2020-02-13 15:57:58');
INSERT INTO `op_record` VALUES (30, '分包', 's41eeaab1f07249f2975b0ab54b30b2b2', '新增分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:47', '2020-02-13 16:02:47');
INSERT INTO `op_record` VALUES (31, '分包', 'scbdfa952a0db4b8b99b20cd600f8c181', '新增分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `op_record` VALUES (32, '分包', 's7dacf11e0bea4262969f3c8a9122b25f', '新增分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:03:35', '2020-02-13 16:03:35');
INSERT INTO `op_record` VALUES (33, '分包', 's7dacf11e0bea4262969f3c8a9122b25f', '更新分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:04:18', '2020-02-13 16:04:18');
INSERT INTO `op_record` VALUES (34, '分包', 's7dacf11e0bea4262969f3c8a9122b25f', '更新分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:04:21', '2020-02-13 16:04:21');
INSERT INTO `op_record` VALUES (35, '分包', 'sea85280bf9934122ab0ba1923d4be317', '删除分包', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:08:51', '2020-02-13 16:08:51');
INSERT INTO `op_record` VALUES (36, '招标项目', 't84c29e1db0134e5b990c337d92c1926f', '无须审核', 'aaaa', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:13:45', '2020-02-13 16:13:45');
INSERT INTO `op_record` VALUES (37, '交易文件', 'defc68d28ac7140ee8476ca69be462919', '新增交易文件', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-14 09:47:36', '2020-02-14 09:47:36');
INSERT INTO `op_record` VALUES (38, '交易文件', 'defc68d28ac7140ee8476ca69be462919', '更新交易文件', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-14 09:51:22', '2020-02-14 09:51:22');
INSERT INTO `op_record` VALUES (39, '交易文件', 'defc68d28ac7140ee8476ca69be462919', '提交审核', 'asdfasfasf', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-14 09:53:58', '2020-02-14 09:53:58');
INSERT INTO `op_record` VALUES (40, '交易文件', 'defc68d28ac7140ee8476ca69be462919', '审核通过', 'asdfasfasf', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-14 09:55:30', '2020-02-14 09:55:30');
INSERT INTO `op_record` VALUES (41, '公告', 'a57bbf1557240444f9860f614acd1b76c', '提交审核', '无备注', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:49:21', '2020-02-17 16:49:21');
INSERT INTO `op_record` VALUES (42, '公告', 'a57bbf1557240444f9860f614acd1b76c', '审核通过', '备注收拾收拾、', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:51:03', '2020-02-17 16:51:03');
INSERT INTO `op_record` VALUES (43, '公告', 'a57bbf1557240444f9860f614acd1b76c', '发布公告', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:51:58', '2020-02-17 16:51:58');
INSERT INTO `op_record` VALUES (44, '公告', 'a57bbf1557240444f9860f614acd1b76c', '发布公告', '', '13131197776', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:54:28', '2020-02-17 16:54:28');
INSERT INTO `op_record` VALUES (45, '项目', '69b24833975348949d0084a386b890e0', '删除项目', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-24 10:29:39', '2020-04-24 10:29:39');
INSERT INTO `op_record` VALUES (46, '标段（包）', 'de9a5bcf08cb4228b2e755728b57a130', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 10:23:23', '2020-04-26 10:23:23');
INSERT INTO `op_record` VALUES (47, '标段（包）', 'b2ea67318ecd407eaa75960be5b88151', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 10:26:09', '2020-04-26 10:26:09');
INSERT INTO `op_record` VALUES (48, '标段（包）', 'de9a5bcf08cb4228b2e755728b57a130', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:38:02', '2020-04-26 11:38:02');
INSERT INTO `op_record` VALUES (49, '标段（包）', 'b2ea67318ecd407eaa75960be5b88151', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:39:19', '2020-04-26 11:39:19');
INSERT INTO `op_record` VALUES (50, '标段（包）', '5639cd8958384f3b8009124c2bb721f3', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:39:52', '2020-04-26 11:39:52');
INSERT INTO `op_record` VALUES (51, '标段（包）', 'b2ea67318ecd407eaa75960be5b88151', '删除标段（包）', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:40:58', '2020-04-26 11:40:58');
INSERT INTO `op_record` VALUES (52, '标段（包）', '2d3f5da4b9e94574af1af7fe07c58fd0', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:29', '2020-04-26 11:41:29');
INSERT INTO `op_record` VALUES (53, '标段（包）', 'de9a5bcf08cb4228b2e755728b57a130', '删除标段（包）', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:34', '2020-04-26 11:41:34');
INSERT INTO `op_record` VALUES (54, '标段（包）', '5639cd8958384f3b8009124c2bb721f3', '删除标段（包）', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:37', '2020-04-26 11:41:37');
INSERT INTO `op_record` VALUES (55, '标段（包）', '127b77675f5d4bd8b131be67f9d2fdc9', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `op_record` VALUES (56, '标段（包）', 'd8e8f1f816494c10979b5502e833ef98', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `op_record` VALUES (57, '标段（包）', '2d3f5da4b9e94574af1af7fe07c58fd0', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:52:04', '2020-04-26 11:52:04');
INSERT INTO `op_record` VALUES (58, '标段（包）', '2d3f5da4b9e94574af1af7fe07c58fd0', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:55:45');
INSERT INTO `op_record` VALUES (59, '招标项目', '7ad3b53739d44c27865a00893ca54e5a', 'SUBMIT', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 16:44:11', '2020-04-26 16:44:11');
INSERT INTO `op_record` VALUES (60, '标段（包）', 'ef14c5cd5fca45c5ba11a64f04cb2905', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 17:28:45', '2020-04-26 17:28:45');
INSERT INTO `op_record` VALUES (61, '招标项目', '55c67c0ba3b24ec19d2376298a4048ec', 'SUBMIT', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 17:29:13', '2020-04-26 17:29:13');
INSERT INTO `op_record` VALUES (62, '标段（包）', 'e3bb2d4213dc417ba4a38c46d4cedd54', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:36:16', '2020-05-06 14:36:16');
INSERT INTO `op_record` VALUES (63, '标段（包）', 'e3bb2d4213dc417ba4a38c46d4cedd54', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:36:31', '2020-05-06 14:36:31');
INSERT INTO `op_record` VALUES (64, '标段（包）', '0d2d0144a5cd4a51a4e10f703890fd44', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:42:09', '2020-05-06 14:42:09');
INSERT INTO `op_record` VALUES (65, '标段（包）', '497dd69801d042cb825fc6da75f8b9aa', '新增/更新分包', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 15:05:33', '2020-05-06 15:05:33');
INSERT INTO `op_record` VALUES (66, '招标项目', '9bb8aec8d0d145e4aef69ea336c0a754', 'SUBMIT', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 15:10:02', '2020-05-06 15:10:02');
INSERT INTO `op_record` VALUES (67, '招标项目', '63188a7448fb4af3a114077f9f6eb459', 'SUBMIT', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 13:48:51', '2020-05-08 13:48:51');
INSERT INTO `op_record` VALUES (68, '招标项目', '63188a7448fb4af3a114077f9f6eb459', 'SUBMIT', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 14:00:07', '2020-05-08 14:00:07');
INSERT INTO `op_record` VALUES (69, '项目', '63188a7448fb4af3a114077f9f6eb459', 'APPROVAL', 'test', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 14:02:37', '2020-05-08 14:02:37');
INSERT INTO `op_record` VALUES (70, '项目', '9f99d84122444f2692b6c8403df3af83', 'SUBMIT', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）电泳仪等设备、荧光化学发光成像系统等设备招标', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:19:52', '2020-06-01 16:19:52');
INSERT INTO `op_record` VALUES (71, '项目', '9f99d84122444f2692b6c8403df3af83', 'APPROVAL', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）电泳仪等设备、荧光化学发光成像系统等设备招标', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 17:10:42', '2020-06-01 17:10:42');
INSERT INTO `op_record` VALUES (72, '项目', '9f99d84122444f2692b6c8403df3af83', 'APPROVAL', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）电泳仪等设备、荧光化学发光成像系统等设备招标', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-08 17:12:59', '2020-06-08 17:12:59');
INSERT INTO `op_record` VALUES (73, '项目', '48b5dca8891647d5b406792374db886e', 'SUBMIT', '1234', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 09:56:19', '2020-06-22 09:56:19');
INSERT INTO `op_record` VALUES (74, '项目', '48b5dca8891647d5b406792374db886e', 'APPROVAL', '1234', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 09:56:42', '2020-06-22 09:56:42');
INSERT INTO `op_record` VALUES (75, '标段', '2c1637c9d09c473381608e5be3e70c87', '删除标段（包）', '1', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 16:57:08', '2020-06-22 16:57:08');
INSERT INTO `op_record` VALUES (76, '公告', 'a9653453590bf41e499ed11b9c7203b52', '发布公告', '', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 17:35:48', '2020-06-28 17:35:48');
INSERT INTO `op_record` VALUES (77, '公告', 'a7d4310b9cae343bd89dc0e99833f04c1', '删除公告', 't1公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:34:49', '2020-06-30 14:34:49');
INSERT INTO `op_record` VALUES (78, '公告', 'af7d69658080b49e09eb2090076fc153a', '删除公告', 't1公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:34:53', '2020-06-30 14:34:53');
INSERT INTO `op_record` VALUES (79, '公告', 'a84349d7731284aa9932457c6ff83dc5c', '删除公告', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:52:34', '2020-06-30 14:52:34');
INSERT INTO `op_record` VALUES (80, '公告', 'a5ad169721dab437da6e5984715ac35d6', '删除公告', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购招标公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:52:37', '2020-06-30 14:52:37');
INSERT INTO `op_record` VALUES (81, '公告', 'a12fa6c570d4448f69b47c253ab7e54f7', '删除公告', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购招标公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:52:42', '2020-06-30 14:52:42');
INSERT INTO `op_record` VALUES (82, '公告', 'a19ce50887fac4b68a99f1d58d495b419', '删除公告', '11111', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:42', '2020-06-30 14:55:42');
INSERT INTO `op_record` VALUES (83, '公告', 'a2ab6c15ed86e4d4f9f6ed185f7921dab', '删除公告', '1111招标公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:45', '2020-06-30 14:55:45');
INSERT INTO `op_record` VALUES (84, '公告', 'a987d40d854a243a8a336f95343a3c093', '删除公告', '1111', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:47', '2020-06-30 14:55:47');
INSERT INTO `op_record` VALUES (85, '公告', 'a25f093c3e0024dd5bfc8f75cf2d89cd8', '删除公告', '6666招标公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:49', '2020-06-30 14:55:49');
INSERT INTO `op_record` VALUES (86, '公告', 'afa878b6456514f60839ec3bfd664ac04', '删除公告', '123123招标公告', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:51', '2020-06-30 14:55:51');
INSERT INTO `op_record` VALUES (87, '公告', 'aa2f9fbe7b0594cba99473291c8fed5ed', '删除公告', '123123', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 14:55:54', '2020-06-30 14:55:54');
INSERT INTO `op_record` VALUES (88, '公告', 'adfe17680f61a4b769b24616c5bf8c3f3', '删除公告', '11', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 15:02:17', '2020-06-30 15:02:17');
INSERT INTO `op_record` VALUES (89, '公告', 'a31c3d27f29c34c4c9db6a6d3b611e411', '删除公告', '11', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 15:02:20', '2020-06-30 15:02:20');
INSERT INTO `op_record` VALUES (90, '公告', 'a482a8d93566e4f98a9fd2932279f1d1e', '删除公告', 't1', '13131197776', 'u428b238826764e50b674c3adc88e8b84', '2020-06-30 15:04:57', '2020-06-30 15:04:57');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目uid',
  `serial_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目名称',
  `tenderee_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购单位uid',
  `tenderee_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '采购单位名称',
  `contact_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购联系人',
  `contact_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购联系地址',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购地址',
  `area` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所在辖区',
  `trade_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '采购方式（公开招标，邀请招标，竞争性谈判，竞争性磋商，询价，单一来源）',
  `trade_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购交易分类',
  `organize_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交易组织形式',
  `investment_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资项目统一代码',
  `investor_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投资主体性质',
  `is_PPP` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否PPP项目',
  `supervisor` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '监督部门',
  `resourcer` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '公共资源',
  `platform` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '推送平台',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购内容与范围及招标方案说明',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理uid',
  `agency_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理名称',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '搜索关键字',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态（编辑中，待审核，审核通过，审核不通过）',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `approver_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核人uid',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人id',
  `creator_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人（申报责任人）',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间（建立时间）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (5, 'de544b8701e044c884426d136420e98e', '11', '11', NULL, '11', '11', '11', '11', '河北省,石家庄市,长安区', '公开招标', '工程类', '1自行招标', '11', '政府投资', '1', '', '', '', '11', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '11 11 11 河北省,石家庄市,长安区 公开招标 工程类 1自行招标', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 09:30:47', '2020-04-23 14:08:32');
INSERT INTO `project` VALUES (6, '901c18f7b64e42e3a746503a526eed28', '12', '12', NULL, '12', '', '', '', '河北省,石家庄市,桥东区', '邀请招标', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', NULL, 'SUBMIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-23 14:46:06', '2020-04-23 14:44:15');
INSERT INTO `project` VALUES (7, 'e7f21693adea45779c5541cbf6aae1d7', '13', '13', NULL, '13', '', '', '13', '河北省,石家庄市,桥西区', '竞争性谈判', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '13 13 13 河北省,石家庄市,桥西区 竞争性谈判  ', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 09:30:12', '2020-04-23 14:45:17');
INSERT INTO `project` VALUES (9, 'f4e8d50e68d74c5491823e63688a1d61', '15', '15', NULL, '15', '', '', '', '河北省,石家庄市,裕华区', '询价', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '15 15 15 河北省,石家庄市,裕华区 询价  ', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 09:30:00', '2020-04-23 14:56:37');
INSERT INTO `project` VALUES (10, 'a63747f57bcd4de0bca41a65b194cea8', '16', '16', NULL, '16', '', '', '', '河北省,石家庄市,井陉矿区', '单一来源', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '16 16 16 河北省,石家庄市,井陉矿区 单一来源  ', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 09:29:55', '2020-04-23 14:56:59');
INSERT INTO `project` VALUES (11, '3e2099a8b26e46b3945fd93b59527b80', '17', '17', NULL, '17', '', '', '', '河北省,石家庄市,高新区', '单一来源', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '17 17 17 河北省,石家庄市,高新区 单一来源  ', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 11:57:23', '2020-04-23 14:57:17');
INSERT INTO `project` VALUES (12, 'be5102c5be95490e82fcdbb9a63e5852', '1', '1', NULL, '1', '', '', '', '河北省,石家庄市,长安区', '公开招标', '', '', '', '', '', '', '', '', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '1 1 1 河北省,石家庄市,长安区 公开招标  ', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-24 16:49:05', '2020-04-24 16:49:05');
INSERT INTO `project` VALUES (13, '55c67c0ba3b24ec19d2376298a4048ec', '22', '采购项目22', NULL, '单位22', '张sa', '123', '石家庄', '河北省,石家庄市,长安区', '公开招标', '工程类', '自行招标', '', '', '', '河北省招标投标公共服务平台', '河北省招标投标公共服务平台', '河北省公共资源交易中心,中国公共资源交易中心', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '22 采购项目22 单位22 河北省,石家庄市,长安区 公开招标 工程类 自行招标', 'SUBMIT', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-26 17:29:13', '2020-04-24 17:00:24');
INSERT INTO `project` VALUES (14, '7ad3b53739d44c27865a00893ca54e5a', '21', '21', NULL, '21', '21', '21', '石家庄', '河北省,石家庄市,桥东区', '公开招标', '服务类', '2委托招标', '1231123123', '政府投资', '1', '河北省招标投标公共服务平台,河北省交通运输厅招投标中心', '河北省交通运输厅招投标中心', '河北省公共资源交易中心,中国公共资源交易中心', '132131312312313', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '21 21 21 河北省,石家庄市,桥东区 公开招标 服务类 2委托招标', 'SUBMIT', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-04-26 16:44:11', '2020-04-24 17:15:32');
INSERT INTO `project` VALUES (15, '9bb8aec8d0d145e4aef69ea336c0a754', '0012', '0012', NULL, '0012', '0012', '0012', '0012', '河北省,石家庄市,桥东区', '邀请招标', '货物类（含药品集中采购）', '委托招标', '0012', '企业投资', '1', '河北省招标投标公共服务平台,河北省发展和改革委员会', '河北省交通运输厅招投标中心', '河北省公共资源交易中心', '0012', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '0012 0012 0012 河北省,石家庄市,桥东区 邀请招标 货物类（含药品集中采购） 委托招标', 'EDIT', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-05-06 15:10:02', '2020-05-06 14:31:11');
INSERT INTO `project` VALUES (16, '63188a7448fb4af3a114077f9f6eb459', 'test', 'test', NULL, 'test', 'test', 'test', 'test', '河北省,保定市,新市区', '竞争性谈判', '工程类', '采购中心', 'test', '政府投资', '0', '河北省发展和改革委员会', '河北省交通运输厅招投标中心', '河北省公共资源交易中心', 'tset', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'test test test 河北招标集团测试网络科技有限公司 河北省,保定市,新市区 竞争性谈判 工程类 采购中心', 'APPROVAL', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-05-08 14:02:36', '2020-05-08 11:59:32');
INSERT INTO `project` VALUES (17, '9f99d84122444f2692b6c8403df3af83', 'HB-200084', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）电泳仪等设备、荧光化学发光成像系统等设备招标', NULL, '河北医科大学', '刘乃江', '0311-86265515', '石家庄市中山东路361号', '河北省,石家庄市,长安区', '公开招标', '货物类（含药品集中采购）', '委托招标', '1321312312312312312321', '政府投资', '0', '河北省招标投标公共服务平台', '河北省交通运输厅招投标中心', '河北省公共资源交易中心,中国公共资源交易中心', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目，其中标段1：电泳仪等设备采购；标段2：荧光化学发光成像系统等设备采购；采购设备数量1批，共2个标段。', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HB-200084 河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）电泳仪等设备、荧光化学发光成像系统等设备招标 河北医科大学 河北招标集团测试网络科技有限公司 河北省,石家庄市,长安区 公开招标 货物类（含药品集中采购） 委托招标', 'APPROVAL', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-06-08 17:12:59', '2020-06-01 16:15:03');
INSERT INTO `project` VALUES (18, '6beee6eb295c4b6db277f980b5c498f5', 'HBCT-200496-002', '河北省机关事务管理服务平台建设工程等保测评项目', NULL, '河北省机关事务管理局', '陈敬山', '15831181051', '石家庄市桥西区裕华西路69号', '河北省,石家庄市,桥西区', '公开招标', '工程类', '自行招标', '21212312111', '政府投资', '1', '河北省招标投标公共服务平台,河北省交通运输厅招投标中心', '河北省招标投标公共服务平台', '河北省公共资源交易中心,中国公共资源交易中心', '简要技术要求/采购项目的性质：详见磋商文件', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT-200496-002 河北省机关事务管理服务平台建设工程等保测评项目 河北省机关事务管理局 河北招标集团测试网络科技有限公司 河北省,石家庄市,桥西区 公开招标 工程类 自行招标', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-06-15 11:39:07', '2020-06-15 11:38:38');
INSERT INTO `project` VALUES (19, '8c2961258fee4864ad076450be7af110', 'HBCT20200611', '国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目采购及安装', NULL, '河北机场管理集团有限公司', '薛惠平', '0311-88255539', '石家庄国际机场T2', '河北省,石家庄市,正定县', '公开招标', '货物类（含药品集中采购）', '委托招标', '', '', '', '河北省招标投标公共服务平台', '石家庄市发展和改革委员会', '河北省公共资源交易中心,中国公共资源交易中心', '招标范围及标段划分：本项目标段划分为1个标段，石家庄国际机场T2航显屏升级改造项目的所有内容。', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT20200611 国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目采购及安装 河北机场管理集团有限公司 河北招标集团测试网络科技有限公司 河北省,石家庄市,正定县 公开招标 货物类（含药品集中采购） 委托招标', 'EDIT', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-06-15 11:47:16', '2020-06-15 11:44:40');
INSERT INTO `project` VALUES (20, 'accc50714f134614ba8a53f6282ac0f4', 'HBXJ20200611', '沧州经济开发区高端装备产业园（一车间、二车间）8台1600KVA配电工程', NULL, '沧州经济开发区建设投资有限公司', '苑红英  高哲', '0317-8698985  13463745088', '沧州市开发区东海路18号 地    址：  保定市七一中路84号', '河北省,沧州市,新华区', '邀请招标', '货物类（含药品集中采购）', '自行招标', '沧开发经发批字【2018】2号', '政府投资', '1', '河北省招标投标公共服务平台', '河北省发展和改革委员会', '河北省公共资源交易中心,中国公共资源交易中心', '本招标项目沧州经济开发区高端装备产业园（一车间、二车间）8台1600KVA配电工程已由 河北沧州经济开发区经济发展局  以关于“沧州经济开发区高端装备产业园”  可行性研究报告的批复沧开发经发批字【2018】2号 批准建设，招标人(项目业主)为沧州经济开发区建设投资有限公司，建设资金来自企业自筹，项目出资比例为100%。项目已具备招标条件，现对该项目的施工进行公开招标。', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBXJ20200611 沧州经济开发区高端装备产业园（一车间、二车间）8台1600KVA配电工程 沧州经济开发区建设投资有限公司 河北招标集团测试网络科技有限公司 河北省,沧州市,新华区 邀请招标 货物类（含药品集中采购） 自行招标', 'APPROVAL', NULL, '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-06-15 11:51:28', '2020-06-15 11:50:03');
INSERT INTO `project` VALUES (21, '48b5dca8891647d5b406792374db886e', '1234', '1234', NULL, '1234', '2134', '1234', '1234', '河北省,石家庄市,长安区', '公开招标', '工程类', '自行招标', '1234', '政府投资', '1', '河北省交通运输厅招投标中心', '河北省招标投标公共服务平台', '河北省公共资源交易中心', '1234', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '1234 1234 1234 河北招标集团测试网络科技有限公司 河北省,石家庄市,长安区 公开招标 工程类 自行招标', 'APPROVAL', '', '', 'u428b238826764e50b674c3adc88e8b84', '13131197776', '2020-06-22 09:56:42', '2020-06-22 09:42:07');

-- ----------------------------
-- Table structure for project_material
-- ----------------------------
DROP TABLE IF EXISTS `project_material`;
CREATE TABLE `project_material`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目uid',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '材料名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '材料电子件',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of project_material
-- ----------------------------
INSERT INTO `project_material` VALUES (1, '3e2099a8b26e46b3945fd93b59527b80', '采购计划相关附件', '526e060b-a508-4dc5-b3de-99c935d4dd2e.png', 'u428b238826764e50b674c3adc88e8b84', '2020-04-24 11:56:57', '2020-04-24 11:56:57');
INSERT INTO `project_material` VALUES (2, '3e2099a8b26e46b3945fd93b59527b80', '委托协议（合同）', 'eb18eef1-8d88-4d43-b398-a68ec821790f.png', 'u428b238826764e50b674c3adc88e8b84', '2020-04-24 11:57:14', '2020-04-24 11:57:14');
INSERT INTO `project_material` VALUES (3, 'be5102c5be95490e82fcdbb9a63e5852', '采购计划相关附件', 'c084510a-d830-4c59-8b93-b1d7897f0ff4.png', 'u428b238826764e50b674c3adc88e8b84', '2020-04-24 16:49:57', '2020-04-24 16:49:57');
INSERT INTO `project_material` VALUES (4, '7ad3b53739d44c27865a00893ca54e5a', '采购计划相关附件', 'bb3fb88b-5da8-4a88-8cc3-551f4ddf5335.png', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 15:24:37', '2020-04-26 15:24:37');
INSERT INTO `project_material` VALUES (5, '55c67c0ba3b24ec19d2376298a4048ec', '采购计划相关附件', '3e084bf5-cd85-4636-94c9-e7e7cbedc640.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 17:28:59', '2020-04-26 17:28:59');
INSERT INTO `project_material` VALUES (6, '55c67c0ba3b24ec19d2376298a4048ec', '委托协议（合同）', '7d6ca03f-dfcd-4b5f-a819-70a34acd3d8d.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 17:29:04', '2020-04-26 17:29:04');
INSERT INTO `project_material` VALUES (8, '9bb8aec8d0d145e4aef69ea336c0a754', '委托协议（合同）', '61dad89683254adb8657f0410419ca9a.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:42:19', '2020-05-06 14:42:19');
INSERT INTO `project_material` VALUES (9, '9bb8aec8d0d145e4aef69ea336c0a754', '招标文件', 'd6b808ef919e437eb8677288f8386069.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:44:48', '2020-05-06 14:44:48');
INSERT INTO `project_material` VALUES (10, '9bb8aec8d0d145e4aef69ea336c0a754', '承诺书', '4d554f24-7c9d-4e85-877a-aa2bde3556bc.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 15:05:44', '2020-05-06 15:05:44');
INSERT INTO `project_material` VALUES (11, '9bb8aec8d0d145e4aef69ea336c0a754', '采购计划相关附件', '281a848e-b366-472a-a7db-95ea85513075.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 15:08:41', '2020-05-06 15:08:41');
INSERT INTO `project_material` VALUES (12, '63188a7448fb4af3a114077f9f6eb459', '采购计划相关附件', '60718854-0cdb-44b8-981b-73227bc35af0.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 13:43:34', '2020-05-08 13:43:34');
INSERT INTO `project_material` VALUES (13, '63188a7448fb4af3a114077f9f6eb459', '委托协议（合同）', '62a3789c-d4c6-4bb1-8905-3371f4fc426f.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 13:48:22', '2020-05-08 13:48:22');
INSERT INTO `project_material` VALUES (14, '9f99d84122444f2692b6c8403df3af83', '采购计划相关附件', 'c1173506-cae5-42de-a34a-8d432fa0640f.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:19:34', '2020-06-01 16:19:34');
INSERT INTO `project_material` VALUES (15, '9f99d84122444f2692b6c8403df3af83', '委托协议（合同）', '988441da-f609-448f-89c4-6d146a51b763.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:19:43', '2020-06-01 16:19:43');
INSERT INTO `project_material` VALUES (16, '9f99d84122444f2692b6c8403df3af83', '招标文件', 'c59923d2-9420-494f-9285-cb4d10257db0.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:19:47', '2020-06-01 16:19:47');
INSERT INTO `project_material` VALUES (17, '9f99d84122444f2692b6c8403df3af83', '承诺书', '490e1ad2-0051-467c-ab32-4f63c0e4ae3c.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-06-08 17:12:46', '2020-06-08 17:12:46');
INSERT INTO `project_material` VALUES (18, '6beee6eb295c4b6db277f980b5c498f5', '采购计划相关附件', '4c899904-ec71-4061-b1c4-9875d41604b8.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:39:38', '2020-06-15 11:39:38');
INSERT INTO `project_material` VALUES (19, '8c2961258fee4864ad076450be7af110', '采购计划相关附件', '5ca52d89-81ac-4696-a1be-43d3afc69cbe.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:47:12', '2020-06-15 11:47:12');
INSERT INTO `project_material` VALUES (20, '8c2961258fee4864ad076450be7af110', '委托协议（合同）', '48e2cc2a-1e19-4d37-827c-78a62056df86.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:47:14', '2020-06-15 11:47:14');
INSERT INTO `project_material` VALUES (21, '48b5dca8891647d5b406792374db886e', '采购计划相关附件', 'd484bebc-bdfe-4895-a461-652bb9c7d757.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 09:51:09', '2020-06-22 09:51:09');
INSERT INTO `project_material` VALUES (22, '48b5dca8891647d5b406792374db886e', '委托协议（合同）', '126d5fe7-4e05-4390-ae92-5f6b5c5162ed.jpg', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 09:56:15', '2020-06-22 09:56:15');
INSERT INTO `project_material` VALUES (23, '8c2961258fee4864ad076450be7af110', '招标文件', '3eb24642-9d87-4e71-ae96-978d9fe86636.pdf', 'u428b238826764e50b674c3adc88e8b84', '2020-06-23 16:24:10', '2020-06-23 16:24:10');

-- ----------------------------
-- Table structure for section
-- ----------------------------
DROP TABLE IF EXISTS `section`;
CREATE TABLE `section`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属项目uid',
  `uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段uid',
  `serial_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段名称',
  `area` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属辖区',
  `trade_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购方式',
  `trade_category` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '采购交易分类',
  `budget` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '预算总价（元）',
  `reply_deadline` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邀请招标的回复截止时间',
  `bid_open_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开标时间',
  `bid_open_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开标场地',
  `bid_online` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否采用网上开评标',
  `need_guarantee` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否有投标保证金',
  `eval_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标办法',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标段内容',
  `qualification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '投标人资格条件',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理uid',
  `agency_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '搜索关键字',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `approver_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核人uid',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of section
-- ----------------------------
INSERT INTO `section` VALUES (1, 't8c19f2f81ac4422cba52e880b0a068bf', 's1da168e4271949a9ba08fb2e88a01b4c', '1111', '1111', '', '', NULL, '1111', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, '1', '河北招标集团网络科技有限公司', '1', '审核通过', '', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14', '2020-02-04 17:59:37');
INSERT INTO `section` VALUES (2, 't783e04ab94e94f37a7c235977ad11321', 's4d8f31cc5da4493cb64943916deb45ba', 'asdf111', '1111', '', '', NULL, '1111', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, '', '1', '河北招标集团网络科技有限公司', '1', '编辑中', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `section` VALUES (4, 't84c29e1db0134e5b990c337d92c1926f', 's41eeaab1f07249f2975b0ab54b30b2b2', 'ttt222', '222', '', '', NULL, '222', NULL, NULL, NULL, NULL, NULL, NULL, 'asdf', NULL, 'xxx', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', ' ', '审核通过', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:02:47');
INSERT INTO `section` VALUES (5, 't84c29e1db0134e5b990c337d92c1926f', 'scbdfa952a0db4b8b99b20cd600f8c181', 'ttt333', '333', '', '', NULL, '333', NULL, NULL, NULL, NULL, NULL, NULL, 'asdf', NULL, 'xxx', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', '审核通过', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `section` VALUES (6, 't84c29e1db0134e5b990c337d92c1926f', 's7dacf11e0bea4262969f3c8a9122b25f', '6666', '666', '', '', NULL, '66', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', 'APPROVAL', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:03:35');
INSERT INTO `section` VALUES (10, '7ad3b53739d44c27865a00893ca54e5a', '2d3f5da4b9e94574af1af7fe07c58fd0', 'HBCT-160161-172XX', '河北建投沙河发电有限责任公司经营期运输车辆采购项目', '600000', '3', NULL, '1800000', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '3 3 竞争性磋商  1', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:41:29');
INSERT INTO `section` VALUES (11, '7ad3b53739d44c27865a00893ca54e5a', '127b77675f5d4bd8b131be67f9d2fdc9', 'HBCT-160161-191XX', '华北制药股份有限公司北元分厂多效蒸馏水机采购招标', '2500000', '2', NULL, '5000000', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '2 2 公开招标  0', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `section` VALUES (12, '7ad3b53739d44c27865a00893ca54e5a', 'scbdfa952a0db4b8b99b20cd600f8c181', 'HBCT-170101-100XX', '东方碧水雅居安置房项目所需电梯采购及安装招标', '120000', '10', NULL, '12000000', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '1 1 邀请招标  0', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `section` VALUES (13, '55c67c0ba3b24ec19d2376298a4048ec', 'ef14c5cd5fca45c5ba11a64f04cb2905', 'HBCT2019062001001', '安平县东庙头至北郝村（南大堤）道路路灯采购项目', '50000', '5000', NULL, '250000000', NULL, NULL, NULL, NULL, NULL, NULL, '1', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '11 1 公开招标  0', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 17:28:45', '2020-04-26 17:28:45');
INSERT INTO `section` VALUES (14, '9bb8aec8d0d145e4aef69ea336c0a754', 'e3bb2d4213dc417ba4a38c46d4cedd54', 'HBCT-190054-009', '沧州市御河新城项目御河新城住宅小区项目三期工程御河新城东区5号住宅楼、东商业11号楼、东商业9号楼、东商业10号楼及东区6号地下车库工程钢材供应商入围项目', '2500', '20000', NULL, '500000000', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '00120012 00120012 邀请招标  0', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:36:31', '2020-05-06 14:36:16');
INSERT INTO `section` VALUES (15, '9bb8aec8d0d145e4aef69ea336c0a754', '0d2d0144a5cd4a51a4e10f703890fd44', '00120013', '00120013', '3', '3', NULL, '3', NULL, NULL, NULL, NULL, NULL, NULL, '3', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '00120013 00120013 公开招标  1', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 14:42:09', '2020-05-06 14:42:09');
INSERT INTO `section` VALUES (16, '9bb8aec8d0d145e4aef69ea336c0a754', '497dd69801d042cb825fc6da75f8b9aa', '00120014', '00120014', '4', '4', NULL, '4', NULL, NULL, NULL, NULL, NULL, NULL, '00120014', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '00120014 00120014 竞争性谈判  1', 'SUBMIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-06 15:05:33', '2020-05-06 15:05:33');
INSERT INTO `section` VALUES (17, '63188a7448fb4af3a114077f9f6eb459', '69e70d7c6ebe47d09c803816433eb7bd', 't1', 't1', '1', '1', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, 't1', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 't1 t1', 'APPROVAL', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 13:45:42', '2020-05-08 11:59:49');
INSERT INTO `section` VALUES (18, '63188a7448fb4af3a114077f9f6eb459', 'b17bf185f4f04798a195c3594492ed44', 't2', 't2', '2', '2', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 't2 t2', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-08 13:45:53', '2020-05-08 13:34:40');
INSERT INTO `section` VALUES (19, '9f99d84122444f2692b6c8403df3af83', '08aa6b2c47bf401983333b792714858d', 'HB-200084-001', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目电泳仪等设备采购', '500000', '10', NULL, '5000000', NULL, NULL, NULL, NULL, NULL, NULL, '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目电泳仪等设备采购', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HB-200084-001 河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目电泳仪等设备采购', '等待开标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:17:29', '2020-06-01 16:17:29');
INSERT INTO `section` VALUES (20, '9f99d84122444f2692b6c8403df3af83', '423e0526b2e542e789ed3a8037fbc846', 'HB-200084-002', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购', '300000', '10', NULL, '3000000', NULL, NULL, NULL, NULL, NULL, NULL, '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HB-200084-002 河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购', 'APPROVAL', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 16:18:59', '2020-06-01 16:18:59');
INSERT INTO `section` VALUES (21, '6beee6eb295c4b6db277f980b5c498f5', '65034a694b3f48768df5e28978c4637e', 'HBCT-200496-002-01', '河北省机关事务管理服务平台建设工程等保测评项目A包', '5000', '100', NULL, '5000000', NULL, NULL, NULL, NULL, NULL, NULL, '河北省机关事务管理服务平台建设工程等保测评项目A包', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT-200496-002-01 河北省机关事务管理服务平台建设工程等保测评项目A包', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:41:14', '2020-06-15 11:41:14');
INSERT INTO `section` VALUES (22, '6beee6eb295c4b6db277f980b5c498f5', 'a16fa272708d493d90f37a640d302eee', 'HBCT-200496-002-02', '河北省机关事务管理服务平台建设工程等保测评项目B包', '6000', '100', NULL, '600000', NULL, NULL, NULL, NULL, NULL, NULL, '河北省机关事务管理服务平台建设工程等保测评项目B包', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT-200496-002-02 河北省机关事务管理服务平台建设工程等保测评项目B包', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:41:46', '2020-06-15 11:41:46');
INSERT INTO `section` VALUES (23, '8c2961258fee4864ad076450be7af110', '57c45e124c054fc588d0be8c0d7ea302', 'HBCT202006-01', '国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目采购', '5000000', '2', NULL, '10000000', NULL, NULL, NULL, NULL, NULL, NULL, '国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目采购', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT202006-01 国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目采购', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:46:30', '2020-06-15 11:46:30');
INSERT INTO `section` VALUES (24, '8c2961258fee4864ad076450be7af110', 'd27ec5f008174b12ab043233fd2a74a0', 'HBCT202006-02', '国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目安装', '100000', '1', NULL, '100000', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBCT202006-02 国家税务总局河北省税务局采购项目石家庄国际机场T2航显屏升级改造项目安装', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:47:07', '2020-06-15 11:47:07');
INSERT INTO `section` VALUES (25, 'accc50714f134614ba8a53f6282ac0f4', 'f43f489e646e4476ae81927db26bb9bf', 'HBJX2020001', '沧州经济开发区高端装备产业园一车间8台1600KVA配电工程', '323500', '5', NULL, '1876000', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBJX2020001 沧州经济开发区高端装备产业园一车间8台1600KVA配电工程', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:50:50', '2020-06-15 11:50:50');
INSERT INTO `section` VALUES (26, 'accc50714f134614ba8a53f6282ac0f4', '205af479b38e4f0390693086116f47bc', 'HBJX2020206002', '沧州经济开发区高端装备产业园（二车间）8台1600KVA配电工程', '58000', '300', NULL, '16800250', NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'HBJX2020206002 沧州经济开发区高端装备产业园（二车间）8台1600KVA配电工程', '等待开标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-15 11:51:23', '2020-06-15 11:51:23');
INSERT INTO `section` VALUES (28, '48b5dca8891647d5b406792374db886e', 'd297464504994678a91d695ab12049d7', '1234-1', '1234-1', '河北省,石家庄市,桥东区', '邀请招标', '工程类', '12341', NULL, '2020-06-30 00:00:00', '开标2室', '0', '0', NULL, '12341', '12341', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '1234-1 1234-1', 'EDIT', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 18:01:57', '2020-06-22 10:04:41');
INSERT INTO `section` VALUES (29, '63188a7448fb4af3a114077f9f6eb459', '46614a8c6b894cd8beda65e7451a42fe', 'test-1', 'test-1', '河北省,承德市,承德县', '公开招标', '货物类（含药品集中采购）', '111', NULL, '2020-06-26 00:00:00', '开标一室', 'true', 'true', '综合评分法', 'test-1', 'test-1', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'test-1 test-1', 'APPROVAL', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 17:01:16', '2020-06-22 16:47:20');
INSERT INTO `section` VALUES (30, '63188a7448fb4af3a114077f9f6eb459', '043445bfc575473abfc57bb2e6608e68', 'test-2', 'test-2', '河北省,邯郸市,武安市', '询价', '服务类', '500000', NULL, '2020-06-30 00:00:00', '石家庄', 'false', 'true', NULL, 'test-2', 'test-2', '', '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 'test-2 test-2', 'APPROVAL', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-28 17:01:16', '2020-06-23 08:41:34');

-- ----------------------------
-- Table structure for tender_bid_file
-- ----------------------------
DROP TABLE IF EXISTS `tender_bid_file`;
CREATE TABLE `tender_bid_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sort_id` int(11) DEFAULT NULL COMMENT '序号',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）uid',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件名称',
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '说明',
  `needed` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否必须',
  `sealed` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否需要签章',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tender_bid_file
-- ----------------------------
INSERT INTO `tender_bid_file` VALUES (13, 1, 's7dacf11e0bea4262969f3c8a9122b25f', '封面', '投标文件封面（加盖公章）', '否', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 10:22:39', '2020-04-26 17:28:45');
INSERT INTO `tender_bid_file` VALUES (14, 2, 's7dacf11e0bea4262969f3c8a9122b25f', '正文', '正文说明', '是', '否', 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 10:21:59', '2020-05-09 17:08:07');
INSERT INTO `tender_bid_file` VALUES (16, 3, 's7dacf11e0bea4262969f3c8a9122b25f', '测试', 'test', '否', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 10:22:44', '2020-05-11 10:07:10');
INSERT INTO `tender_bid_file` VALUES (17, 2, 'ef14c5cd5fca45c5ba11a64f04cb2905', '正文', '投标文件正文', '是', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:15:16', '2020-05-18 11:15:16');
INSERT INTO `tender_bid_file` VALUES (18, 1, 'ef14c5cd5fca45c5ba11a64f04cb2905', '封面', '封面', '是', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:15:31', '2020-05-18 11:15:31');
INSERT INTO `tender_bid_file` VALUES (19, 1, '423e0526b2e542e789ed3a8037fbc846', '封面', '投标文件封面', '是', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:33:00', '2020-06-02 17:33:00');
INSERT INTO `tender_bid_file` VALUES (20, 2, '423e0526b2e542e789ed3a8037fbc846', '正文', '投标文件正文', '是', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:33:16', '2020-06-02 17:33:16');
INSERT INTO `tender_bid_file` VALUES (21, 1, '46614a8c6b894cd8beda65e7451a42fe', '11', '11', '是', '是', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:24:19', '2020-06-24 16:24:19');
INSERT INTO `tender_bid_file` VALUES (22, 2, '46614a8c6b894cd8beda65e7451a42fe', '22', '22', '是', '否', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:24:27', '2020-06-24 16:24:27');

-- ----------------------------
-- Table structure for tender_file
-- ----------------------------
DROP TABLE IF EXISTS `tender_file`;
CREATE TABLE `tender_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）uid',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件名',
  `price` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '文件售价（元）',
  `start_sell_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发售时间',
  `end_sell_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '发售截止时间',
  `obtain_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '获取方法',
  `origin_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '源文件',
  `encrypt_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '加密文件',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tender_file
-- ----------------------------
INSERT INTO `tender_file` VALUES (1, 's1da168e4271949a9ba08fb2e88a01b4c', NULL, '1', NULL, NULL, '2222', NULL, NULL, NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14', '2020-02-04 17:59:37');
INSERT INTO `tender_file` VALUES (2, 's4d8f31cc5da4493cb64943916deb45ba', NULL, '2', NULL, NULL, '2222', NULL, NULL, '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `tender_file` VALUES (4, 's41eeaab1f07249f2975b0ab54b30b2b2', NULL, '3', NULL, NULL, '2222', NULL, NULL, 'xxx', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:02:47');
INSERT INTO `tender_file` VALUES (5, 'scbdfa952a0db4b8b99b20cd600f8c181', NULL, '4', NULL, NULL, '333', NULL, NULL, 'xxx', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `tender_file` VALUES (6, 's7dacf11e0bea4262969f3c8a9122b25f', NULL, '666', '2020-05-09 00:00:00', '2020-05-31 00:00:00', '6666', NULL, NULL, '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 17:03:13', '2020-02-13 16:03:35');
INSERT INTO `tender_file` VALUES (10, '2d3f5da4b9e94574af1af7fe07c58fd0', NULL, '6', NULL, NULL, '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:41:29');
INSERT INTO `tender_file` VALUES (11, '127b77675f5d4bd8b131be67f9d2fdc9', NULL, '7', NULL, NULL, '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `tender_file` VALUES (12, 'd8e8f1f816494c10979b5502e833ef98', NULL, '8', NULL, NULL, '', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `tender_file` VALUES (13, 'ef14c5cd5fca45c5ba11a64f04cb2905', NULL, '9', '2020-05-18 10:15:32', '2020-05-31 00:00:00', '招标文件获取方法', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 10:16:46', '2020-04-26 17:28:45');
INSERT INTO `tender_file` VALUES (14, '423e0526b2e542e789ed3a8037fbc846', NULL, '500', '2020-06-02 17:31:10', '2020-06-30 00:00:00', '招标文件获取方法', NULL, NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:31:49', '2020-06-02 17:31:49');
INSERT INTO `tender_file` VALUES (15, '043445bfc575473abfc57bb2e6608e68', '招标文件.pdf', '600', '2020-06-23 17:17:40', '2020-06-30 00:00:00', '投标人平台获取', '167a2430-d3c6-47bf-9156-cf66c039e8f5.pdf', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 10:13:05', '2020-06-23 17:07:32');
INSERT INTO `tender_file` VALUES (16, '46614a8c6b894cd8beda65e7451a42fe', '招标文件.pdf', '800', '2020-06-25 00:00:00', '2020-06-30 00:00:00', '招标文件获取方法', '2189fddd-418c-4228-81c6-ba283ca6f778.pdf', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 14:35:40', '2020-06-24 14:35:32');

-- ----------------------------
-- Table structure for tender_guarantee
-- ----------------------------
DROP TABLE IF EXISTS `tender_guarantee`;
CREATE TABLE `tender_guarantee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）uid',
  `amount` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金金额（元）',
  `deadline` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金缴纳截至日期',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金缴纳方式',
  `bank_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金缴纳开户行',
  `account_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金缴纳户名',
  `account_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '保证金缴纳账号',
  `basic_pay` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '必须基本户支付',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tender_guarantee
-- ----------------------------
INSERT INTO `tender_guarantee` VALUES (1, 's1da168e4271949a9ba08fb2e88a01b4c', '1441144', '2020-02-05 15:30:30', NULL, '202222', '2021111', '', '0', NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-06 15:12:11', '2020-02-06 15:08:14');
INSERT INTO `tender_guarantee` VALUES (2, 's7dacf11e0bea4262969f3c8a9122b25f', '66666', '2020-02-05 15:30:30', 'true', '666', '666', '666666', '1', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-11 16:51:40', '2020-05-11 16:51:40');
INSERT INTO `tender_guarantee` VALUES (3, 'ef14c5cd5fca45c5ba11a64f04cb2905', '11', '2020-05-31 00:00:00', 'true', '1', '1', '1', '1', '111', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 10:22:43', '2020-05-18 10:22:43');
INSERT INTO `tender_guarantee` VALUES (4, '423e0526b2e542e789ed3a8037fbc846', '100000', '2020-06-30 00:00:00', 'true', '河北银行', '河北招标集团', '1234125632355', '1', '无', 'u428b238826764e50b674c3adc88e8b84', '2020-06-01 18:02:28', '2020-06-01 18:02:28');
INSERT INTO `tender_guarantee` VALUES (5, '46614a8c6b894cd8beda65e7451a42fe', '150000', '2020-06-30 00:00:00', '银行保函,现金', '11', '11', '11', 'true', '11', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:34:52', '2020-06-24 15:14:59');
INSERT INTO `tender_guarantee` VALUES (6, '043445bfc575473abfc57bb2e6608e68', '500000', '2020-06-30 00:00:00', '担保机构保函', '', '', '', 'false', '', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 17:02:01', '2020-06-24 15:47:03');

-- ----------------------------
-- Table structure for tender_section
-- ----------------------------
DROP TABLE IF EXISTS `tender_section`;
CREATE TABLE `tender_section`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '项目uid',
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）uid',
  `serial_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）编号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）名称',
  `trade_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '采购方式',
  `bid_open_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开标时间',
  `bid_open_period` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开标时长',
  `bid_open_place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '开标场地',
  `bid_eval_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标时间',
  `bid_eval_period` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标时长',
  `bid_eval_place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标场地',
  `bid_online` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '是否采用网上开评标',
  `eval_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '评标方法',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理uid',
  `agency_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '招标代理',
  `keyword` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '搜索关键字',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态(编辑中、待审核、审核通过、审核不通过)',
  `approver_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '审核人uid',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tender_section
-- ----------------------------
INSERT INTO `tender_section` VALUES (1, 't8c19f2f81ac4422cba52e880b0a068bf', '2d3f5da4b9e94574af1af7fe07c58fd0', 'HBCT-160161-172XX', '河北建投沙河发电有限责任公司经营期运输车辆采购项目', '公开招标', '2020-05-17 09:00:00', '30', '开标一室', '2020-05-17 10:00:00', '120', '评标一室', '1', NULL, NULL, '1', '河北招标集团网络科技有限公司', '1', '已结束', '', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14', '2020-02-04 17:59:37');
INSERT INTO `tender_section` VALUES (2, 't783e04ab94e94f37a7c235977ad11321', '69e70d7c6ebe47d09c803816433eb7bd', 'HBCT-160161-191XX', '华北制药股份有限公司北元分厂多效蒸馏水机采购招标', '公开招标', '2020-05-20 09:00:00', '60', '开标二室', '2020-05-20 10:00:00', '180', '评标二室', '0', NULL, '', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', '已结束', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `tender_section` VALUES (4, 't84c29e1db0134e5b990c337d92c1926f', '0d2d0144a5cd4a51a4e10f703890fd44', 'HBCT-170101-100XX', '东方碧水雅居安置房项目所需电梯采购及安装招标', '邀请招标', '2020-05-22 10:00:00', '30', '开标二室', '2020-05-22 11:00:00', '120', '评标二室', '0', NULL, 'xxx', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', '已结束', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:02:47');
INSERT INTO `tender_section` VALUES (5, 't84c29e1db0134e5b990c337d92c1926f', 'scbdfa952a0db4b8b99b20cd600f8c181', 'HBCT2019062001001', '安平县东庙头至北郝村（南大堤）道路路灯采购项目', '单一来源', '2020-05-25 10:00:00', '60', '开标一室', '2020-05-25 14:00:00', '240', '评标一室', '0', NULL, 'xxx', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', '已结束', '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `tender_section` VALUES (6, 't84c29e1db0134e5b990c337d92c1926f', 'e3bb2d4213dc417ba4a38c46d4cedd54', 'HBCT-190054-009', '沧州市御河新城项目御河新城住宅小区项目三期工程御河新城东区5号住宅楼、东商业11号楼、东商业9号楼、东商业10号楼及东区6号地下车库工程钢材供应商入围项目', '竞争性磋商', '2020-05-25 10:00:00', '30', '开标二室', '2020-05-25 14:00:00', '120', '评标二室', '1', '最低评标价法', '', '033239197e1b40f8ab4890beced34722', '河北招标集团网络科技有限公司', '1', '正在评标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-02-17 16:42:22', '2020-02-13 16:03:35');
INSERT INTO `tender_section` VALUES (10, '7ad3b53739d44c27865a00893ca54e5a', 'b17bf185f4f04798a195c3594492ed44', 'HBCT-200324-001', '中国邮政储蓄银行股份有限公司石家庄市分行新装空调新装空调招标公告', '竞争性磋商', '2020-05-25 10:00:00', '60', '开标一室', '2020-05-25 12:00:00', '60', '评标一室', '1', '综合评分法', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '中国邮政储蓄银行股份有限公司石家庄市分行新装空调新装空调招标公告', '等待评标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:41:29');
INSERT INTO `tender_section` VALUES (11, '7ad3b53739d44c27865a00893ca54e5a', '127b77675f5d4bd8b131be67f9d2fdc9', 'YQJZCG20200085', '唐山市长期照护保险经办服务项目（A包、B包、C包、D包、E包、F包）唐山市长期照护保险经办服务项目A包、唐山市长期照护保险经办服务项目B包、唐山市长期照护保险经办服务项目C包、唐山市长期照护保险经办服务项目D包、唐山市长期照护保险经办服务项目E包、唐山市长期照护保险经办服务项目F包', '公开招标', '2020-05-28 09:00:00', '30', '开标二室', '2020-05-28 10:00:00', '60', '评标二室', '0', '综合评分法', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '唐山市长期照护保险经办服务项目（A包、B包、C包、D包、E包、F包）唐山市长期照护保险经办服务项目A包、唐山市长期照护保险经办服务项目B包、唐山市长期照护保险经办服务项目C包、唐山市长期照护保险经办服务项目D包、唐山市长期照护保险经办服务项目E包、唐山市长期照护保险经办服务项目F包', '评标准备', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `tender_section` VALUES (12, '7ad3b53739d44c27865a00893ca54e5a', 'd8e8f1f816494c10979b5502e833ef98', 'ZC2020007', '国家税务总局河北省税务局采购项目河北省税务局技术咨询热线外包服务招标公告', '邀请招标', '2020-05-28 09:00:00', '30', '开标二室', '2020-05-28 10:00:00', '60', '评标二室', '0', NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '国家税务总局河北省税务局采购项目河北省税务局技术咨询热线外包服务招标公告', '正在开标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `tender_section` VALUES (13, '55c67c0ba3b24ec19d2376298a4048ec', 'ef14c5cd5fca45c5ba11a64f04cb2905', '1111', '1111', '公开招标', '2020-05-28 09:00:00', '30', '开标一室112', '2020-05-28 10:00:00', '60', '评标一室222', '0', '综合评分法', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '11 1 公开招标  0', '等待开标', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-18 11:50:15', '2020-04-26 17:28:45');
INSERT INTO `tender_section` VALUES (14, '9f99d84122444f2692b6c8403df3af83', '423e0526b2e542e789ed3a8037fbc846', 'HB-200084-002', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购', '公开招标', '2020-06-02 00:00:00', '30', '石家庄', '2020-06-02 01:00:00', '120', '石家庄', '', '综合评分法', NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '423e0526b2e542e789ed3a8037fbc846 HB-200084-002 河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目荧光化学发光成像系统等设备采购 河北招标集团测试网络科技有限公司', 'EDIT', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:33:59', '2020-06-01 17:11:28');
INSERT INTO `tender_section` VALUES (15, '9f99d84122444f2692b6c8403df3af83', '08aa6b2c47bf401983333b792714858d', 'HB-200084-001', '河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目电泳仪等设备采购', '公开招标', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', '08aa6b2c47bf401983333b792714858d HB-200084-001 河北医科大学2020年高校综合水平提升经费-基础医学实验教学建设平台（进口）项目电泳仪等设备采购 河北招标集团测试网络科技有限公司', 'EDIT', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-18 10:46:12', '2020-06-18 10:46:12');
INSERT INTO `tender_section` VALUES (16, 't84c29e1db0134e5b990c337d92c1926f', 's7dacf11e0bea4262969f3c8a9122b25f', '6666', '666', '邀请招标', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '033239197e1b40f8ab4890beced34722', '河北招标集团测试网络科技有限公司', 's7dacf11e0bea4262969f3c8a9122b25f 6666 666 河北招标集团测试网络科技有限公司', 'EDIT', NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-06-22 16:59:10', '2020-06-22 16:59:10');

-- ----------------------------
-- Table structure for tender_sheet
-- ----------------------------
DROP TABLE IF EXISTS `tender_sheet`;
CREATE TABLE `tender_sheet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `section_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '标段（包）uid',
  `item` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '唱标项',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '数据类型',
  `unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单位',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tender_sheet
-- ----------------------------
INSERT INTO `tender_sheet` VALUES (1, 's1da168e4271949a9ba08fb2e88a01b4c', '1', '2222', NULL, NULL, '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-04 18:02:14', '2020-02-04 17:59:37');
INSERT INTO `tender_sheet` VALUES (2, 's4d8f31cc5da4493cb64943916deb45ba', '2', '2222', NULL, '', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 15:57:24', '2020-02-13 15:57:24');
INSERT INTO `tender_sheet` VALUES (4, 's41eeaab1f07249f2975b0ab54b30b2b2', '3', '2222', NULL, 'xxx', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-17 16:42:22', '2020-02-13 16:02:47');
INSERT INTO `tender_sheet` VALUES (5, 'scbdfa952a0db4b8b99b20cd600f8c181', '4', '333', NULL, 'xxx', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 16:02:59', '2020-02-13 16:02:59');
INSERT INTO `tender_sheet` VALUES (6, 's7dacf11e0bea4262969f3c8a9122b25f', '交货日期', '文字', '天', '666', 'u428b238826764e50b674c3adc88e8b84', '2020-05-09 17:29:54', '2020-02-13 16:03:35');
INSERT INTO `tender_sheet` VALUES (10, '2d3f5da4b9e94574af1af7fe07c58fd0', '6', '', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:55:45', '2020-04-26 11:41:29');
INSERT INTO `tender_sheet` VALUES (11, '127b77675f5d4bd8b131be67f9d2fdc9', '7', '', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:41:51', '2020-04-26 11:41:51');
INSERT INTO `tender_sheet` VALUES (12, 'd8e8f1f816494c10979b5502e833ef98', '8', '', NULL, NULL, 'u428b238826764e50b674c3adc88e8b84', '2020-04-26 11:51:47', '2020-04-26 11:51:47');
INSERT INTO `tender_sheet` VALUES (24, 'ef14c5cd5fca45c5ba11a64f04cb2905', '工期', '文字', '天', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:09:04', '2020-05-25 14:09:04');
INSERT INTO `tender_sheet` VALUES (26, 'ef14c5cd5fca45c5ba11a64f04cb2905', '质量要求', '文字', '', '', 'u428b238826764e50b674c3adc88e8b84', '2020-05-25 14:19:01', '2020-05-25 14:19:01');
INSERT INTO `tender_sheet` VALUES (27, '423e0526b2e542e789ed3a8037fbc846', '交货日期', '文字', '天', '交货日期', 'u428b238826764e50b674c3adc88e8b84', '2020-06-02 17:32:24', '2020-06-02 17:32:24');
INSERT INTO `tender_sheet` VALUES (28, '46614a8c6b894cd8beda65e7451a42fe', '工期', '文字', '天', '无', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:10:41', '2020-06-24 16:10:41');
INSERT INTO `tender_sheet` VALUES (29, '46614a8c6b894cd8beda65e7451a42fe', '质量', '文字', '等级', 'AAA', 'u428b238826764e50b674c3adc88e8b84', '2020-06-24 16:11:42', '2020-06-24 16:11:42');

-- ----------------------------
-- Table structure for tenderee
-- ----------------------------
DROP TABLE IF EXISTS `tenderee`;
CREATE TABLE `tenderee`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '单位名称',
  `uniform_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '统一社会信用代码',
  `contact_person` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系人',
  `contact_number` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `agency_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '所属招标代理uid',
  `creator_uid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建人uid',
  `update_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '更新时间',
  `create_time` varchar(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tenderee
-- ----------------------------
INSERT INTO `tenderee` VALUES (2, '河北测试采购单位333', '3333xxxx911023123123123', '张三333', '13912348877', '1', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:55:55', '2020-01-23 10:42:40');
INSERT INTO `tenderee` VALUES (4, '河北测试采购单位', '911023123123123', '张三', '13912348877', '1', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:12', '2020-02-11 09:52:12');
INSERT INTO `tenderee` VALUES (5, '河北测试采购单位2', 'xxxx911023123123123', '张三', '13912348877', '1', '698e0138-b8ef-42a2-aac1-5ba16285f56c', '2020-02-11 09:52:37', '2020-02-11 09:52:37');
INSERT INTO `tenderee` VALUES (6, '河北测试采购单位TTT', '3333xx000000000123', '张三00', '13912348877', '1', '0c89b4f7-0265-45f9-84eb-4b34278331db', '2020-02-13 08:54:32', '2020-02-13 08:52:48');

SET FOREIGN_KEY_CHECKS = 1;
