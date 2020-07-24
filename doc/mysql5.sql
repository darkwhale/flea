
-- 建立用户表
drop table if exists `user`;
create table `user`(
    `user_id` varchar(32) not null comment '用户id',
    `email` varchar(32) not null comment '',
    `username` varchar(32) not null comment '用户名',
    `password` varchar(32) not null comment '',
    `role` int(4) not null default '0' comment '',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
	primary key(`user_id`),
	unique key `email_uqe` (`email`) using btree
)engine=InnoDB default charset=utf8;

-- 建立地址表
drop table if exists `address`;
create table `address`(
    `address_id` int(32) not null auto_increment comment '地址id',
    `address_type` tinyint(4) not null comment '地址类型：0宿舍地址，1上课地址',
    `address_region` varchar(32) not null comment '地址区域，例如：梅园',
    `address_floor` varchar(32) not null comment '地址建筑标识',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`address_id`)
)engine=InnoDB default charset=utf8;

-- 建立学院表
drop table if exists `campus`;
create table `campus`(
    `campus_id` int(32) not null auto_increment comment '学院id',
    `campus_name` varchar(32) not null comment '学院名称',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`campus_id`)
)engine=InnoDB default charset=utf8;

-- 用户信息表
drop table if exists `user_info`;
create table `user_info`(
    `user_info_id` varchar(32) not null comment '用户信息id',
    `user_email` varchar(32) not null comment '用户邮箱,用于关联',
    `user_gender` int(4) not null default '0' comment '用户性别：0男性，1女性',
    `user_campus_id` int(8) comment '学院',
    `user_mobile` varchar(16) comment '用户电话',
    `user_qq` varchar(32) comment '用户qq',
    `user_wx` varchar(32) comment '用户微信',
    `user_reside_address_id` int(4) comment '用户居住地址',
    `user_study_address_id` int(4) comment '用户学习地址',
    `user_grade` int(4) not null default '1' comment '用户年级',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
    primary key(`user_info_id`),
    unique key `email_uqe` (`user_email`) using btree
)engine=InnoDB default charset=utf8;

-- 小摊表
drop table if exists `booth`;
create table `booth`(
    `booth_id` varchar(32) not null comment '书摊id',
    `user_id` varchar(32) not null comment '用户id',
    `booth_type` int(2) not null default '0' comment '小摊类型：0书摊，1杂货摊',
    `address_id` int(4) comment '地址',
    `booth_name` varchar(64) not null comment '小摊名',
    `synopsis` varchar(256) not null comment '小摊简介',
    `rub_time` int(32) not null default '0' comment '擦亮次数',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
	primary key(`booth_id`),
	unique key `user_key` (`user_id`) using btree
)engine=InnoDB default charset=utf8;



