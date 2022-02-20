/*
 Navicat Premium Data Transfer

 Source Server         : out_source
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : imageserver

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 20/02/2022 00:25:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for image
-- ----------------------------
DROP TABLE IF EXISTS `image`;
CREATE TABLE `image`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKlxnnh0ir05khn8iu9tgwh1yyk`(`user_id`) USING BTREE,
  CONSTRAINT `FK5csfoosboovo3vew4085mt3or` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKlxnnh0ir05khn8iu9tgwh1yyk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image
-- ----------------------------
INSERT INTO `image` VALUES (44, 'IMG_20170611_160346 - ??.jpg', '/upload/9c2a24b2-cfdf-4657-86b5-9f1642df482f.jpg', 7);
INSERT INTO `image` VALUES (45, '1.jpg', '/upload/7fa74431-f1ba-452f-8cf7-cd17cf6e0b28.jpg', 7);
INSERT INTO `image` VALUES (46, 'f3ed8aa4-9d70-442e-a4ff-48a7fbcbdd50.jpg', '/upload/5d1e9c4b-7f35-4814-b410-e75b78aaa788.jpg', 7);
INSERT INTO `image` VALUES (47, 'IMG_20170611_160346.jpg', '/upload/a859c460-8146-4f7d-bd34-dbdaadd44492.jpg', 7);
INSERT INTO `image` VALUES (48, 'IMG_20170613_075738.jpg', '/upload/a0f12ef0-f3b2-4803-9bbe-23785dfce294.jpg', 7);
INSERT INTO `image` VALUES (49, 'IMG_20170611_160346 - 副本.jpg', '/upload/649fe357-c0c6-4800-83c2-fad6035ad461.jpg', 7);
INSERT INTO `image` VALUES (50, 'IMG_20170611_160346 - 副本.jpg', '/upload/f5641f60-6ed6-49e2-a93f-49ef3e87885a.jpg', 7);
INSERT INTO `image` VALUES (51, 'IMG_20170611_160346.jpg', '/upload/e1df91fc-d76f-4a6b-9217-02644130555d.jpg', 7);
INSERT INTO `image` VALUES (52, 'IMG_20170613_075738.jpg', '/upload/acddd1d0-47d1-4ff2-9a31-86e219bc6eac.jpg', 7);
INSERT INTO `image` VALUES (53, 'IMG_20170920_120043.jpg', '/upload/6fe6d566-c2d7-4ef4-ad39-3306b14a09cc.jpg', 7);
INSERT INTO `image` VALUES (54, 'IMG_20170923_165257.jpg', '/upload/1482f482-26c0-45c0-9291-5cd2984bd27d.jpg', 7);
INSERT INTO `image` VALUES (55, 'IMG_20170919_205317_HHT.jpg', '/upload/9179e8a6-2b10-4da6-a583-c6f1af075446.jpg', 7);

-- ----------------------------
-- Table structure for image_comment
-- ----------------------------
DROP TABLE IF EXISTS `image_comment`;
CREATE TABLE `image_comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image_id` int(11) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKm33l4gqbdjsy8r3bwtl4a6imv`(`image_id`) USING BTREE,
  CONSTRAINT `FKm33l4gqbdjsy8r3bwtl4a6imv` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKpjn3lytknpkscmf5nvqna422m` FOREIGN KEY (`image_id`) REFERENCES `image` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of image_comment
-- ----------------------------
INSERT INTO `image_comment` VALUES (10, '阿萨斯', 44, '2022-02-20 00:03:22');
INSERT INTO `image_comment` VALUES (11, '1212', 44, '2022-02-20 00:04:43');
INSERT INTO `image_comment` VALUES (12, '12121212', 44, '2022-02-20 00:04:49');
INSERT INTO `image_comment` VALUES (13, '1212', 44, '2022-02-20 00:05:13');
INSERT INTO `image_comment` VALUES (14, '1212', 44, '2022-02-20 00:05:59');
INSERT INTO `image_comment` VALUES (15, '1212', 44, '2022-02-20 00:06:02');
INSERT INTO `image_comment` VALUES (16, '1212', 45, '2022-02-20 00:06:54');
INSERT INTO `image_comment` VALUES (17, '666', 44, '2022-02-20 00:16:32');
INSERT INTO `image_comment` VALUES (18, '777', 44, '2022-02-20 00:16:54');
INSERT INTO `image_comment` VALUES (19, '888', 44, '2022-02-20 00:17:09');
INSERT INTO `image_comment` VALUES (20, '999', 44, '2022-02-20 00:17:23');
INSERT INTO `image_comment` VALUES (21, '999', 44, '2022-02-20 00:17:49');
INSERT INTO `image_comment` VALUES (22, '000', 44, '2022-02-20 00:18:15');
INSERT INTO `image_comment` VALUES (23, '111', 44, '2022-02-20 00:23:32');
INSERT INTO `image_comment` VALUES (24, '111', 49, '2022-02-20 00:23:51');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(65) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (7, 'test', '123');

SET FOREIGN_KEY_CHECKS = 1;
