-- 用户与角色关系
-- 在多租户情况下这两张表(t_role/t_user_info_role),可以下冗到租户自己的库里面.
CREATE TABLE t_user_info_role (
    id                    BIGINT  PRIMARY KEY   COMMENT '用户与角色主键',
    user_info_id          BIGINT  NOT NULL      COMMENT '用户基本信息主键',
	role_id               BIGINT  NOT NULL      COMMENT '角色信息主键' 
) COMMENT '用户与角色关系表';


-- 角色表
CREATE TABLE t_role (
    role_id           BIGINT                       PRIMARY KEY COMMENT '角色信息主键',
    role_name         VARCHAR(200)                 NOT NULL    COMMENT '角色名称',
    create_by         VARCHAR(64)     DEFAULT ''               COMMENT '创建者',
    create_time       DATETIME                                 COMMENT '创建时间',
    update_by         VARCHAR(64)     DEFAULT ''               COMMENT '更新者',
    update_time       DATETIME                                 COMMENT '更新时间',
    remark            VARCHAR(500)    DEFAULT ''               COMMENT '备注'
) COMMENT '角色信息表';


-- 权限表
CREATE TABLE t_power (
    power_id     BIGINT        PRIMARY KEY     COMMENT '权限ID',
    power_type   VARCHAR(50)   NOT NULL        COMMENT '权限类型(menu/pageElement/operation)'
) COMMENT  '权限表';


-- 权限与菜单关联
CREATE TABLE t_power_menu (
    power_menu_id   BIGINT        PRIMARY KEY COMMENT '主键',
    power_id        BIGINT        NOT NULL        COMMENT '权限ID',
    menu_id         BIGINT        NOT NULL        COMMENT '菜单ID'
) COMMENT  '权限与菜单关联';


-- 权限与页面元素关联
CREATE TABLE t_power_page (
    power_page_id           BIGINT        PRIMARY KEY     COMMENT '主键',
    power_id                BIGINT        NOT NULL        COMMENT '权限ID',
    page_element_id         BIGINT        NOT NULL        COMMENT '页面元素ID'
) COMMENT  '权限与页面元素关联表';



-- 权限与操作关联表
CREATE TABLE t_power_operation (
    power_operation_id      BIGINT        PRIMARY KEY     COMMENT '主键',
    power_id                BIGINT        NOT NULL        COMMENT '权限ID',
    operation_id            BIGINT        NOT NULL        COMMENT '操作ID'
) COMMENT  '权限与操作关联表';



-- 菜单
CREATE TABLE t_menu (
    menu_id           BIGINT          PRIMARY KEY              COMMENT '菜单ID',
    menu_parent_id    BIGINT          DEFAULT -1               COMMENT '菜单父ID',
    menu_name         VARCHAR(255)    NOT NULL                 COMMENT '菜单名称',
    icon              VARCHAR(100)    DEFAULT '#'              COMMENT '菜单图标',
    menu_url          VARCHAR(255)    DEFAULT '#'    NOT NULL  COMMENT '菜单URL',
    order_num         INT(4)          DEFAULT 0                COMMENT '显示顺序',
    visible           CHAR(1)         DEFAULT 0                COMMENT '菜单状态（0显示 1隐藏）',
    perms             VARCHAR(100)    DEFAULT NULL             COMMENT ' 权限标识',
    create_by         VARCHAR(64)     DEFAULT ''               COMMENT '创建者',
    create_time       DATETIME                                 COMMENT '创建时间',
    update_by         VARCHAR(64)     DEFAULT ''               COMMENT '更新者',
    update_time       DATETIME                                 COMMENT '更新时间',
    remark            VARCHAR(500)    DEFAULT ''               COMMENT '备注'
) COMMENT '菜单';


-- 页面元素
CREATE TABLE t_page_element (
	page_element_id              BIGINT          PRIMARY KEY  COMMENT '页面元素ID',
	page_element_key             VARCHAR(100)    NOT NULL     COMMENT '页面元素KEY',
	page_element_name            VARCHAR(100)    NOT NULL     COMMENT '页面元素名称',
	create_by                    VARCHAR(64)     DEFAULT ''   COMMENT '创建者',
    create_time                  DATETIME                     COMMENT '创建时间',
    update_by                    VARCHAR(64)     DEFAULT ''   COMMENT '更新者',
    update_time                  DATETIME                     COMMENT '更新时间',
    remark                       VARCHAR(500)    DEFAULT ''   COMMENT '备注'
) COMMENT '页面元素';


-- 功能操作(与页面元素有时确实是一对一,但是,建议拆出来细化)
CREATE TABLE t_operation (
	operation_id                 BIGINT         PRIMARY KEY  COMMENT '操作ID',
	operation_parent_id          BIGINT         NOT NULL     COMMENT '操作父ID',
	operation_name               VARCHAR(50)    NOT NULL     COMMENT '操作名称',
	operation_code               VARCHAR(50)    NOT NULL     COMMENT '操作编码',
	operation_url                VARCHAR(50)    NOT NULL     COMMENT '操作URL',
	create_by                    VARCHAR(64)    DEFAULT ''   COMMENT '创建者',
    create_time                  DATETIME                    COMMENT '创建时间',
    update_by                    VARCHAR(64)    DEFAULT ''   COMMENT '更新者',
    update_time                  DATETIME                    COMMENT '更新时间',
    remark                       VARCHAR(500)   DEFAULT ''   COMMENT '备注'
) COMMENT '功能操作';

