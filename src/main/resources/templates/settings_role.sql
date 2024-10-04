-- auto-generated definition
create table settings_role
(
    role_uuid       varchar(64) not null
        constraint settings_role_pk
            primary key,
    role_alias      varchar(36) not null,
    role_permission text        not null
);

comment on table settings_role is '角色表';

comment on column settings_role.role_uuid is '角色UUID';

comment on constraint settings_role_pk on settings_role is '角色表主键';

comment on column settings_role.role_alias is '角色别名';

comment on column settings_role.role_permission is '角色权限';

alter table settings_role
    owner to "racingSettings";

