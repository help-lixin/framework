-- t_user_info 与 t_user 是一对多的关系(一个人可以存在多个账号).

CREATE TABLE  t_user_info (
    user_info_id   BIGINT  PRIMARY KEY COMMENT '用户基本信息主键',
    nickname       VARCHAR(64)  COMMENT '昵称',
    icon           VARCHAR(64)  COMMENT '用户图标',
    register_time  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间'
) COMMENT '用户基本信息表';


CREATE TABLE  t_user (
    user_id        BIGINT   PRIMARY KEY COMMENT '用户表主键',
    user_info_id   BIGINT   NOT NULL   COMMENT '用户基本信息主键',
    status         TINYINT  DEFAULT   1 NOT NULL  COMMENT '用户状态(0:禁用 1:启用)',
    type           CHAR(8)  DEFAULT 'general' NOT NULL  COMMENT '登录类型(手机号码/邮箱/用户名)或者第三方应用名称(微信/微博)',
    identifier     VARCHAR(200) NOT NULL COMMENT '标识(手机号码/邮箱/用户名)或者第三方的唯一标识',
    credential     VARCHAR(200) NOT NULL COMMENT '密码凭证(站内保存的密码,站外不做保存或保存为token)'
) COMMENT '用户表';


INSERT INTO t_user_info
      (user_info_id,nickname,icon,register_time) 
VALUES( 378249758998925312,'张三','/378249758998925312.jpeg', NOW() );

INSERT INTO t_user
     (user_id,user_info_id,status,type,identifier,credential)
VALUES( 378249759007313920,378249758998925312,1,'general','lixin','123456' );
