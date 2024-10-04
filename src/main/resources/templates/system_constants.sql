
create table system_constants
(
    key   varchar(64) not null
        primary key,
    value varchar(64) not null
);

comment on table system_constants is '系统常量表';

comment on column system_constants.key is '存放的信息主键';

comment on column system_constants.value is '信息值';

alter table system_constants
    owner to "racingSettings";

