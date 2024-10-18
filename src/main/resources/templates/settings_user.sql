-- auto-generated definition
create table settings_user
(
    user_uuid     varchar(64) not null
        constraint settings_user_pk
            primary key,
    role_uuid     varchar(64) not null
        constraint settings_user_settings_role_role_uuid_fk
            references settings_role
            on update restrict on delete restrict,
    user_email    varchar(36) not null,
    user_password varchar(100) not null,
    nick_name     varchar(36) not null
);

comment on table settings_user is '用户表';

comment on column settings_user.user_uuid is '用户UUID';

comment on column settings_user.role_uuid is '角色UUID';

comment on column settings_user.user_password is '用户密码';

comment on column settings_user.user_email is '用户邮箱';

comment on column settings_user.nick_name is '用户名';

alter table settings_user
    owner to "racingSettings";

