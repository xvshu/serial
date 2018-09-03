/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.150
Source Server Version : 50623
Source Host           : 192.168.2.150:3316
Source Database       : eloan_serial

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2018-09-03 16:51:49
*/

SET FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS xsf_serial DEFAULT CHARSET utf8;
use xsf_serial;
-- ----------------------------
-- Table structure for sequence_value
-- ----------------------------
DROP TABLE IF EXISTS `sequence_value`;
CREATE TABLE `sequence_value` (
  `name` varchar(100) NOT NULL COMMENT '表名称',
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '自增加id',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;