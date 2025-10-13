-- auto-generated definition
create table settings_match
(
    match_uuid               varchar(36) not null
        constraint settings_match_pk
            primary key,
    organizer_uuid           varchar(36) not null
        constraint settings_match_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    match_name               varchar(100) not null,
    match_description        text,
    match_type               varchar(50),
    match_category           varchar(50),
    match_location           varchar(200),
    registration_start_time  timestamp    not null,
    registration_end_time    timestamp    not null,
    match_start_time         timestamp    not null,
    match_end_time           timestamp,
    max_participants         integer default 50,
    current_participants     integer default 0,
    match_status             varchar(20) default 'PENDING',
    created_at               timestamp   default CURRENT_TIMESTAMP,
    updated_at               timestamp   default CURRENT_TIMESTAMP
);

comment on table settings_match is '赛事信息表';

comment on column settings_match.match_uuid is '赛事唯一标识';

comment on column settings_match.organizer_uuid is '主办方UUID(关联settings_user)';

comment on column settings_match.match_name is '赛事名称';

comment on column settings_match.match_description is '赛事介绍';

comment on column settings_match.match_type is '赛事类型(如:竞技赛、友谊赛、锦标赛等)';

comment on column settings_match.match_category is '赛事分类(如:体育、电竞、学术、文艺等)';

comment on column settings_match.match_location is '赛事地点';

comment on column settings_match.registration_start_time is '报名开始时间';

comment on column settings_match.registration_end_time is '报名结束时间';

comment on column settings_match.match_start_time is '赛事开始时间';

comment on column settings_match.match_end_time is '赛事结束时间';

comment on column settings_match.max_participants is '最大参赛人数';

comment on column settings_match.current_participants is '当前报名人数';

comment on column settings_match.match_status is '赛事状态(PENDING:待审核, APPROVED:审核通过, REJECTED:审核拒绝, ONGOING:进行中, FINISHED:已结束, CANCELLED:已取消)';

comment on column settings_match.created_at is '创建时间';

comment on column settings_match.updated_at is '更新时间';

alter table settings_match
    owner to "racingSettings";
