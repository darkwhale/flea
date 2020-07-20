
-- 建立用户表
drop table if exists `flea_user`;
create table `flea_user`(
    `user_id` varchar(16) not null comment '用户id',
    `email` varchar(32) not null comment '',
    `username` varchar(32) not null comment '用户名',
    `password` varchar(32) not null comment '',
    `role` int(4) not null default '0' comment '',
    `create_time` timestamp not null default current_timestamp comment '创建时间',
	`update_time` timestamp not null default current_timestamp on update current_timestamp comment '最后修改时间',
	primary key(`user_id`),
	unique key `email_uqe` (`email`) using btree
)engine=InnoDB default charset=utf8;