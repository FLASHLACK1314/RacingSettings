-- auto-generated definition
create table email_code
(
    user_email  varchar(64) not null
        constraint email_code_pk
            primary key,
    user_uuid   varchar(64)
        constraint email_code_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    email_code  varchar(32) not null,
    create_time timestamp   not null,
    expire_time timestamp   not null
);

comment on column email_code.user_email is '用户邮箱';

comment on column email_code.user_uuid is '用户uuid';

comment on column email_code.email_code is '邮箱验证码';

comment on column email_code.create_time is '验证码发送时间';

comment on column email_code.expire_time is '验证码过期时间';

alter table email_code
    owner to "racingSettings";

