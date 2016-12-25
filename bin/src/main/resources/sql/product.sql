CREATE DATABASE `product` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
use product;
create table user (
userId varchar(32) not null unique,
userName varchar(128) not  null,
userPassword varchar(128) not null,
roleid int not null,
menuids varchar(128)
);



#角色表
DROP TABLE IF EXISTS `role`;
create table role(
roleid int comment '角色id',
rolename varchar(64) comment '角色名称',
) comment='角色表';







insert into user values ("admin","admin","admin");
##货物信息
DROP TABLE IF EXISTS `cargoinfo`;
create table cargoinfo(
id int PRIMARY key auto_increment,
cargoname VARCHAR(64) not null UNIQUE  COMMENT '货物名称',
org VARCHAR(128) comment '单位' ,
irradtype VARCHAR(32) comment '辐照方式',
irradtime datetime comment '辐照时间',
timeorg VARCHAR(32) comment '时间单位'
)comment='货物信息表';

##送货信息
DROP TABLE IF EXISTS `shippinginfo`;
create table shippinginfo(
id int PRIMARY key auto_increment,
orgname varchar(64) not null UNIQUE comment '单位名称' ,
mask varchar(255) comment '备注'
) comment='送货信息';
##送取货人
DROP TABLE IF EXISTS `bringtakeinfo`;
create table bringtakeinfo(
id int PRIMARY key auto_increment,
name VARCHAR(32) not NULL comment '姓名',
orgname varchar(64) comment '所在单位',
tel  int(16) comment '联系电话',
mask varchar(255) comment '备注'
) comment='送取货人';
##单位信息
DROP TABLE IF EXISTS `orginfo`;
create table orginfo(
id int PRIMARY key auto_increment,
orgname varchar(64) not null UNIQUE comment '计数单位名称',
mask varchar(255) comment '备注'
)comment='单位信息';

#收获基本信息表
DROP TABLE IF EXISTS `receivemgrbase`;
create table  receivemgrbase(
id int PRIMARY key auto_increment,
receivetime datetime not null comment '收货日期',
receiveorgid varchar(64) not null comment '送货单位 shippinginfo：orgname',
receivepeopleid VARCHAR(32) not null comment '送货人 bringtakeinfo：name',
telnum VARCHAR(16) not null comment '联系电话',
userid varchar(32) comment '操作员编号',
status VARCHAR(1) comment '是否指纹确认 0:未指纹确认，1:已指纹确认  2:已收货确认 3:已辐射完毕'
)comment ='收货管理基本信息表';

#收获详情表
DROP TABLE IF EXISTS `receivemgrdetail`;
create table receivemgrdetail(
id int PRIMARY key auto_increment,
receivemgrid int comment '收获管理基本信息id ReceiveMgrBase：id',
cargoid VARCHAR(64) comment '货物id cargoinfo：id',
cargocount VARCHAR(32) comment '货物数量',
takecargocount varchar(32) comment '已取货数量',
countorg varchar(64) comment '数量单位 orginfo：orgname', 
cargoweight VARCHAR(32)  comment '货物重量(吨)',
funguscount VARCHAR(32) comment '含菌数',
reqreagent VARCHAR(32)  comment '要求剂量(kgy)',
irradtype VARCHAR(32) comment '辐照方式 cargoinfo：irradtype',
irradtime VARCHAR(32) comment   '辐照时间',
irradtimeorg  VARCHAR(32) comment '辐照时间单位',
irradflag VARCHAR(32) comment ' 辐照类型：首次辐照、重新辐照、不辐照',
irradednum int comment '已照数量',
status varchar(1) comment '状态',
mask varchar(255) comment '备注说明'
) comment='收货管理基本信息表的子表-存储详细信息';

#特性参数表
DROP TABLE IF EXISTS `product_chart`;
create table product_chart(
itemid varchar(64) not null unique comment '特性参数的key',
itemvalue varchar(255) comment '特性参数的value'
)comment='特性参数表';

#字典表基表
DROP TABLE IF EXISTS `dict_group`;
create table dict_group(
groupid varchar(128) not null unique comment '分组编号',
groupname varchar(256) not null comment '分组名称'
) comment='字典组表基表';

#字典表附表
DROP TABLE IF EXISTS `dict_item`;
create table dict_item(
itemid varchar(128) comment '项目编号',
groupid varchar(128) comment '分组编号',
itemname varchar(256) comment '项目名称'
)comment='字典组表项目表';

#辐照信息表
DROP TABLE IF EXISTS `irradiation`;
create table  irradiation(
id int PRIMARY key auto_increment,
detailid int comment '货物详情的主键',
baseid int comment '货物基本信息表的主键',
receiveorgid VARCHAR(64) comment '送货单位',
cargoname VARCHAR(64) comment '货物名称',
irradednum int comment '辐照数量',
countorg varchar(64) comment '数量单位',
cargoweight varchar(64) comment '货物重量',
irradtype  varchar(64) comment '辐照类型',
irradtime varchar(64) comment '辐照时间',
timeorg varchar(64) comment '时间单位',
doirraddate varchar(64) comment '日期',
ordernum varchar(64) comment '序号',
connecttime varchar(64) comment '交接时间',
firstspreadernum varchar(64) comment '首位吊具号',
irradbatchnum varchar(64) comment '辐照批号',
entrancetime varchar(64) comment '入场时间',
spreadernum varchar(64) comment '吊具号码',
loadmodel varchar(64) comment '装载模式',
runparam varchar(64) comment '运行参数',
runcycle varchar(64) comment '运行圈数',
changedesc varchar(128) comment '变动说明',
nextcyclestarttime varchar(64) comment '下圈开始时间',
takecargocount varchar(32) comment '已取货数量',
status varchar(1) comment '状态',
mask varchar(64) comment '备注'
);

#出货信息表基表
DROP TABLE IF EXISTS `takecargobase`;
create table takecargobase(
id int PRIMARY key auto_increment,
taketime datetime not null comment '出获日期',
takecargoorg varchar(64)  comment '取货单位',
proxyorg varchar(64) comment '委托单位',
takecargopeople varchar(64) comment '取货人',
telnum varchar(64) comment '联系电话',
shippers varchar(64) comment '发货人'
) comment='出货信息表基表';

#出货信息表附表
DROP TABLE IF EXISTS `takecargodetail`;
create table takecargodetail(
id int PRIMARY key auto_increment,
baseid int not null  comment '基本信息表Id',
cargoname varchar(64)  comment '货物名称',
cargocount varchar(64) comment '货物数量',
countorg varchar(64) comment '数量单位',
weight varchar(64) comment '重量单位(吨)'
) comment='出货信心附表';
