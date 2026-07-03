/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : localhost:3306
 Source Schema         : qqsystem

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 03/07/2026 23:25:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for actor
-- ----------------------------
DROP TABLE IF EXISTS `actor`;
CREATE TABLE `actor`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '演员姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '演员照片URL',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '个人简介，例如国家一级演员、艺术经历等',
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '擅长角色，例如生旦净丑等',
  `style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '流派，例如传统派、创新派等',
  `works_count` int NULL DEFAULT 0 COMMENT '作品数量，统计演员主导或参与的作品数量',
  `view_count` int NULL DEFAULT 0 COMMENT '点击量 / 浏览量（例如125w）',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞量（例如8.9w）',
  `join_date` date NULL DEFAULT NULL COMMENT '加入时间（演员开始活跃时间/入行时间）',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：0=上架，1=下架',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '演员表：用于管理秦腔演员及相关资料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of actor
-- ----------------------------
INSERT INTO `actor` VALUES (1, '李东桥', 'https://example.com/actor1.jpg', '陕西著名秦腔演员，国家一级演员，擅长小生行当，多次获得戏曲表演一等奖。', '生', '传统派', 38, 560000, 32000, '1998-01-01', 1, '2026-04-14 15:23:10', '2026-04-14 15:23:10');
INSERT INTO `actor` VALUES (2, '张兰', 'https://example.com/actor2.jpg', '秦腔旦角表演艺术家，唱腔委婉细腻，是陕西省戏曲界的中坚力量。', '旦', '传统派', 42, 480005, 28000, '1995-03-01', 1, '2026-04-14 15:23:10', '2026-05-06 17:56:59');
INSERT INTO `actor` VALUES (4, '赵晴', 'https://example.com/actor4.jpg', '青年秦腔演员，风格清新自然，代表作《锁麟囊》《三滴血》。', '旦', '创新派', 18, 350000, 18000, '2012-06-10', 1, '2026-04-14 15:23:10', '2026-04-14 15:23:10');
INSERT INTO `actor` VALUES (5, '马浩', 'https://example.com/actor5.jpg', '秦腔老生演员，台风稳重，常出演历史题材剧目。', '生', '传统派', 50, 600000, 35000, '1988-01-01', 1, '2026-04-14 15:23:10', '2026-04-14 15:23:10');
INSERT INTO `actor` VALUES (6, '刘玉珍', 'https://example.com/actor6.jpg', '国家一级演员，擅长青衣，艺术功底深厚。', '旦', '传统派', 45, 470002, 26000, '1993-04-20', 1, '2026-04-14 15:23:10', '2026-05-06 17:41:09');
INSERT INTO `actor` VALUES (7, '韩建军', 'https://example.com/actor7.jpg', '秦腔武生，为动作戏和武戏的代表性人物之一。', '生', '传统派', 33, 390000, 21000, '2000-02-01', 1, '2026-04-14 15:23:10', '2026-04-14 15:23:10');
INSERT INTO `actor` VALUES (8, '郭小梅', 'https://example.com/actor8.jpg', '擅长花旦角色，唱腔明亮，表演灵动。', '旦', '创新派', 28, 360001, 17000, '2010-11-10', 0, '2026-04-14 15:23:10', '2026-05-11 18:06:02');
INSERT INTO `actor` VALUES (9, '陈子昊', 'https://example.com/actor9.jpg', '青年小生演员，近年来崭露头角，深受观众喜爱。', '生', '创新派', 20, 310001, 15000, '2015-05-01', 0, '2026-04-14 15:23:10', '2026-05-11 18:05:58');
INSERT INTO `actor` VALUES (10, '刘海龙', 'https://example.com/actor10.jpg', '丑角演员，喜剧表现力强，风格诙谐又不失专业。', '丑', '传统派', 26, 330004, 16000, '2008-08-08', 0, '2026-04-14 15:23:10', '2026-05-11 18:05:54');
INSERT INTO `actor` VALUES (12, '贾春生', 'https://example.com/actor12.jpg', '老生演员，舞台功底深厚，常担纲大戏主角。', '生', '创新派', 48, 520005, 30000, '1990-07-01', 0, '2026-04-14 15:23:10', '2026-05-11 18:05:50');
INSERT INTO `actor` VALUES (14, '高志强', '/upload/actor/7aed1d8e-b3b1-4b2b-9873-462a6d4287a0.jpeg', '擅长武生，动作利落，节奏紧凑。', '旦', '传统派', 0, 7, 0, '2006-06-22', 0, '2026-04-14 15:23:10', '2026-05-11 18:05:46');

-- ----------------------------
-- Table structure for browse_history
-- ----------------------------
DROP TABLE IF EXISTS `browse_history`;
CREATE TABLE `browse_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览的是什么类型（剧目/文化/演员/资讯）',
  `target_id` bigint NULL DEFAULT NULL COMMENT '具体浏览的对象ID',
  `view_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of browse_history
-- ----------------------------
INSERT INTO `browse_history` VALUES (1, 1, 'drama', 1, '2026-04-14 14:01:49');
INSERT INTO `browse_history` VALUES (2, 1, 'actor', 9, '2026-04-14 15:23:21');
INSERT INTO `browse_history` VALUES (3, 1, 'actor', 8, '2026-04-14 15:26:24');
INSERT INTO `browse_history` VALUES (4, 1, 'news', 1, '2026-04-14 22:32:34');
INSERT INTO `browse_history` VALUES (5, 1, 'news', 19, '2026-04-14 22:32:39');
INSERT INTO `browse_history` VALUES (6, 1, 'news', 17, '2026-04-14 22:32:41');
INSERT INTO `browse_history` VALUES (7, 1, 'news', 11, '2026-04-14 22:32:44');
INSERT INTO `browse_history` VALUES (8, 1, 'news', 20, '2026-04-14 22:35:44');
INSERT INTO `browse_history` VALUES (9, 1, 'news', 15, '2026-04-14 23:03:33');
INSERT INTO `browse_history` VALUES (10, 1, 'news', 12, '2026-04-15 00:31:11');
INSERT INTO `browse_history` VALUES (11, 1, 'news', 2, '2026-04-15 00:31:59');

-- ----------------------------
-- Table structure for content
-- ----------------------------
DROP TABLE IF EXISTS `content`;
CREATE TABLE `content`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '内容ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `biz_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '业务类型：drama/culture',
  `biz_id` bigint NULL DEFAULT NULL COMMENT '关联业务ID',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容类型：video/article/image',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '正文/描述',
  `media_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '视频或图片地址',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞量',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文化的分类：秦腔历史发展\n秦腔流派与艺术风格\n行当与角色体系（生旦净丑）\n秦腔经典剧目解析\n秦腔音乐与唱腔艺术\n舞美、服饰与化妆\n秦腔名家与艺术家\n剧团与传承机构\n秦腔文化活动\n秦腔数字化与多媒体资料',
  `status` int NULL DEFAULT 0 COMMENT '0：已上架 1：已下架',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_biz`(`biz_type` ASC, `biz_id` ASC) USING BTREE,
  INDEX `idx_type`(`type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '统一内容表（视频/文章/图片）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content
-- ----------------------------
INSERT INTO `content` VALUES (101, '锁麟囊-剧情介绍', 'drama', 1, 'article', '剧情介绍内容...', NULL, NULL, 0, 0, '2026-04-13 20:58:47', '2026-04-13 20:58:47', NULL, NULL);
INSERT INTO `content` VALUES (102, '锁麟囊-经典唱段', 'drama', 1, 'video', '唱段描述...', 'https://video.test/sln.mp4', 'https://img.test/sln_video.jpg', 0, 0, '2026-04-13 20:58:47', '2026-04-13 20:58:47', NULL, NULL);
INSERT INTO `content` VALUES (103, '锁麟囊-演出图片', 'drama', 1, 'image', '演出图片说明', 'https://img.test/sln1.jpg', 'https://img.test/sln1.jpg', 0, 0, '2026-04-13 20:58:47', '2026-04-13 20:58:47', NULL, NULL);
INSERT INTO `content` VALUES (201, '三滴血-剧情介绍', 'drama', 2, 'article', '三滴血剧情...', NULL, NULL, 0, 0, '2026-04-13 20:58:47', '2026-04-13 20:58:47', NULL, NULL);
INSERT INTO `content` VALUES (202, '三滴血-精彩片段', 'drama', 2, 'video', '精彩片段...', 'https://video.test/sdx.mp4', 'https://img.test/sdx_video.jpg', 0, 0, '2026-04-13 20:58:47', '2026-04-13 20:58:47', NULL, NULL);
INSERT INTO `content` VALUES (203, '秦腔的历史起源：从西周到今天的传承', 'culture', NULL, 'article', '秦腔源自西周时期的民间音乐，是中国最古老的戏曲之一。经过千年发展，逐渐形成陕西方言唱腔体系。其声腔高亢、放达、粗犷，被称为“百戏之祖”。', NULL, '/images/culture/history1.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:23', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (204, '秦腔的唱腔特点：苍凉、粗犷与爆发力', 'culture', NULL, 'article', '秦腔唱腔属于板腔体，同时具有西北地区的地域风格。唱腔高亢明亮，爆发力极强，被称为“吼秦腔”。其核心特点在于声腔粗犷、气息饱满、节奏紧张。', NULL, '/images/culture/voice_style.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:24', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (205, '秦腔的四大行当：生旦净丑解析', 'culture', NULL, 'article', '秦腔行当分为生、旦、净、丑四类。其中生行多为男性正面角色；旦行展示女性柔美与刚毅；净行以花脸为主，多为豪放角色；丑行则兼具幽默与智慧，提升戏剧节奏。', NULL, '/images/culture/roles.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:25', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (206, '秦腔服饰与化妆：色彩与角色性格的艺术表达', 'culture', NULL, 'article', '秦腔的服装主要包括蟒、靠、褶子等，结合明亮色彩呈现角色地位。脸谱颜色象征人物性格，如红色代表忠勇，黑色象征刚直不阿，蓝色多为草莽英雄。', NULL, '/images/culture/makeup.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:25', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (207, '秦腔的乐器配置：板胡、唢呐与锣鼓的激情碰撞', 'culture', NULL, 'article', '秦腔伴奏以板胡为主奏乐器，配以唢呐、笛子以及强烈的锣鼓点，形成刚劲有力的音乐特色。其乐队结构体现了西北地区粗犷豪迈的审美传统。', NULL, '/images/culture/music.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:28', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (208, '秦腔剧目的分类：全本戏与折子戏', 'culture', NULL, 'article', '秦腔剧目大致分为全本戏和折子戏。全本戏结构完整，时长较长；折子戏则取精彩片段，适合演出与教学。代表剧目包括《锁麟囊》《三滴血》《火焰驹》等。', NULL, '/images/culture/drama_types.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:27', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (209, '秦腔的念白与陕西方言', 'culture', NULL, 'article', '秦腔念白使用陕西方言，带有强烈的地域特色，是其艺术魅力的重要组成部分。念白节奏鲜明，情绪波动大，使人物刻画更加鲜活。', NULL, '/images/culture/dialogue.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:29', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (210, '秦腔中的武生表演：刚劲流畅的身段艺术', 'culture', NULL, 'article', '秦腔武生擅长刀枪把子功，身段强调力量、速度与爆发力。武打场面节奏紧凑，是秦腔舞台上的亮点之一。', NULL, '/images/culture/wusheng.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:31', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (211, '秦腔旦角的艺术风格：刚柔并济的舞台表现', 'culture', NULL, 'article', '秦腔旦角唱腔委婉细腻，但又不失秦腔的高亢与力量。旦行分为青衣、花旦、刀马旦等，各具艺术风格。', NULL, '/images/culture/dan.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:32', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (212, '秦腔净角（花脸）的角色特征', 'culture', NULL, 'article', '秦腔净角多为性格鲜明、情绪激烈的人物。脸谱夸张、唱腔洪亮，表演中常见豪放动作，是舞台上的“气氛制造者”。', NULL, '/images/culture/jing.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:32', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (213, '秦腔文化在西北地区的社会影响', 'culture', NULL, 'article', '秦腔不仅是艺术，更是西北群众生活的一部分。在节庆、庙会、婚礼等场景中，秦腔演出常常成为最受欢迎的节目，具有强烈的文化认同感。', NULL, '/images/culture/society.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:33', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (214, '秦腔的传承困境与数字化创新', 'culture', NULL, 'article', '随着年轻人兴趣转变，秦腔面临传承危机。数字化平台、短视频、直播等为秦腔文化推广带来了新机遇，使更多人了解这一传统艺术。', NULL, '/images/culture/digital.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:33', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (215, '秦腔中的经典武戏动作解析', 'culture', NULL, 'article', '秦腔武戏中常见“趟马”“翻跟头”“亮相”等动作。演员需具备扎实的武功基础，通过动作与节奏展示人物情绪。', NULL, '/images/culture/wuxi.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:36', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (216, '秦腔舞台调度：空间节奏与剧情推进', 'culture', NULL, 'article', '秦腔舞台调度强调人物之间的对位关系，奔放大气的走位方式反映西北文化的开阔与豪迈，体现了强烈的戏剧张力。', NULL, '/images/culture/stage.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:37', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (217, '秦腔在民间传统节日中的角色', 'culture', NULL, 'article', '春节、元宵、社火活动中秦腔演出十分普遍，是西北节庆文化的重要组成部分。其热烈气氛与节庆仪式相互映衬。', NULL, '/images/culture/festival.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:37', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (218, '秦腔的代表人物与艺术大师介绍', 'culture', NULL, 'article', '秦腔界涌现出众多艺术大师，如李彦琴、马友仙、杨荣荣等，他们的艺术风格各具特色，对秦腔发展具有重要影响。', NULL, '/images/culture/masters.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:38', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (219, '秦腔的身段与表演体系', 'culture', NULL, 'article', '秦腔表演动作幅度大，节奏迅猛，尤其注重手眼身法步的协调配合。其“亮相”“云手”等动作具有独特艺术美感。', NULL, '/images/culture/body.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:39', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (220, '秦腔服装纹样与传统审美', 'culture', NULL, 'article', '秦腔服饰纹样常见龙凤、山水、祥云等传统图案，色彩鲜艳，象征吉祥与身份地位，是戏曲美学的重要组成部分。', NULL, '/images/culture/pattern.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:39', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (221, '秦腔在现代舞台的创新发展', 'culture', NULL, 'article', '秦腔融合现代舞台灯光、音响、多媒体技术，形成新的观演方式。创新戏目不断涌现，使其在新时代重新焕发活力。', NULL, '/images/culture/modern.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:40', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (222, '秦腔的教育与传承体系', 'culture', NULL, 'article', '目前陕西一些专业艺术院校设有秦腔表演专业，并与剧团合作培养青年演员，形成系统的传承机制。', NULL, '/images/culture/education.jpg', 0, 0, '2026-04-14 08:41:57', '2026-04-21 14:14:40', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (223, '秦腔的起源与发展', 'culture', NULL, 'article', '秦腔起源于西北地区，是中国最古老的剧种之一，历史悠久，影响深远。', NULL, 'https://example.com/culture1.jpg', 52000, 3200, '2026-04-14 20:34:30', '2026-04-21 14:14:41', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (224, '秦腔发展史纪录片', 'culture', NULL, 'article', '通过影像资料回顾秦腔从古至今的发展历程。', NULL, '', 88012, 5400, '2026-04-14 20:34:30', '2026-05-11 18:09:18', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (225, '秦腔流派艺术解析', 'culture', NULL, 'article', '不同流派在唱腔、表演形式上各具特色。', NULL, 'https://example.com/culture3.jpg', 43000, 2100, '2026-04-14 20:34:30', '2026-04-21 14:14:42', '秦腔流派与艺术风格', 0);
INSERT INTO `content` VALUES (226, '传统派与创新派对比', 'culture', NULL, 'article', '分析秦腔传统派与现代创新派的差异。', NULL, 'https://example.com/culture4.jpg', 39024, 1801, '2026-04-14 20:34:30', '2026-05-06 18:12:46', '秦腔流派与艺术风格', 0);
INSERT INTO `content` VALUES (227, '生旦净丑详解', 'culture', NULL, 'article', '秦腔行当体系分明，各具特色。', NULL, 'https://example.com/culture5.jpg', 61004, 3900, '2026-04-14 20:34:30', '2026-05-06 18:02:03', '行当与角色体系（生旦净丑）', 0);
INSERT INTO `content` VALUES (228, '秦腔角色造型图集', 'culture', NULL, 'image', '展示不同角色的服饰与脸谱。', 'https://example.com/image1.jpg', 'https://example.com/culture6.jpg', 35003, 1800, '2026-04-14 20:34:30', '2026-05-06 18:02:47', '行当与角色体系（生旦净丑）', 0);
INSERT INTO `content` VALUES (229, '《锁麟囊》深度解析', 'culture', 1, 'article', '从剧情、人物到唱腔全面解析经典剧目。', NULL, 'https://example.com/culture7.jpg', 72008, 4502, '2026-04-14 20:34:30', '2026-05-06 18:05:01', '秦腔经典剧目解析', 0);
INSERT INTO `content` VALUES (230, '经典剧目合集视频', 'culture', NULL, 'video', '精选经典剧目片段合集。', 'https://example.com/video2.mp4', 'https://example.com/culture8.jpg', 91002, 6001, '2026-04-14 20:34:30', '2026-05-06 18:22:45', '秦腔经典剧目解析', 0);
INSERT INTO `content` VALUES (231, '秦腔唱腔特点', 'culture', NULL, 'article', '秦腔唱腔高亢激昂，富有表现力。', NULL, 'https://example.com/culture9.jpg', 48002, 2600, '2026-04-14 20:34:30', '2026-04-24 22:11:32', '秦腔音乐与唱腔艺术', 0);
INSERT INTO `content` VALUES (232, '经典唱段欣赏', 'culture', NULL, 'video', '精选经典唱段视频合集。', 'https://example.com/video3.mp4', 'https://example.com/culture10.jpg', 87008, 5301, '2026-04-14 20:34:30', '2026-04-21 14:14:49', '秦腔音乐与唱腔艺术', 0);
INSERT INTO `content` VALUES (233, '秦腔服饰文化', 'culture', NULL, 'article', '秦腔服饰精美，体现传统文化特色。', NULL, 'https://example.com/culture11.jpg', 39000, 2100, '2026-04-14 20:34:30', '2026-04-21 14:14:50', '舞美、服饰与化妆', 0);
INSERT INTO `content` VALUES (234, '脸谱艺术展示', 'culture', NULL, 'image', '秦腔脸谱具有独特艺术价值。', 'https://example.com/image2.jpg', 'https://example.com/culture12.jpg', 44000, 2500, '2026-04-14 20:34:30', '2026-04-21 14:14:51', '舞美、服饰与化妆', 0);
INSERT INTO `content` VALUES (235, '秦腔名家介绍', 'culture', NULL, 'article', '介绍历代秦腔艺术名家及其贡献。', NULL, 'https://example.com/culture13.jpg', 58000, 3400, '2026-04-14 20:34:30', '2026-04-21 14:12:10', '秦腔名家与艺术家', 0);
INSERT INTO `content` VALUES (236, '秦腔剧团发展史', 'culture', NULL, 'article', '介绍各大秦腔剧团的发展历程。', NULL, 'https://example.com/culture14.jpg', 31000, 1500, '2026-04-14 20:34:30', '2026-04-21 14:14:53', '剧团与传承机构', 0);
INSERT INTO `content` VALUES (237, '秦腔文化节回顾', 'culture', NULL, 'article', '总结近年来重要秦腔文化活动。', NULL, 'https://example.com/culture15.jpg', 67000, 4200, '2026-04-14 20:34:30', '2026-04-21 14:14:53', '秦腔文化活动', 0);
INSERT INTO `content` VALUES (238, '秦腔比赛现场', 'culture', NULL, 'video', '记录秦腔比赛精彩瞬间。', 'https://example.com/video4.mp4', 'https://example.com/culture16.jpg', 83000, 5100, '2026-04-14 20:34:30', '2026-04-21 14:14:55', '秦腔文化活动', 0);
INSERT INTO `content` VALUES (239, '秦腔数字化发展', 'culture', NULL, 'article', '探讨秦腔在互联网时代的传播方式。', NULL, 'https://example.com/culture17.jpg', 45000, 2200, '2026-04-14 20:34:30', '2026-04-21 14:14:56', '秦腔数字化与多媒体资料', 0);
INSERT INTO `content` VALUES (240, '秦腔数字资源展示', 'culture', NULL, 'video', '展示数字化秦腔资源。', 'https://example.com/video5.mp4', 'https://example.com/culture18.jpg', 76000, 4801, '2026-04-14 20:34:30', '2026-04-21 14:14:57', '秦腔数字化与多媒体资料', 0);
INSERT INTO `content` VALUES (241, '秦腔唱腔特点', 'culture', NULL, 'article', '秦腔唱腔高亢激昂，富有表现力。', NULL, 'https://example.com/culture9.jpg', 24, 0, '2026-04-16 22:08:25', '2026-05-06 18:14:10', '秦腔音乐与唱腔艺术', 0);
INSERT INTO `content` VALUES (242, '秦腔唱腔特点', 'culture', NULL, 'article', '秦腔唱腔高亢激昂，富有表现力。', NULL, '', 3, 0, '2026-04-16 22:08:42', '2026-05-11 18:10:14', '秦腔音乐与唱腔艺术', 0);
INSERT INTO `content` VALUES (243, '秦腔唱腔特点', 'culture', NULL, 'article', '秦腔唱腔高亢激昂，富有表现力。1111', NULL, '', 0, 0, '2026-04-16 22:08:56', '2026-04-21 15:13:00', '剧目赏析', NULL);
INSERT INTO `content` VALUES (246, '1', 'culture', NULL, 'article', '1111111', NULL, '', 24, 0, '2026-04-21 15:29:51', '2026-05-06 18:19:07', '秦腔历史发展', 0);
INSERT INTO `content` VALUES (247, '22211', 'culture', NULL, 'article', '1111111111', NULL, '/upload/culture/2e45416d-8cf0-4100-a61b-793597d08841.jpeg', 0, 0, '2026-04-21 15:40:19', '2026-05-08 14:14:18', '秦腔流派与艺术风格', 1);

-- ----------------------------
-- Table structure for content_stats
-- ----------------------------
DROP TABLE IF EXISTS `content_stats`;
CREATE TABLE `content_stats`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content_id` bigint NULL DEFAULT NULL,
  `view_count` int NULL DEFAULT 0,
  `like_count` int NULL DEFAULT 0,
  `comment_count` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_content`(`content_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_stats
-- ----------------------------
INSERT INTO `content_stats` VALUES (1, 101, 10, 2, 1);
INSERT INTO `content_stats` VALUES (2, 102, 50, 10, 5);
INSERT INTO `content_stats` VALUES (3, 103, 20, 3, 0);
INSERT INTO `content_stats` VALUES (4, 201, 15, 1, 0);
INSERT INTO `content_stats` VALUES (5, 202, 40, 8, 2);

-- ----------------------------
-- Table structure for content_tag
-- ----------------------------
DROP TABLE IF EXISTS `content_tag`;
CREATE TABLE `content_tag`  (
  `content_id` bigint NOT NULL,
  `tag_id` bigint NOT NULL,
  PRIMARY KEY (`content_id`, `tag_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of content_tag
-- ----------------------------

-- ----------------------------
-- Table structure for daily_stats
-- ----------------------------
DROP TABLE IF EXISTS `daily_stats`;
CREATE TABLE `daily_stats`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `stat_date` date NOT NULL COMMENT '统计日期',
  `pv` int NULL DEFAULT 0 COMMENT '页面访问量',
  `uv` int NULL DEFAULT 0 COMMENT '独立访客数',
  `new_user_count` int NULL DEFAULT 0 COMMENT '新增用户数',
  `user_total_count` int NULL DEFAULT 0 COMMENT '用户总数',
  `drama_count` int NULL DEFAULT 0 COMMENT '剧目总数',
  `content_count` int NULL DEFAULT 0 COMMENT '内容总数',
  `like_count` int NULL DEFAULT 0 COMMENT '当日点赞数',
  `comment_count` int NULL DEFAULT 0 COMMENT '当日评论数',
  `favorite_count` int NULL DEFAULT 0 COMMENT '当日收藏数',
  `share_count` int NULL DEFAULT 0 COMMENT '当日分享数',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `view_count` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_date`(`stat_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of daily_stats
-- ----------------------------
INSERT INTO `daily_stats` VALUES (1, '2026-04-15', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '2026-04-15 22:14:02', '2026-04-15 22:14:02', NULL);
INSERT INTO `daily_stats` VALUES (2, '2026-04-16', 0, 0, 0, 3, 20, 43, 0, 0, 0, 0, '2026-04-16 08:13:20', '2026-04-16 08:13:20', NULL);
INSERT INTO `daily_stats` VALUES (3, '2026-04-17', 3, 0, 0, 3, 8, 48, 0, 0, 0, 0, '2026-04-17 15:15:38', '2026-04-17 15:15:38', 1059033);
INSERT INTO `daily_stats` VALUES (4, '2026-05-16', 0, 0, 0, 5, 9, 48, 0, 0, 0, 0, '2026-05-16 01:19:05', '2026-05-16 01:19:05', 1059114);
INSERT INTO `daily_stats` VALUES (5, '2026-06-10', 0, 0, 0, 5, 9, 48, 0, 0, 0, 0, '2026-06-10 01:00:30', '2026-06-10 01:00:30', 1059114);

-- ----------------------------
-- Table structure for drama
-- ----------------------------
DROP TABLE IF EXISTS `drama`;
CREATE TABLE `drama`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '剧目ID',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '剧目名称，例如《锁麟囊》',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '剧目封面图URL',
  `intro` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '剧情简介/剧目介绍',
  `category_id` bigint NULL DEFAULT NULL COMMENT '剧目分类ID（例如传统剧目、新编剧目等）',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1=上线（可展示），0=下线',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量 / 点击量，用于统计剧目热度',
  `publish_date` date NULL DEFAULT NULL COMMENT '剧目首次公开或上线日期，可选',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '剧目表：用于管理秦腔剧目的基本资料' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drama
-- ----------------------------
INSERT INTO `drama` VALUES (2, '周仁哭坟11', 'https://video.test/sdx.mp4', '秦腔传统悲剧，表现周仁忠孝两难的内心挣扎，是经典唱段代表。', 2, 1, 29, NULL, '2026-04-14 15:21:24', '2026-06-06 15:41:33', 2);
INSERT INTO `drama` VALUES (15, '柜中缘', 'https://example.com/drama15.jpg', '传统小戏，节奏紧凑，表演细腻。', 3, 1, 79, '1940-01-01', '2026-04-14 15:21:24', '2026-06-06 15:48:18', 2);
INSERT INTO `drama` VALUES (16, '杨门女将', 'https://example.com/drama16.jpg', '杨家将题材经典剧目，人物群像丰富。', 1, 1, 30, '1962-01-01', '2026-04-14 15:21:24', '2026-06-06 15:49:09', 2);
INSERT INTO `drama` VALUES (17, '破洪州', 'https://example.com/drama17.jpg', '武戏代表作，动作性强，舞台表现力突出。', 2, 1, 22, '1978-01-01', '2026-04-14 15:21:24', '2026-05-09 12:32:08', 0);
INSERT INTO `drama` VALUES (18, '大祭桩', 'https://example.com/drama18.jpg', '秦腔悲剧代表作，情感浓烈。', 1, 1, 30, '1951-01-01', '2026-04-14 15:21:24', '2026-05-09 12:32:10', 0);
INSERT INTO `drama` VALUES (19, '蝶恋花', 'https://example.com/drama19.jpg', '现代创作剧目，爱情题材，舞美新颖。', 1, 1, 98, '2012-01-01', '2026-04-14 15:21:24', '2026-05-09 12:32:11', 0);
INSERT INTO `drama` VALUES (20, '苏武牧羊', 'https://example.com/drama20.jpg', '历史题材剧目，展现民族气节与忠诚精神。', 4, 1, 18, '1998-01-01', '2026-04-14 15:21:24', '2026-05-11 18:07:52', 0);
INSERT INTO `drama` VALUES (37, '123', '/upload/drama/7e4a6c2a-32f2-4ca3-94d5-d61616e81a9b.mp4', '1', 3, 0, 17, NULL, '2026-04-17 14:35:36', '2026-05-08 14:14:39', 0);
INSERT INTO `drama` VALUES (38, '11', '/upload/drama/a6801a20-df3d-4db1-9fca-beea082d58a0.mp4', '1111111', 1, 1, 8, NULL, '2026-05-06 14:35:21', '2026-05-08 14:14:55', 1);

-- ----------------------------
-- Table structure for drama_actor
-- ----------------------------
DROP TABLE IF EXISTS `drama_actor`;
CREATE TABLE `drama_actor`  (
  `drama_id` bigint NOT NULL,
  `actor_id` bigint NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`drama_id`, `actor_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drama_actor
-- ----------------------------
INSERT INTO `drama_actor` VALUES (2, 1, '主角（旦角）');
INSERT INTO `drama_actor` VALUES (2, 2, '配角（丑角）');
INSERT INTO `drama_actor` VALUES (2, 3, '配角（二旦）');

-- ----------------------------
-- Table structure for drama_category
-- ----------------------------
DROP TABLE IF EXISTS `drama_category`;
CREATE TABLE `drama_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称，例如：传统剧目、现代剧目、经典折子戏、新编历史剧',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类代码，用于程序识别，如：traditional, modern, classic, new_history',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类描述',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序序号，数字越小越靠前',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1=启用，0=禁用',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类图标URL',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE,
  UNIQUE INDEX `uk_code`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '剧目分类表：管理剧目的分类信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drama_category
-- ----------------------------
INSERT INTO `drama_category` VALUES (1, '传统剧目', 'traditional', '经典秦腔传统剧目', 1, 1, NULL, '2026-04-16 09:28:52', '2026-04-16 09:28:52');
INSERT INTO `drama_category` VALUES (2, '现代剧目', 'modern', '反映现代题材的秦腔剧目', 2, 1, NULL, '2026-04-16 09:28:52', '2026-04-16 09:28:52');
INSERT INTO `drama_category` VALUES (3, '经典折子戏', 'classic_opera', '秦腔经典折子戏选段', 3, 1, NULL, '2026-04-16 09:28:52', '2026-04-16 09:28:52');
INSERT INTO `drama_category` VALUES (4, '新编历史剧', 'new_history', '新编历史题材剧目', 4, 1, NULL, '2026-04-16 09:28:52', '2026-04-16 09:28:52');

-- ----------------------------
-- Table structure for drama_content
-- ----------------------------
DROP TABLE IF EXISTS `drama_content`;
CREATE TABLE `drama_content`  (
  `drama_id` bigint NOT NULL,
  `content_id` bigint NOT NULL,
  PRIMARY KEY (`drama_id`, `content_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drama_content
-- ----------------------------
INSERT INTO `drama_content` VALUES (1, 101);
INSERT INTO `drama_content` VALUES (1, 102);
INSERT INTO `drama_content` VALUES (1, 103);
INSERT INTO `drama_content` VALUES (2, 201);
INSERT INTO `drama_content` VALUES (2, 202);

-- ----------------------------
-- Table structure for drama_stats
-- ----------------------------
DROP TABLE IF EXISTS `drama_stats`;
CREATE TABLE `drama_stats`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `drama_id` bigint NULL DEFAULT NULL,
  `view_count` int NULL DEFAULT 0,
  `popularity_score` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_drama`(`drama_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of drama_stats
-- ----------------------------
INSERT INTO `drama_stats` VALUES (1, 1, 100, 85);
INSERT INTO `drama_stats` VALUES (2, 2, 80, 70);

-- ----------------------------
-- Table structure for favorite
-- ----------------------------
DROP TABLE IF EXISTS `favorite`;
CREATE TABLE `favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `target_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_fav`(`user_id` ASC, `target_type` ASC, `target_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of favorite
-- ----------------------------
INSERT INTO `favorite` VALUES (1, 1, 'drama', 1, '2026-04-15 00:30:28');
INSERT INTO `favorite` VALUES (2, 1, 'drama', 2, '2026-04-15 00:30:41');

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `target_id` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_user_target`(`user_id` ASC, `target_type` ASC, `target_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of like
-- ----------------------------
INSERT INTO `like` VALUES (1, 1, 'drama', 1, '2026-04-15 00:30:27');
INSERT INTO `like` VALUES (2, 1, 'drama', 2, '2026-04-15 00:30:40');

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NULL DEFAULT NULL,
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `device` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `login_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------
INSERT INTO `login_log` VALUES (1, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-04-24 21:46:56');
INSERT INTO `login_log` VALUES (2, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-06 11:59:44');
INSERT INTO `login_log` VALUES (3, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-06 16:57:55');
INSERT INTO `login_log` VALUES (4, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-06 17:10:25');
INSERT INTO `login_log` VALUES (5, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-06 17:11:32');
INSERT INTO `login_log` VALUES (6, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT; Windows NT 10.0; zh-CN) WindowsPowerShell/5.1.26100.8115', '2026-05-06 17:13:05');
INSERT INTO `login_log` VALUES (7, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT; Windows NT 10.0; zh-CN) WindowsPowerShell/5.1.26100.8115', '2026-05-06 17:15:06');
INSERT INTO `login_log` VALUES (8, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT; Windows NT 10.0; zh-CN) WindowsPowerShell/5.1.26100.8115', '2026-05-06 17:19:30');
INSERT INTO `login_log` VALUES (9, 11, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/147.0.0.0 Safari/537.36', '2026-05-06 17:32:36');
INSERT INTO `login_log` VALUES (10, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36 Edg/148.0.0.0', '2026-05-11 16:13:13');
INSERT INTO `login_log` VALUES (11, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36 Edg/148.0.0.0', '2026-05-11 17:50:11');
INSERT INTO `login_log` VALUES (12, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36', '2026-06-06 15:41:31');
INSERT INTO `login_log` VALUES (13, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/148.0.0.0 Safari/537.36 Edg/148.0.0.0', '2026-06-06 15:49:03');
INSERT INTO `login_log` VALUES (14, 2, '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Code/1.121.0 Chrome/142.0.7444.265 Electron/39.8.8 Safari/537.36', '2026-06-06 17:27:15');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` bigint NULL DEFAULT 0,
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int NULL DEFAULT 0 COMMENT '同一层级数据的显示顺序（排序权重）',
  `visible` tinyint NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '数据大屏', 'home', '/src/components/home.vue', NULL, 0, 'home:home:list', 1, 1);
INSERT INTO `menu` VALUES (2, '统计分析', 'analysis', NULL, 'DataAnalysis', 0, NULL, 2, 1);
INSERT INTO `menu` VALUES (3, '剧目热度', 'analysis/drama', '/src/components/Analysis/Drama/index.vue', NULL, 2, 'analysis:drama:list', 1, 1);
INSERT INTO `menu` VALUES (4, '用户画像', 'analysis/user', '/src/components/Analysis/User/index.vue', NULL, 2, 'analysis:user:list', 2, 1);
INSERT INTO `menu` VALUES (5, '系统管理', '/system', NULL, 'Setting', 0, NULL, 3, 1);
INSERT INTO `menu` VALUES (6, '用户管理', 'system/user', '/src/components/System/User/index.vue', NULL, 3, 'system:user:list', 1, 1);
INSERT INTO `menu` VALUES (7, '内容管理', '/contents', NULL, 'Document', 0, NULL, 4, 1);
INSERT INTO `menu` VALUES (8, '剧目管理', 'contents/drama', '/src/components/Contents/Drama/index.vue', NULL, 4, 'contents:drama:list', 1, 1);
INSERT INTO `menu` VALUES (9, '文化管理', 'contents/article', '/src/components/Contents/Article/index.vue', NULL, 4, 'contents:article:list', 2, 1);
INSERT INTO `menu` VALUES (10, '名家管理', 'contents/actor', '/src/components/Contents/Actor/index.vue', NULL, 4, 'contents:article:list', 3, 1);
INSERT INTO `menu` VALUES (11, '资讯管理', 'contents/profile', '/src/components/Contents/Profile/index.vue', NULL, 4, 'contents:article:list', 4, 1);
INSERT INTO `menu` VALUES (12, '推荐系统', '/recommend', NULL, 'Star', 0, NULL, 5, 1);
INSERT INTO `menu` VALUES (13, '推荐算法配置', 'recommend/config', '/src/components/Recommend/Config/index.vue', NULL, 5, 'recommend:config:list', 1, 1);
INSERT INTO `menu` VALUES (14, '推荐命中率统计', 'recommend/stat', '/src/components/Recommend/Stat/index.vue', NULL, 5, 'recommend:stat:list', 2, 1);
INSERT INTO `menu` VALUES (15, '评论审核', '/comment', NULL, 'ChatLineSquare', 0, NULL, 6, 1);
INSERT INTO `menu` VALUES (16, '评论列表', 'comment/list', '/src/components/Comment/List/index.vue', NULL, 6, 'comment:list', 1, 1);
INSERT INTO `menu` VALUES (17, '审核操作', 'comment/audit', '/src/components/Comment/Audit/index.vue', NULL, 6, 'comment:audit:list', 2, 1);
INSERT INTO `menu` VALUES (18, '个人管理', '/mine', NULL, 'Mine', 0, NULL, 7, 1);
INSERT INTO `menu` VALUES (19, '个人中心', 'mine/center', '/src/components/Mine/Center/index.vue', NULL, 7, 'mine:list', 1, 1);

-- ----------------------------
-- Table structure for news
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '新闻ID',
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '资讯标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '正文内容（富文本/图文混排）',
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源：爬虫/人工录入/公众号等',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面图 URL',
  `view_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `like_count` int NULL DEFAULT 0 COMMENT '点赞量',
  `status` tinyint NULL DEFAULT 0 COMMENT '状态：0=正常 1=下架',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_create_time`(`create_time` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '秦腔资讯表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news
-- ----------------------------
INSERT INTO `news` VALUES (1, '秦腔艺术节在西安盛大开幕', '来自全国各地的秦腔剧团齐聚西安，共同参与本届艺术节，展示传统戏曲魅力。', '人民网', 'https://example.com/news1.jpg', 23, 1, 1, '2026-04-14 21:36:15', '2026-04-15 09:58:29', 'performance');
INSERT INTO `news` VALUES (2, '陕西秦腔剧院公布春季演出安排', '《锁麟囊》《三滴血》等经典剧目将在本月轮番上演。', '陕西日报', 'https://example.com/news2.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 13:33:39', 'performance');
INSERT INTO `news` VALUES (3, '全国秦腔票友大赛正式启动报名', '本届大赛面向全国秦腔爱好者，报名通道已开启。', '文化部官网', 'https://example.com/news3.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:57', 'competition');
INSERT INTO `news` VALUES (4, '关于举办秦腔培训班的通知', '本次培训班面向青年演员，重点提升唱腔与表演技巧。', '官方公告', 'https://example.com/news4.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:56', 'announcement');
INSERT INTO `news` VALUES (5, '秦腔艺术走进校园活动圆满结束', '通过互动教学，让学生近距离感受传统戏曲魅力。', '教育局', 'https://example.com/news5.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-04-24 22:07:05', 'news');
INSERT INTO `news` VALUES (6, '国家级非遗秦腔保护项目推进情况', '秦腔作为国家级非遗项目，正在进行系统化保护与传承。', '新华社', 'https://example.com/news6.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:55', 'heritage');
INSERT INTO `news` VALUES (7, '秦腔唱腔艺术研究取得新成果', '专家团队对秦腔板式结构进行了系统分析。', '学术期刊', 'https://example.com/news7.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:55', 'academic');
INSERT INTO `news` VALUES (8, '秦腔文化交流活动在兰州举办', '多地剧团参与交流演出，促进区域文化融合。', '地方媒体', 'https://example.com/news8.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-04-15 10:13:53', 'exchange');
INSERT INTO `news` VALUES (9, '秦腔名家专访：传承与创新并重', '著名演员分享其艺术生涯与创作心得。', '访谈栏目', 'https://example.com/news9.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:54', 'interview');
INSERT INTO `news` VALUES (10, '央视专题报道秦腔艺术发展', '央视推出专题节目，深入探访秦腔文化。', '央视网', 'https://example.com/news10.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:53', 'media');
INSERT INTO `news` VALUES (11, '秦腔进社区公益演出活动开展', '演员深入基层，为群众带来经典剧目演出。', '社区新闻', 'https://example.com/news11.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:52', 'news');
INSERT INTO `news` VALUES (12, '第十六届秦腔艺术节筹备工作启动', '预计将邀请50余家剧团参演。', '文化厅', 'https://example.com/news12.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-04-24 22:07:29', 'performance');
INSERT INTO `news` VALUES (13, '秦腔青年演员大赛圆满落幕', '多位优秀青年演员脱颖而出。', '赛事组委会', 'https://example.com/news13.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:51', 'competition');
INSERT INTO `news` VALUES (14, '关于剧场开放时间调整的公告', '因设备维护，剧场开放时间有所调整。', '剧院公告', 'https://example.com/news14.jpg', 0, 0, 1, '2026-04-14 21:36:15', '2026-04-15 09:46:50', 'announcement');
INSERT INTO `news` VALUES (15, '秦腔经典剧目巡演走进西南地区', '扩大秦腔影响力，促进文化传播。', '文旅部', 'https://example.com/news15.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-04-24 22:08:08', 'news');
INSERT INTO `news` VALUES (16, '秦腔非遗传承人名单公布', '多位艺术家入选新一批非遗传承人名单。', '文化部', 'https://example.com/news16.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-04-24 22:03:15', 'heritage');
INSERT INTO `news` VALUES (17, '秦腔音乐结构研究论文发布', '深入分析秦腔音乐体系与演变。', '学术机构', 'https://example.com/news17.jpg', 1, 0, 1, '2026-04-14 21:36:15', '2026-05-06 16:44:18', 'academic');
INSERT INTO `news` VALUES (18, '秦腔国际交流演出在新加坡举行', '展示中国传统戏曲魅力，获得海外观众好评。', '国际媒体', 'https://example.com/news18.jpg', 9, 0, 1, '2026-04-14 21:36:15', '2026-05-06 17:10:33', 'exchange');
INSERT INTO `news` VALUES (19, '专访：青年演员如何传承秦腔艺术', '新一代演员分享学习与成长经历。', '访谈节目', 'https://example.com/news19.jpg', 0, 0, 0, '2026-04-14 21:36:15', '2026-04-24 20:52:02', 'interview');
INSERT INTO `news` VALUES (20, '多家媒体聚焦秦腔文化复兴', '传统戏曲在新时代焕发新活力。', '综合报道', 'https://example.com/news20.jpg', 2, 0, 0, '2026-04-14 21:36:15', '2026-05-11 17:58:37', 'media');
INSERT INTO `news` VALUES (34, '11', '111111111111', '111', '/upload/culture/0c9697e0-3f3f-4acc-b21b-fea40c7e5170.jpg', 0, 0, 0, '2026-05-06 14:52:31', '2026-05-08 14:15:27', '');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签名称',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '内容标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '经典', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (2, '热门', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (3, '新编', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (4, '代表作', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (5, '必看', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (6, '高分推荐', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (7, '冷门佳作', '2026-04-11 18:20:15');
INSERT INTO `tag` VALUES (8, '爱情', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (9, '历史', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (10, '家庭伦理', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (11, '英雄传奇', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (12, '民间故事', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (13, '宫廷故事', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (14, '悲剧', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (15, '喜剧', '2026-04-11 18:20:22');
INSERT INTO `tag` VALUES (16, '秦腔', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (17, '传统戏曲', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (18, '非遗文化', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (19, '高音唱腔', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (20, '西北风格', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (21, '舞台艺术', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (22, '唱念做打', '2026-04-11 18:20:28');
INSERT INTO `tag` VALUES (23, '入门推荐', '2026-04-11 18:20:36');
INSERT INTO `tag` VALUES (24, '进阶学习', '2026-04-11 18:20:36');
INSERT INTO `tag` VALUES (25, '老戏迷', '2026-04-11 18:20:36');
INSERT INTO `tag` VALUES (26, '新手推荐', '2026-04-11 18:20:36');
INSERT INTO `tag` VALUES (27, '经典回顾', '2026-04-11 18:20:36');
INSERT INTO `tag` VALUES (28, '热门推荐', '2026-04-11 18:20:36');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'user' COMMENT 'user：普通用户，admin：管理员',
  `status` tinyint NULL DEFAULT 1 COMMENT '0禁用 1正常 2注销',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '0:未知，1：女，2：男',
  `birthday` datetime NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'admin', '12345678', 'admin', '/upload/avatar/bd156591-d1d2-4acd-ac36-d87eec4b97e0.jpeg', 'admin', 1, '2026-04-12 12:46:31', '2026-05-08 14:15:42', '0', '2025-12-17 00:00:00', NULL, NULL);
INSERT INTO `user` VALUES (10, 'user1234', '12345678', '123', NULL, 'user', 1, '2026-04-16 12:14:35', '2026-04-24 21:44:26', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (11, 'user', '12345678', '123456', '/upload/avatar/9394479f-1405-4754-bd8f-44f5383ad272.jpg', 'user', 1, '2026-04-16 12:17:38', '2026-06-09 14:22:09', NULL, NULL, '天津市-市辖区-河西区', NULL);
INSERT INTO `user` VALUES (12, 'editor', '12345678', 'editor', '/upload/avatar/9394479f-1405-4754-bd8f-44f5383ad272.jpg', 'editor', 1, '2026-05-09 10:35:50', '2026-05-09 10:36:05', NULL, NULL, NULL, NULL);
INSERT INTO `user` VALUES (13, 'viewer', '12345678', 'viewer', '/upload/avatar/9394479f-1405-4754-bd8f-44f5383ad272.jpg', 'viewer', 1, '2026-05-09 10:36:28', '2026-05-09 10:36:28', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for user_behavior
-- ----------------------------
DROP TABLE IF EXISTS `user_behavior`;
CREATE TABLE `user_behavior`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID，唯一标识一条用户行为记录',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID，表示是谁产生的行为（关联user表）',
  `target_id` bigint NULL DEFAULT NULL COMMENT '目标ID，被操作的内容ID（如drama/actor/news/culture的主键）',
  `target_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目标类型：drama=剧目 / actor=演员 / news=新闻 / culture=文化内容',
  `action` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '行为类型：view=浏览 / like=点赞 / favorite=收藏',
  `status` int NULL DEFAULT NULL COMMENT '状态：1=有效（已点赞/收藏），0=取消',
  `create_time` datetime NULL DEFAULT NULL COMMENT '行为发生时间，用于统计、推荐排序、热度计算',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户行为记录表（用于点赞、收藏、浏览、推荐系统数据来源）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_behavior
-- ----------------------------
INSERT INTO `user_behavior` VALUES (1, 1, 1, 'drama', 'view', 1, '2026-04-15 09:09:18', '2026-04-16 13:38:45');
INSERT INTO `user_behavior` VALUES (2, 1, 1, 'drama', 'like', 0, '2026-04-15 09:09:19', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (3, 1, 1, 'drama', 'favorite', 0, '2026-04-15 09:09:49', '2026-04-15 13:07:43');
INSERT INTO `user_behavior` VALUES (4, 1, 18, 'drama', 'view', 0, '2026-04-15 09:10:03', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (5, 1, 18, 'actor', 'view', 0, '2026-04-15 09:10:03', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (6, 1, 18, 'actor', 'like', 0, '2026-04-15 09:10:05', '2026-04-15 11:11:11');
INSERT INTO `user_behavior` VALUES (7, 1, 18, 'actor', 'favorite', 0, '2026-04-15 09:10:06', '2026-04-15 13:07:43');
INSERT INTO `user_behavior` VALUES (8, 1, 2, 'drama', 'view', 1, '2026-04-15 09:29:53', '2026-04-16 17:53:37');
INSERT INTO `user_behavior` VALUES (11, 1, 20, 'news', 'view', 0, '2026-04-15 09:36:13', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (12, 1, 20, 'drama', 'view', 0, '2026-04-15 09:36:13', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (13, 1, 20, 'news', 'like', 0, '2026-04-15 09:36:35', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (14, 1, 20, 'news', 'favorite', 0, '2026-04-15 09:36:37', '2026-04-15 13:07:41');
INSERT INTO `user_behavior` VALUES (15, 1, 1, 'news', 'view', 0, '2026-04-15 09:37:52', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (16, 1, 1, 'news', 'like', 0, '2026-04-15 09:38:02', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (17, 1, 1, 'news', 'favorite', 0, '2026-04-15 09:38:04', '2026-04-15 13:07:41');
INSERT INTO `user_behavior` VALUES (18, 1, 8, 'news', 'view', 0, '2026-04-15 10:13:53', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (19, 1, 2, 'drama', 'like', 1, '2026-04-15 10:18:48', '2026-04-15 13:13:13');
INSERT INTO `user_behavior` VALUES (20, 1, 8, 'drama', 'like', 0, '2026-04-15 10:18:56', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (21, 1, 240, 'culture', 'like', 0, '2026-04-15 10:21:16', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (22, 1, 240, 'culture', 'favorite', 0, '2026-04-15 10:21:18', '2026-04-15 13:07:40');
INSERT INTO `user_behavior` VALUES (23, 1, 230, 'culture', 'like', 0, '2026-04-15 10:24:36', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (24, 1, 226, 'culture', 'like', 0, '2026-04-15 10:25:16', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (25, 1, 226, 'culture', 'favorite', 0, '2026-04-15 10:25:18', '2026-04-15 13:07:38');
INSERT INTO `user_behavior` VALUES (26, 1, 226, 'culture', 'view', 0, '2026-04-15 10:30:25', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (27, 1, 232, 'culture', 'view', 0, '2026-04-15 10:30:33', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (28, 1, 232, 'culture', 'like', 0, '2026-04-15 10:30:35', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (29, 1, 229, 'culture', 'view', 1, '2026-04-15 10:39:10', '2026-05-06 16:28:39');
INSERT INTO `user_behavior` VALUES (30, 1, 229, 'culture', 'like', 0, '2026-04-15 10:39:13', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (31, 1, 229, 'culture', 'favorite', 0, '2026-04-15 11:00:24', '2026-04-15 13:07:38');
INSERT INTO `user_behavior` VALUES (32, 1, 20, 'culture', 'view', 0, '2026-04-15 11:00:47', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (33, 1, 20, 'actor', 'like', 0, '2026-04-15 11:00:59', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (34, 1, 1, 'culture', 'view', 0, '2026-04-15 11:01:18', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (35, 1, 2, 'culture', 'view', 0, '2026-04-15 11:01:53', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (36, 1, 7, 'drama', 'view', 0, '2026-04-15 11:10:42', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (37, 1, 7, 'drama', 'like', 0, '2026-04-15 11:10:44', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (38, 1, 10, 'drama', 'view', 0, '2026-04-15 11:10:48', '2026-04-15 13:07:51');
INSERT INTO `user_behavior` VALUES (39, 1, 10, 'drama', 'like', 0, '2026-04-15 11:10:50', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (40, 1, 19, 'drama', 'view', 1, '2026-04-15 11:11:01', '2026-04-15 13:08:09');
INSERT INTO `user_behavior` VALUES (41, 1, 19, 'actor', 'like', 0, '2026-04-15 11:11:03', '2026-04-15 13:07:57');
INSERT INTO `user_behavior` VALUES (42, 1, 4, 'drama', 'view', 0, '2026-04-15 11:22:24', '2026-04-15 13:07:49');
INSERT INTO `user_behavior` VALUES (43, 1, 19, 'actor', 'view', 1, '2026-04-15 11:30:35', '2026-04-15 13:08:09');
INSERT INTO `user_behavior` VALUES (44, 1, 2, 'actor', 'view', 1, '2026-04-15 13:08:24', '2026-04-15 13:08:24');
INSERT INTO `user_behavior` VALUES (45, 1, 2, 'drama', 'favorite', 1, '2026-04-15 13:08:25', '2026-04-15 13:08:25');
INSERT INTO `user_behavior` VALUES (46, 1, 3, 'drama', 'view', 1, '2026-04-15 13:08:39', '2026-04-15 13:08:39');
INSERT INTO `user_behavior` VALUES (47, 1, 3, 'drama', 'favorite', 1, '2026-04-15 13:08:40', '2026-04-15 13:08:40');
INSERT INTO `user_behavior` VALUES (48, 1, 5, 'drama', 'view', 1, '2026-04-15 13:10:51', '2026-04-15 13:18:27');
INSERT INTO `user_behavior` VALUES (49, 1, 5, 'drama', 'favorite', 0, '2026-04-15 13:10:52', '2026-04-15 13:11:03');
INSERT INTO `user_behavior` VALUES (50, 1, 8, 'drama', 'view', 1, '2026-04-16 13:38:56', '2026-04-16 13:39:01');
INSERT INTO `user_behavior` VALUES (51, 1, 14, 'drama', 'view', 1, '2026-04-16 13:42:21', '2026-04-16 17:17:45');
INSERT INTO `user_behavior` VALUES (52, 1, 21, 'drama', 'view', 1, '2026-04-16 14:40:02', '2026-04-16 15:08:15');
INSERT INTO `user_behavior` VALUES (53, 1, 12, 'drama', 'view', 1, '2026-04-16 14:52:26', '2026-04-16 17:17:34');
INSERT INTO `user_behavior` VALUES (54, 1, 17, 'drama', 'view', 1, '2026-04-16 15:08:08', '2026-04-16 15:08:08');
INSERT INTO `user_behavior` VALUES (55, 1, 22, 'drama', 'view', 1, '2026-04-16 15:08:48', '2026-04-16 15:08:48');
INSERT INTO `user_behavior` VALUES (56, 1, 24, 'drama', 'view', 1, '2026-04-16 15:09:41', '2026-04-16 15:09:41');
INSERT INTO `user_behavior` VALUES (57, 1, 23, 'drama', 'view', 1, '2026-04-16 15:19:33', '2026-04-16 16:04:56');
INSERT INTO `user_behavior` VALUES (58, 1, 26, 'drama', 'view', 1, '2026-04-16 16:05:08', '2026-04-16 16:05:08');
INSERT INTO `user_behavior` VALUES (59, 1, 29, 'drama', 'view', 1, '2026-04-16 18:24:37', '2026-04-16 18:24:37');
INSERT INTO `user_behavior` VALUES (60, 1, 28, 'drama', 'view', 1, '2026-04-17 13:58:50', '2026-04-17 13:58:50');
INSERT INTO `user_behavior` VALUES (61, 1, 31, 'drama', 'view', 1, '2026-04-17 13:59:16', '2026-04-17 13:59:16');
INSERT INTO `user_behavior` VALUES (62, 1, 37, 'drama', 'view', 1, '2026-04-17 14:35:44', '2026-05-06 14:34:43');
INSERT INTO `user_behavior` VALUES (64, 11, 16, 'drama', 'like', 0, '2026-04-24 21:50:15', '2026-04-24 21:56:20');
INSERT INTO `user_behavior` VALUES (66, 1, 16, 'news', 'view', 1, '2026-04-24 22:03:15', '2026-04-24 22:03:15');
INSERT INTO `user_behavior` VALUES (68, 1, 224, 'culture', 'view', 1, '2026-04-24 22:06:21', '2026-04-24 22:06:21');
INSERT INTO `user_behavior` VALUES (69, 1, 5, 'news', 'view', 1, '2026-04-24 22:07:05', '2026-04-24 22:07:05');
INSERT INTO `user_behavior` VALUES (71, 1, 5, 'culture', 'view', 1, '2026-04-24 22:07:05', '2026-04-24 22:07:05');
INSERT INTO `user_behavior` VALUES (72, 1, 12, 'news', 'view', 1, '2026-04-24 22:07:29', '2026-04-24 22:07:29');
INSERT INTO `user_behavior` VALUES (74, 1, 12, 'culture', 'view', 1, '2026-04-24 22:07:29', '2026-04-24 22:07:29');
INSERT INTO `user_behavior` VALUES (75, 1, 15, 'news', 'view', 1, '2026-04-24 22:08:08', '2026-04-24 22:08:08');
INSERT INTO `user_behavior` VALUES (77, 1, 15, 'culture', 'view', 1, '2026-04-24 22:08:08', '2026-05-06 12:24:17');
INSERT INTO `user_behavior` VALUES (79, 1, 18, 'news', 'view', 1, '2026-04-24 22:09:36', '2026-05-06 17:10:33');
INSERT INTO `user_behavior` VALUES (83, 1, 231, 'culture', 'view', 1, '2026-04-24 22:11:32', '2026-04-24 22:11:32');
INSERT INTO `user_behavior` VALUES (89, 1, 241, 'culture', 'view', 1, '2026-05-06 12:23:49', '2026-05-06 16:40:46');
INSERT INTO `user_behavior` VALUES (90, 11, 15, 'drama', 'favorite', 1, '2026-05-06 12:24:04', '2026-05-06 12:24:04');
INSERT INTO `user_behavior` VALUES (91, 11, 15, 'drama', 'like', 1, '2026-05-06 12:24:05', '2026-05-06 12:24:05');
INSERT INTO `user_behavior` VALUES (92, 2, 38, 'drama', 'view', 1, '2026-05-06 15:48:43', '2026-05-06 16:08:40');
INSERT INTO `user_behavior` VALUES (93, 2, 2, 'drama', 'view', 1, '2026-05-06 15:54:56', '2026-06-06 15:41:33');
INSERT INTO `user_behavior` VALUES (94, 2, 38, 'drama', 'like', 1, '2026-05-06 16:00:54', '2026-05-06 16:00:54');
INSERT INTO `user_behavior` VALUES (95, 2, 38, 'drama', 'favorite', 1, '2026-05-06 16:00:55', '2026-05-06 16:00:55');
INSERT INTO `user_behavior` VALUES (96, 2, 20, 'drama', 'view', 1, '2026-05-06 16:05:13', '2026-05-11 18:07:52');
INSERT INTO `user_behavior` VALUES (97, 2, 14, 'actor', 'view', 1, '2026-05-06 16:14:40', '2026-05-06 16:26:46');
INSERT INTO `user_behavior` VALUES (98, 2, 12, 'actor', 'view', 1, '2026-05-06 16:22:12', '2026-05-06 17:10:58');
INSERT INTO `user_behavior` VALUES (99, 2, 14, 'actor', 'like', 0, '2026-05-06 16:25:14', '2026-05-06 16:26:42');
INSERT INTO `user_behavior` VALUES (100, 2, 14, 'actor', 'favorite', 1, '2026-05-06 16:25:15', '2026-05-06 16:26:24');
INSERT INTO `user_behavior` VALUES (101, 2, 246, 'actor', 'view', 1, '2026-05-06 16:28:31', '2026-05-06 16:28:31');
INSERT INTO `user_behavior` VALUES (102, 2, 246, 'culture', 'view', 1, '2026-05-06 16:28:31', '2026-05-06 16:28:31');
INSERT INTO `user_behavior` VALUES (103, 1, 246, 'culture', 'view', 1, '2026-05-06 16:28:31', '2026-05-06 16:28:31');
INSERT INTO `user_behavior` VALUES (104, 2, 241, 'culture', 'view', 1, '2026-05-06 16:28:36', '2026-05-06 16:40:46');
INSERT INTO `user_behavior` VALUES (105, 2, 241, 'actor', 'view', 1, '2026-05-06 16:28:36', '2026-05-06 16:28:36');
INSERT INTO `user_behavior` VALUES (106, 2, 229, 'culture', 'view', 1, '2026-05-06 16:28:39', '2026-05-06 16:28:39');
INSERT INTO `user_behavior` VALUES (107, 2, 229, 'actor', 'view', 1, '2026-05-06 16:28:39', '2026-05-06 16:28:39');
INSERT INTO `user_behavior` VALUES (108, 2, 229, 'culture', 'like', 1, '2026-05-06 16:28:44', '2026-05-06 16:28:44');
INSERT INTO `user_behavior` VALUES (109, 2, 229, 'culture', 'favorite', 1, '2026-05-06 16:28:46', '2026-05-06 16:28:46');
INSERT INTO `user_behavior` VALUES (110, 2, 18, 'culture', 'view', 1, '2026-05-06 16:44:15', '2026-05-06 16:46:19');
INSERT INTO `user_behavior` VALUES (111, 1, 18, 'culture', 'view', 1, '2026-05-06 16:44:15', '2026-05-06 16:46:19');
INSERT INTO `user_behavior` VALUES (112, 1, 17, 'news', 'view', 1, '2026-05-06 16:44:18', '2026-05-06 16:44:18');
INSERT INTO `user_behavior` VALUES (113, 2, 17, 'culture', 'view', 1, '2026-05-06 16:44:18', '2026-05-06 16:44:18');
INSERT INTO `user_behavior` VALUES (114, 1, 17, 'culture', 'view', 1, '2026-05-06 16:44:18', '2026-05-06 16:44:18');
INSERT INTO `user_behavior` VALUES (115, 2, 18, 'news', 'like', 0, '2026-05-06 16:44:24', '2026-05-06 16:44:24');
INSERT INTO `user_behavior` VALUES (131, 11, 2, 'drama', 'favorite', 1, '2026-05-06 17:55:56', '2026-05-06 17:55:56');
INSERT INTO `user_behavior` VALUES (134, 11, 2, 'drama', 'view', 1, '2026-05-06 18:01:38', '2026-05-06 18:23:14');
INSERT INTO `user_behavior` VALUES (135, 11, 15, 'drama', 'view', 1, '2026-05-06 18:01:42', '2026-05-06 18:01:42');
INSERT INTO `user_behavior` VALUES (136, 11, 16, 'drama', 'view', 1, '2026-05-06 18:01:45', '2026-05-06 18:01:45');
INSERT INTO `user_behavior` VALUES (137, 11, 241, 'drama', 'view', 1, '2026-05-06 18:01:59', '2026-05-06 18:02:25');
INSERT INTO `user_behavior` VALUES (138, 11, 241, 'culture', 'view', 1, '2026-05-06 18:01:59', '2026-05-06 18:14:10');
INSERT INTO `user_behavior` VALUES (139, 11, 227, 'culture', 'view', 1, '2026-05-06 18:02:03', '2026-05-06 18:02:03');
INSERT INTO `user_behavior` VALUES (140, 11, 227, 'drama', 'view', 1, '2026-05-06 18:02:03', '2026-05-06 18:02:03');
INSERT INTO `user_behavior` VALUES (141, 11, 246, 'culture', 'view', 1, '2026-05-06 18:02:15', '2026-05-06 18:19:07');
INSERT INTO `user_behavior` VALUES (142, 11, 246, 'drama', 'view', 1, '2026-05-06 18:02:15', '2026-05-06 18:02:15');
INSERT INTO `user_behavior` VALUES (143, 11, 228, 'culture', 'view', 1, '2026-05-06 18:02:47', '2026-05-06 18:02:47');
INSERT INTO `user_behavior` VALUES (144, 11, 228, 'drama', 'view', 1, '2026-05-06 18:02:47', '2026-05-06 18:02:47');
INSERT INTO `user_behavior` VALUES (145, 11, 229, 'culture', 'view', 1, '2026-05-06 18:05:01', '2026-05-06 18:05:01');
INSERT INTO `user_behavior` VALUES (146, 11, 229, 'drama', 'view', 1, '2026-05-06 18:05:01', '2026-05-06 18:05:01');
INSERT INTO `user_behavior` VALUES (147, 11, 224, 'culture', 'view', 1, '2026-05-06 18:10:33', '2026-05-06 18:12:14');
INSERT INTO `user_behavior` VALUES (148, 11, 224, 'drama', 'view', 1, '2026-05-06 18:10:33', '2026-05-06 18:10:45');
INSERT INTO `user_behavior` VALUES (149, 11, 226, 'culture', 'view', 1, '2026-05-06 18:12:46', '2026-05-06 18:12:46');
INSERT INTO `user_behavior` VALUES (150, 11, 19, 'drama', 'view', 1, '2026-05-06 18:22:13', '2026-05-06 18:22:13');
INSERT INTO `user_behavior` VALUES (151, 11, 18, 'drama', 'view', 1, '2026-05-06 18:22:26', '2026-05-06 18:22:26');
INSERT INTO `user_behavior` VALUES (152, 11, 17, 'drama', 'view', 1, '2026-05-06 18:22:37', '2026-05-06 18:22:37');
INSERT INTO `user_behavior` VALUES (153, 11, 230, 'culture', 'view', 1, '2026-05-06 18:22:45', '2026-05-06 18:22:45');
INSERT INTO `user_behavior` VALUES (154, 2, 20, 'news', 'view', 1, '2026-05-11 17:58:37', '2026-05-11 17:58:37');
INSERT INTO `user_behavior` VALUES (155, 2, 224, 'culture', 'view', 1, '2026-05-11 18:07:22', '2026-05-11 18:07:22');
INSERT INTO `user_behavior` VALUES (156, 2, 242, 'culture', 'view', 1, '2026-05-11 18:09:34', '2026-05-11 18:10:14');
INSERT INTO `user_behavior` VALUES (157, 2, 15, 'drama', 'view', 1, '2026-06-06 15:41:42', '2026-06-06 15:47:49');
INSERT INTO `user_behavior` VALUES (158, 2, 15, 'drama', 'like', 1, '2026-06-06 15:48:18', '2026-06-06 15:48:18');
INSERT INTO `user_behavior` VALUES (159, 2, 15, 'drama', 'favorite', 1, '2026-06-06 15:48:19', '2026-06-06 15:48:19');
INSERT INTO `user_behavior` VALUES (160, 2, 16, 'drama', 'view', 1, '2026-06-06 15:49:08', '2026-06-06 15:49:08');
INSERT INTO `user_behavior` VALUES (161, 2, 16, 'drama', 'like', 1, '2026-06-06 15:49:09', '2026-06-06 15:49:09');
INSERT INTO `user_behavior` VALUES (162, 2, 16, 'drama', 'favorite', 1, '2026-06-06 15:49:10', '2026-06-06 15:49:10');

SET FOREIGN_KEY_CHECKS = 1;
