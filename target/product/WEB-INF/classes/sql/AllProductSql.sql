/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50067
Source Host           : localhost:3306
Source Database       : product

Target Server Type    : MYSQL
Target Server Version : 50067
File Encoding         : 65001

Date: 2015-09-21 20:44:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bringtakeinfo`
-- ----------------------------
DROP TABLE IF EXISTS `bringtakeinfo`;
CREATE TABLE `bringtakeinfo` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `orgname` varchar(64) default NULL COMMENT '所在单位',
  `tel` int(16) default NULL COMMENT '联系电话',
  `mask` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='送取货人';

-- ----------------------------
-- Records of bringtakeinfo
-- ----------------------------


-- ----------------------------
-- Table structure for `cargoinfo`
-- ----------------------------
DROP TABLE IF EXISTS `cargoinfo`;
CREATE TABLE `cargoinfo` (
  `id` int(11) NOT NULL auto_increment,
  `cargoname` varchar(64) NOT NULL COMMENT '货物名称',
  `org` varchar(128) default NULL COMMENT '单位',
  `irradtype` varchar(32) default NULL COMMENT '辐照方式',
  `irradtime` varchar(32) default NULL COMMENT '辐照时间',
  `timeorg` varchar(32) default NULL COMMENT '时间单位',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `cargoname` (`cargoname`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='货物信息表';

-- ----------------------------
-- Records of cargoinfo
-- ----------------------------


-- ----------------------------
-- Table structure for `chargelog`
-- ----------------------------
DROP TABLE IF EXISTS `chargelog`;
CREATE TABLE `chargelog` (
  `id` int(11) NOT NULL auto_increment,
  `chargeid` int(11) NOT NULL COMMENT '对receivemgrdetail表的那条记录收的款',
  `chargeorg` varchar(128) default NULL COMMENT '交款单位',
  `chargetime` datetime default NULL COMMENT '交款日期',
  `chargeamount` double default NULL COMMENT '交款金额',
  `receivetime` datetime NOT NULL COMMENT '收货日期',
  `cargoname` varchar(128) default NULL COMMENT '货物名称',
  `cargocount` varchar(128) default NULL COMMENT '货物数量',
  `cargoweight` varchar(128) default NULL COMMENT '货物重量',
  `fee` double default NULL COMMENT '费用',
  `paid` double default NULL COMMENT '已付',
  `unpaid` double default NULL COMMENT '未付',
  `mask` varchar(255) default NULL COMMENT '收费说明',
  `operater` varchar(128) default NULL COMMENT '操作员',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='收款记录表';

-- ----------------------------
-- Records of chargelog
-- ----------------------------

-- ----------------------------
-- Table structure for `dict_group`
-- ----------------------------
DROP TABLE IF EXISTS `dict_group`;
CREATE TABLE `dict_group` (
  `groupid` varchar(128) NOT NULL COMMENT '分组编号',
  `groupname` varchar(256) NOT NULL COMMENT '分组名称',
  UNIQUE KEY `groupid` (`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典组表基表';

-- ----------------------------
-- Records of dict_group
-- ----------------------------
INSERT INTO `dict_group` VALUES ('allmenu', '所有菜单');
INSERT INTO `dict_group` VALUES ('irradflags', '辐照类型');
INSERT INTO `dict_group` VALUES ('irradtypes', '辐照方式');
INSERT INTO `dict_group` VALUES ('loadmodel', '辐照装载模式');
INSERT INTO `dict_group` VALUES ('timeorgs', '时间单位');
INSERT INTO `dict_group` VALUES ('paytype', '收费方式');

-- ----------------------------
-- Table structure for `dict_item`
-- ----------------------------
DROP TABLE IF EXISTS `dict_item`;
CREATE TABLE `dict_item` (
  `itemid` varchar(128) default NULL COMMENT '项目编号',
  `groupid` varchar(128) default NULL COMMENT '分组编号',
  `itemname` varchar(256) default NULL COMMENT '项目名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典组表项目表';

-- ----------------------------
-- Records of dict_item
-- ----------------------------
INSERT INTO `dict_item` VALUES ('staticirrad', 'irradtypes', '静态辐射');
INSERT INTO `dict_item` VALUES ('dnyirrad', 'irradtypes', '动态辐射');
INSERT INTO `dict_item` VALUES ('hour', 'timeorgs', '时');
INSERT INTO `dict_item` VALUES ('minute', 'timeorgs', '分');
INSERT INTO `dict_item` VALUES ('firstirrad', 'irradflags', '首次辐射');
INSERT INTO `dict_item` VALUES ('secondirrad', 'irradflags', '重新辐射');
INSERT INTO `dict_item` VALUES ('noirrad', 'irradflags', '不辐射');
INSERT INTO `dict_item` VALUES ('fullload', 'loadmodel', '满载');
INSERT INTO `dict_item` VALUES ('maximum', 'loadmodel', '最大值');
INSERT INTO `dict_item` VALUES ('2', 'allmenu', '货物信息');
INSERT INTO `dict_item` VALUES ('3', 'allmenu', '送货信息');
INSERT INTO `dict_item` VALUES ('4', 'allmenu', '送取货人');
INSERT INTO `dict_item` VALUES ('5', 'allmenu', '单位信息');
INSERT INTO `dict_item` VALUES ('7', 'allmenu', '收货管理');
INSERT INTO `dict_item` VALUES ('8', 'allmenu', '收货确认');
INSERT INTO `dict_item` VALUES ('9', 'allmenu', '辐照管理');
INSERT INTO `dict_item` VALUES ('10', 'allmenu', '出货管理');
INSERT INTO `dict_item` VALUES ('12', 'allmenu', '权限配置');
INSERT INTO `dict_item` VALUES ('15', 'allmenu', '收款');
INSERT INTO `dict_item` VALUES ('17', 'allmenu', '日报表');
INSERT INTO `dict_item` VALUES ('18', 'allmenu', '月报表');
INSERT INTO `dict_item` VALUES ('cash', 'paytype', '现金');
INSERT INTO `dict_item` VALUES ('transfer', 'paytype', '转账');
INSERT INTO `dict_item` VALUES ('other', 'paytype', '其他');

-- ----------------------------
-- Table structure for `irradiation`
-- ----------------------------
DROP TABLE IF EXISTS `irradiation`;
CREATE TABLE `irradiation` (
  `id` int(11) NOT NULL auto_increment,
  `detailid` int(11) default NULL COMMENT '货物详情的主键',
  `baseid` int(11) default NULL COMMENT '货物基本信息表的主键',
  `receiveorgid` varchar(64) default NULL COMMENT '送货单位',
  `cargoname` varchar(64) default NULL COMMENT '货物名称',
  `irradednum` int(11) default NULL COMMENT '辐照数量',
  `countorg` varchar(64) default NULL COMMENT '数量单位',
  `cargoweight` varchar(64) default NULL COMMENT '货物重量',
  `irradtype` varchar(64) default NULL COMMENT '辐照类型',
  `irradtime` varchar(64) default NULL COMMENT '辐照时间',
  `timeorg` varchar(64) default NULL COMMENT '时间单位',
  `doirraddate` date default NULL COMMENT '辐照日期',
  `starttime` datetime default NULL COMMENT '开始时间',
  `position` varchar(64) default NULL COMMENT '吊具号码',
  `takecargocount` varchar(32) default NULL COMMENT '已取货数量',
  `status` varchar(1) default NULL COMMENT '状态',
  `mask` varchar(64) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of irradiation
-- ----------------------------

-- ----------------------------
-- Table structure for `orginfo`
-- ----------------------------
DROP TABLE IF EXISTS `orginfo`;
CREATE TABLE `orginfo` (
  `id` int(11) NOT NULL auto_increment,
  `orgname` varchar(64) NOT NULL COMMENT '计数单位名称',
  `mask` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `orgname` (`orgname`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='单位信息';

-- ----------------------------
-- Records of orginfo
-- ----------------------------


-- ----------------------------
-- Table structure for `product_chart`
-- ----------------------------
DROP TABLE IF EXISTS `product_chart`;
CREATE TABLE `product_chart` (
  `itemid` varchar(64) NOT NULL COMMENT '特性参数的key',
  `itemvalue` varchar(255) default NULL COMMENT '特性参数的value',
  UNIQUE KEY `itemid` (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特性参数表';

-- ----------------------------
-- Records of product_chart
-- ----------------------------
INSERT INTO `product_chart` VALUES ('checkRepeatOfsaveReceive', '1');
INSERT INTO `product_chart` VALUES ('NOPRINTPICTUREDEFAULT', 'noprint.jpg');
INSERT INTO `product_chart` VALUES ('PRINTPICTURESAVEDIR', 'E:/Amusement/新建文件夹 (7)/');

-- ----------------------------
-- Table structure for `product_resources`
-- ----------------------------
DROP TABLE IF EXISTS `product_resources`;
CREATE TABLE `product_resources` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(50) default NULL,
  `parentId` int(11) default NULL,
  `resKey` varchar(50) default NULL,
  `type` varchar(40) default NULL,
  `resUrl` varchar(200) default NULL,
  `level` int(4) default NULL,
  `icon` varchar(100) default NULL,
  `ishide` int(3) default '0',
  `description` varchar(200) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product_resources
-- ----------------------------
INSERT INTO `product_resources` VALUES ('1', '系统基础管理', '0', 'system', '0', '/system/', '1', 'fa-desktop', '0', '系统基础管理');
INSERT INTO `product_resources` VALUES ('2', '货物信息', '1', 'cargoinfo', '1', '/system/cargoinfo.do?pageNow=0&pageSize=20', '2', null, '0', '货物信息');
INSERT INTO `product_resources` VALUES ('3', '送货信息', '1', 'shippinginfo', '1', '/system/shippinginfo.do', '7', 'fa-list', '0', '送货信息');
INSERT INTO `product_resources` VALUES ('4', '送取货人', '1', 'bringtakeinfo', '1', '/system/bringtakeinfo.do', null, null, '0', '送取货人');
INSERT INTO `product_resources` VALUES ('5', '单位信息', '1', 'orginfo', '1', '/system/orginfo.do', '12', 'fa-list-alt', '0', '单位信息');
INSERT INTO `product_resources` VALUES ('6', '业务管理', '0', 'businessmana', '1', '/business/', null, null, '0', '业务管理');
INSERT INTO `product_resources` VALUES ('7', '收货管理', '6', 'receivingmana', '1', '/business/receivingmana.do', null, null, '0', '收货管理');
INSERT INTO `product_resources` VALUES ('8', '收货确认', '6', 'confirmation', '1', '/business/confirmation.do', null, null, '0', '收货确认');
INSERT INTO `product_resources` VALUES ('9', '辐照管理', '6', 'irradiationmana', '1', '/business/Irradiationmana.do', null, null, '0', '辐照管理');
INSERT INTO `product_resources` VALUES ('10', '出货管理', '6', 'shippingmana', '1', '/business/shoppingmana.do', null, null, '0', '出货管理');
INSERT INTO `product_resources` VALUES ('11', '权限管理', '0', 'privilegemana', '1', '/privilegemana/', null, null, '0', '权限管理');
INSERT INTO `product_resources` VALUES ('12', '权限配置', '11', 'privilegeconfig', '1', '/privilegemana/configuration.do', null, null, '0', '权限配置');
INSERT INTO `product_resources` VALUES ('14', '收款管理', '0', 'collectionmana', '1', '/collection/', null, null, '0', '收款管理');
INSERT INTO `product_resources` VALUES ('15', '收款', '14', 'collectioninit', '1', '/collection/init.do', null, null, '0', '收款');
INSERT INTO `product_resources` VALUES ('16', '报表管理', '0', 'report', '1', '/report/', null, null, '0', '报表管理');
INSERT INTO `product_resources` VALUES ('17', '日报表', '16', 'dayreport', '1', '/report/dayreport.do', null, null, '0', '日报表');
INSERT INTO `product_resources` VALUES ('18', '月报表', '16', 'monthreport', '1', '/report/monthreport.do', null, null, '0', '月报表');

-- ----------------------------
-- Table structure for `receivemgrbase`
-- ----------------------------
DROP TABLE IF EXISTS `receivemgrbase`;
CREATE TABLE `receivemgrbase` (
  `id` int(11) NOT NULL auto_increment,
  `receivetime` datetime NOT NULL COMMENT '收货日期',
  `receiveorgid` varchar(64) NOT NULL COMMENT '送货单位 shippinginfo：orgname',
  `receivepeopleid` varchar(32) NOT NULL COMMENT '送货人 bringtakeinfo：name',
  `telnum` varchar(16) NOT NULL COMMENT '联系电话',
  `status` varchar(1) default NULL COMMENT '是否指纹确认 0:未指纹确认，1:已指纹确认',
  `userid` varchar(32) default NULL COMMENT '操作员编号',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='收货管理基本信息表';

-- ----------------------------
-- Records of receivemgrbase
-- ----------------------------


-- ----------------------------
-- Table structure for `receivemgrdetail`
-- ----------------------------
DROP TABLE IF EXISTS `receivemgrdetail`;
CREATE TABLE `receivemgrdetail` (
  `id` int(11) NOT NULL auto_increment,
  `receivemgrid` int(11) default NULL COMMENT '收获管理基本信息id ReceiveMgrBase：id',
  `cargoid` varchar(64) default NULL COMMENT '货物名称 cargoinfo：id',
  `cargocount` varchar(32) default NULL COMMENT '货物数量',
  `countorg` varchar(64) default NULL COMMENT '数量单位 orginfo：orgname',
  `cargoweight` varchar(32) default NULL COMMENT '货物重量(吨)',
  `funguscount` varchar(32) default NULL COMMENT '含菌数',
  `reqreagent` varchar(32) default NULL COMMENT '要求剂量(kgy)',
  `irradtype` varchar(32) default NULL COMMENT '辐照方式 cargoinfo：irradtype',
  `irradtime` varchar(32) default NULL COMMENT '辐照时间',
  `irradtimeorg` varchar(32) default NULL COMMENT '辐照时间单位',
  `irradflag` varchar(32) default NULL COMMENT ' 辐照类型：首次辐照、重新辐照、不辐照',
  `mask` varchar(255) default NULL,
  `irradednum` int(11) default NULL COMMENT '已照数量',
  `status` varchar(1) default NULL COMMENT '状态',
  `fee` double default '0' COMMENT '货物费用',
  `paid` double NOT NULL default '0' COMMENT '已付费用',
  `unpaid` double NOT NULL default '0' COMMENT '未付费用,默认是fee的值',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8 COMMENT='收货管理基本信息表的子表-存储详细信息';

-- ----------------------------
-- Records of receivemgrdetail
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `roleid` int(11) default NULL COMMENT '角色id',
  `rolename` varchar(64) default NULL COMMENT '角色名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '值班员');

-- ----------------------------
-- Table structure for `shippinginfo`
-- ----------------------------
DROP TABLE IF EXISTS `shippinginfo`;
CREATE TABLE `shippinginfo` (
  `id` int(11) NOT NULL auto_increment,
  `orgname` varchar(64) NOT NULL COMMENT '单位名称',
  `mask` varchar(255) default NULL COMMENT '备注',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `orgname` (`orgname`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='送货信息';

-- ----------------------------
-- Records of shippinginfo
-- ----------------------------


-- ----------------------------
-- Table structure for `takecargobase`
-- ----------------------------
DROP TABLE IF EXISTS `takecargobase`;
CREATE TABLE `takecargobase` (
  `id` int(11) NOT NULL auto_increment,
  `taketime` datetime NOT NULL COMMENT '出获日期',
  `takecargoorg` varchar(64) default NULL COMMENT '取货单位',
  `proxyorg` varchar(64) default NULL COMMENT '委托单位',
  `takecargopeople` varchar(64) default NULL COMMENT '取货人',
  `telnum` varchar(64) default NULL COMMENT '联系电话',
  `shippers` varchar(64) default NULL COMMENT '发货人',
  `status` varchar(1) default '0' COMMENT '状态0:初始状态，即刚添加：1：已出货',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='出货信息表基表';

-- ----------------------------
-- Records of takecargobase
-- ----------------------------


-- ----------------------------
-- Table structure for `takecargodetail`
-- ----------------------------
DROP TABLE IF EXISTS `takecargodetail`;
CREATE TABLE `takecargodetail` (
  `id` int(11) NOT NULL auto_increment,
  `baseid` int(11) NOT NULL COMMENT '基本信息表Id',
  `cargoname` varchar(64) default NULL COMMENT '货物名称',
  `cargocount` varchar(64) default NULL COMMENT '货物数量',
  `countorg` varchar(64) default NULL COMMENT '数量单位',
  `weight` varchar(64) default NULL COMMENT '重量单位(吨)',
  `irradedid` int(11) NOT NULL default '0' COMMENT '辐照货物Id',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='出货信心附表';

-- ----------------------------
-- Records of takecargodetail
-- ----------------------------


-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` varchar(32) NOT NULL,
  `userName` varchar(128) NOT NULL,
  `userPassword` varchar(128) NOT NULL,
  `roleid` int(11) NOT NULL,
  `menuids` varchar(128) default NULL,
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', 'adminfuzhao', '1', '2,3,4,5,7,8,9,10,12,15,17,18');
INSERT INTO `user` VALUES ('root', 'root', 'rootfuzhao', '2', '2,3,4,5,7,18');
INSERT INTO `user` VALUES ('sir', 'sir', '123456', '1', '7,8,9,10');
