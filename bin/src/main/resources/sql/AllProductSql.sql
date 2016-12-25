/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50067
Source Host           : localhost:3306
Source Database       : product

Target Server Type    : MYSQL
Target Server Version : 50067
File Encoding         : 65001

Date: 2015-08-29 09:29:09
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
INSERT INTO `bringtakeinfo` VALUES ('1', '李彦宏', '百度', '23479', null);
INSERT INTO `bringtakeinfo` VALUES ('2', '马化腾', '腾讯', '3243', null);
INSERT INTO `bringtakeinfo` VALUES ('3', '马云', '阿里巴巴', '345', null);
INSERT INTO `bringtakeinfo` VALUES ('4', '任正非', '华为', null, null);

-- ----------------------------
-- Table structure for `cargoinfo`
-- ----------------------------
DROP TABLE IF EXISTS `cargoinfo`;
CREATE TABLE `cargoinfo` (
  `id` int(11) NOT NULL auto_increment,
  `cargoname` varchar(64) NOT NULL COMMENT '货物名称',
  `org` varchar(128) default NULL COMMENT '单位',
  `irradtype` varchar(32) default NULL COMMENT '辐照方式',
  `irradtime` datetime default NULL COMMENT '辐照时间',
  `timeorg` varchar(32) default NULL COMMENT '时间单位',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `cargoname` (`cargoname`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='货物信息表';

-- ----------------------------
-- Records of cargoinfo
-- ----------------------------
INSERT INTO `cargoinfo` VALUES ('1', '带鱼', '条', '无', '2015-07-21 00:00:00', '90');
INSERT INTO `cargoinfo` VALUES ('2', '芹菜', '斤', '无', '2015-07-21 13:35:38', '0');
INSERT INTO `cargoinfo` VALUES ('3', '土豆', '斤', '无', '2015-07-21 13:36:03', '0');
INSERT INTO `cargoinfo` VALUES ('4', '香蕉', '斤', '无', '2015-07-21 13:36:39', '0');
INSERT INTO `cargoinfo` VALUES ('5', '西瓜', '斤', '无', '2015-07-21 13:37:02', '0');
INSERT INTO `cargoinfo` VALUES ('6', '西红柿', '斤', '无', '2015-07-22 16:43:02', '0');
INSERT INTO `cargoinfo` VALUES ('7', '地瓜', '斤', '无', '2015-07-22 16:43:22', '0');
INSERT INTO `cargoinfo` VALUES ('8', '黄瓜', '斤', '无', '2015-07-22 16:43:43', '0');
INSERT INTO `cargoinfo` VALUES ('9', '韭菜', '斤', '无', '2015-07-22 16:44:13', '0');
INSERT INTO `cargoinfo` VALUES ('10', '苹果', '斤', '无', '2015-07-22 16:44:32', '0');
INSERT INTO `cargoinfo` VALUES ('11', '豆角', '斤', '无', '2015-07-22 16:44:56', '0');
INSERT INTO `cargoinfo` VALUES ('12', '黄鱼', '斤', '无', '2015-07-22 16:45:51', '0');
INSERT INTO `cargoinfo` VALUES ('13', '海带', '斤', '无', '2015-07-22 16:46:08', '0');
INSERT INTO `cargoinfo` VALUES ('14', '鲶鱼', '斤', '无', '2015-07-22 16:46:31', '0');
INSERT INTO `cargoinfo` VALUES ('15', '猪肉', '斤', '无', '2015-07-22 16:46:55', '0');
INSERT INTO `cargoinfo` VALUES ('16', '豆腐', '斤', '无', '2015-07-22 16:47:12', '0');
INSERT INTO `cargoinfo` VALUES ('17', '蘑菇', '斤', '无', '2015-07-22 16:47:31', '0');
INSERT INTO `cargoinfo` VALUES ('18', '鲅鱼', '斤', '无', '2015-07-22 16:47:47', '0');
INSERT INTO `cargoinfo` VALUES ('19', '金针菇', '斤', '无', '2015-07-22 16:48:07', '0');
INSERT INTO `cargoinfo` VALUES ('20', '鸡蛋', '斤', '无', '2015-07-22 16:48:30', '0');
INSERT INTO `cargoinfo` VALUES ('21', '火烧', '斤', '无', '2015-07-22 16:48:50', '0');
INSERT INTO `cargoinfo` VALUES ('22', '馒头', '斤', '无', '2015-07-22 16:49:04', '0');
INSERT INTO `cargoinfo` VALUES ('23', '榴莲', '斤', '无', '2015-07-22 16:49:26', '0');
INSERT INTO `cargoinfo` VALUES ('24', 'name', 'org', 'itypr', '2015-07-23 11:15:42', 'timeorg');
INSERT INTO `cargoinfo` VALUES ('26', 'edit', 'oddrg', '', '0000-00-00 00:00:00', '');
INSERT INTO `cargoinfo` VALUES ('27', '王二麻子', '诸城', '辐射', '2015-07-24 00:00:00', '小时/个');

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
INSERT INTO `dict_group` VALUES ('irradflags', '辐照类型');
INSERT INTO `dict_group` VALUES ('irradtypes', '辐照方式');
INSERT INTO `dict_group` VALUES ('loadmodel', '辐照装载模式');
INSERT INTO `dict_group` VALUES ('timeorgs', '时间单位');

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
  `doirraddate` varchar(64) default NULL COMMENT '日期',
  `ordernum` varchar(64) default NULL COMMENT '序号',
  `connecttime` varchar(64) default NULL COMMENT '交接时间',
  `firstspreadernum` varchar(64) default NULL COMMENT '首位吊具号',
  `irradbatchnum` varchar(64) default NULL COMMENT '辐照批号',
  `entrancetime` varchar(64) default NULL COMMENT '入场时间',
  `spreadernum` varchar(64) default NULL COMMENT '吊具号码',
  `loadmodel` varchar(64) default NULL COMMENT '装载模式',
  `runparam` varchar(64) default NULL COMMENT '运行参数',
  `runcycle` varchar(64) default NULL COMMENT '运行圈数',
  `changedesc` varchar(128) default NULL COMMENT '变动说明',
  `nextcyclestarttime` varchar(64) default NULL COMMENT '下圈开始时间',
  `status` varchar(1) default NULL COMMENT '状态',
  `mask` varchar(64) default NULL COMMENT '备注',
  `takecargocount` varchar(32) default NULL COMMENT '已取货数量',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of irradiation
-- ----------------------------
INSERT INTO `irradiation` VALUES ('1', '20', '13', '1', '地瓜', '234', '1', '234', 'staticirrad', '234', 'hour', '2015-08-26', '2', '123', '23', '34', '2015-08-26', '432', '23', '234', '234', '243', '234', '1', '42', '234');
INSERT INTO `irradiation` VALUES ('2', '21', '13', '1', '芹菜', '567556', '1', '567', 'staticirrad', '67', 'hour', '2015-08-26', '234', '2015-08-26', '123', '23', '2015-08-26', '234', '23', '23', '234', '234', '234', '1', '342', '567556');
INSERT INTO `irradiation` VALUES ('3', '22', '14', '2', '带鱼', '345', '1', '3457', 'staticirrad', '5675675', 'hour', '234', '324', '234', '234', '234', '234', '23', '234', '4', '23', '234', '234', '1', '324', '345');
INSERT INTO `irradiation` VALUES ('4', '23', '15', '2', '土豆', '4234', '1', '345', 'staticirrad', '', 'hour', '2015-08-27', '23', '2015-08-27 9:00', '5', '2', '2015-08-27 9:30', '6', 'fullload', '63', '3', '35', '34', '0', '534', '0');
INSERT INTO `irradiation` VALUES ('5', '27', '18', '3', '土豆', '234', '3', '234', 'dnyirrad', '', 'minute', '2015-08-28', '5', '2015-08-28 11:00', '5', '4', '2015-08-28 11:20', '7', 'maximum', '44', '6', '46', '6', '0', '4456', '0');

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
INSERT INTO `orginfo` VALUES ('1', '个', null);
INSERT INTO `orginfo` VALUES ('2', '斤', null);
INSERT INTO `orginfo` VALUES ('3', '升', null);
INSERT INTO `orginfo` VALUES ('4', '吨', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

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
INSERT INTO `product_resources` VALUES ('12', '权限配置', '11', 'privilegeconfig', '1', '/privilegemana/configuration.do', null, null, '0', null);
INSERT INTO `product_resources` VALUES ('13', '角色配置', '11', 'privilegerole', '1', '/privilegemana/privilegerole.do', null, null, '0', '角色管理');
INSERT INTO `product_resources` VALUES ('14', '收款管理', '0', 'collectionmana', '1', '/collection/', null, null, '0', '收款管理');
INSERT INTO `product_resources` VALUES ('15', '收款', '14', 'collectioninit', '1', '/collection/init.do', null, null, '0', '收款');
INSERT INTO `product_resources` VALUES ('16', '报表管理', '0', 'report', '1', '/report/', null, null, '0', '报表管理');
INSERT INTO `product_resources` VALUES ('17', '日报表', '16', 'dayreport', '1', '/report/dayreport.do', null, null, '0', '日报表');
INSERT INTO `product_resources` VALUES ('18', '月报表', '16', 'monthreport', '1', '/report/monthreport.do', null, null, '0', '月报表');
INSERT INTO `product_resources` VALUES ('19', '应收已付', '0', 'prepaidreceivable', '1', '/prepaidreceivable/', null, null, '0', '应收已付');
INSERT INTO `product_resources` VALUES ('20', '应收已付', '19', 'prepaidreceivableinit', null, '/prepaidreceivable/prepaidreceivableinit.do', null, null, '0', '应收已付');

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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='收货管理基本信息表';

-- ----------------------------
-- Records of receivemgrbase
-- ----------------------------
INSERT INTO `receivemgrbase` VALUES ('13', '2015-08-26 00:00:00', '1', '1', '234', '2', 'admin');
INSERT INTO `receivemgrbase` VALUES ('14', '2015-08-26 00:00:00', '2', '1', '345', '2', 'admin');
INSERT INTO `receivemgrbase` VALUES ('15', '2015-08-26 00:00:00', '2', '1', '2342', '2', 'admin');
INSERT INTO `receivemgrbase` VALUES ('16', '2015-08-26 00:00:00', '1', '4', '345634', '1', 'admin');
INSERT INTO `receivemgrbase` VALUES ('17', '2015-08-26 00:00:00', '1', '3', '345', '2', 'admin');
INSERT INTO `receivemgrbase` VALUES ('18', '2015-08-27 00:00:00', '3', '3', '34253', '2', 'admin');
INSERT INTO `receivemgrbase` VALUES ('19', '2015-08-01 00:00:00', '1', '4', '234', '1', 'admin');
INSERT INTO `receivemgrbase` VALUES ('20', '2015-08-14 00:00:00', '2', '1', '456', '0', 'admin');
INSERT INTO `receivemgrbase` VALUES ('21', '2015-08-29 00:00:00', '1', '4', '34252', '0', 'admin');

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
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='收货管理基本信息表的子表-存储详细信息';

-- ----------------------------
-- Records of receivemgrdetail
-- ----------------------------
INSERT INTO `receivemgrdetail` VALUES ('20', '13', '7', '234', '1', '234', '789', '345345', 'staticirrad', '234', 'hour', 'firstirrad', '', '234', '2');
INSERT INTO `receivemgrdetail` VALUES ('21', '13', '2', '567556', '1', '567', '675', '755', 'staticirrad', '67', 'hour', 'firstirrad', null, '567556', '2');
INSERT INTO `receivemgrdetail` VALUES ('22', '14', '1', '345', '1', '3457', '7', '345', 'staticirrad', '5675675', 'hour', 'firstirrad', null, '345', '2');
INSERT INTO `receivemgrdetail` VALUES ('23', '15', '3', '4234', '1', '345', '345', '234', 'staticirrad', '345', 'hour', 'firstirrad', null, '4234', '2');
INSERT INTO `receivemgrdetail` VALUES ('24', '16', '12', '34', '1', '345', '345tg', '34', 'staticirrad', '345', 'hour', 'firstirrad', null, '0', '1');
INSERT INTO `receivemgrdetail` VALUES ('25', '17', '8', '6', '1', '234', '234', '234', 'staticirrad', '64', 'hour', 'firstirrad', null, '0', '2');
INSERT INTO `receivemgrdetail` VALUES ('26', '17', '14', '23', '1', '67843', '234', '234', 'staticirrad', '789', 'hour', 'firstirrad', null, '0', '2');
INSERT INTO `receivemgrdetail` VALUES ('27', '18', '3', '234', '3', '234', '234', '234', 'dnyirrad', '234', 'minute', 'secondirrad', null, '234', '2');
INSERT INTO `receivemgrdetail` VALUES ('28', '19', '1', '234467', '3', '353', '23456', '56234', 'dnyirrad', '5345', 'minute', 'secondirrad', null, '0', '1');
INSERT INTO `receivemgrdetail` VALUES ('29', '19', '2', '76', '3', '32', '12', '1321', 'dnyirrad', '453', 'minute', 'secondirrad', null, '0', '1');
INSERT INTO `receivemgrdetail` VALUES ('30', '20', '11', '7868', '3', '345', '345', '13', 'dnyirrad', '24', 'minute', 'noirrad', null, '0', '0');
INSERT INTO `receivemgrdetail` VALUES ('31', '20', '10', '23', '4', '45', '456', '456', 'staticirrad', '345', 'minute', 'firstirrad', null, '0', '0');
INSERT INTO `receivemgrdetail` VALUES ('32', '21', '1', '23', '3', '45', '45', '45', 'dnyirrad', '456', 'minute', 'firstirrad', null, '0', '0');
INSERT INTO `receivemgrdetail` VALUES ('33', '21', '8', '65', '4', '5', '543', '23', 'staticirrad', '45', 'hour', 'secondirrad', null, '0', '0');

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
-- Table structure for `role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `roleid` int(11) default NULL COMMENT '角色id',
  `menuid` int(11) default NULL COMMENT '菜单id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单连接表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '7');
INSERT INTO `role_menu` VALUES ('1', '8');
INSERT INTO `role_menu` VALUES ('1', '9');
INSERT INTO `role_menu` VALUES ('1', '10');
INSERT INTO `role_menu` VALUES ('1', '12');
INSERT INTO `role_menu` VALUES ('1', '13');
INSERT INTO `role_menu` VALUES ('1', '15');
INSERT INTO `role_menu` VALUES ('1', '17');
INSERT INTO `role_menu` VALUES ('1', '18');
INSERT INTO `role_menu` VALUES ('1', '20');
INSERT INTO `role_menu` VALUES ('1', '2');
INSERT INTO `role_menu` VALUES ('1', '3');
INSERT INTO `role_menu` VALUES ('1', '4');
INSERT INTO `role_menu` VALUES ('1', '5');
INSERT INTO `role_menu` VALUES ('2', '2');
INSERT INTO `role_menu` VALUES ('2', '3');
INSERT INTO `role_menu` VALUES ('2', '4');
INSERT INTO `role_menu` VALUES ('2', '5');

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
INSERT INTO `shippinginfo` VALUES ('1', '华为', '华为公司');
INSERT INTO `shippinginfo` VALUES ('2', '百度', '北京百度');
INSERT INTO `shippinginfo` VALUES ('3', '阿里巴巴', '杭州阿里巴巴');

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
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='出货信息表基表';

-- ----------------------------
-- Records of takecargobase
-- ----------------------------
INSERT INTO `takecargobase` VALUES ('5', '2015-08-26 00:00:00', '1', '华为子公司', '4', '234', '234');
INSERT INTO `takecargobase` VALUES ('6', '2015-08-26 00:00:00', '2', '百度', '1', '234', '345');

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
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='出货信心附表';

-- ----------------------------
-- Records of takecargodetail
-- ----------------------------
INSERT INTO `takecargodetail` VALUES ('7', '5', '地瓜', '234', '1', '234');
INSERT INTO `takecargodetail` VALUES ('8', '5', '芹菜', '567556', '1', '567');
INSERT INTO `takecargodetail` VALUES ('9', '6', '带鱼', '345', '1', '3457');
INSERT INTO `takecargodetail` VALUES ('10', '6', '带鱼', '345', '1', '3457');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `userId` varchar(32) NOT NULL,
  `userName` varchar(128) NOT NULL,
  `userPassword` varchar(128) NOT NULL,
  UNIQUE KEY `userId` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('admin', 'admin', 'admin');
INSERT INTO `user` VALUES ('root', 'root', 'root');

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userid` varchar(32) default NULL COMMENT '用户id',
  `roleid` int(11) default NULL COMMENT '角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色连接表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('admin', '1');
INSERT INTO `user_role` VALUES ('root', '2');
