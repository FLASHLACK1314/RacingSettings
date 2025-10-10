-- match definition aligned with simple报名流程
create table settings_match
(
    match_uuid              varchar(64) not null
        constraint settings_match_pk
            primary key,
    game_uuid               varchar(64) not null
        constraint settings_match_game_fk
            references settings_game
            on update restrict on delete restrict,
    track_uuid              varchar(64) not null
        constraint settings_match_track_fk
            references settings_track
            on update restrict on delete restrict,
    car_uuid                varchar(64) not null
        constraint settings_match_car_fk
            references settings_car
            on update restrict on delete restrict,
    match_name              varchar(64) not null,
    match_time              timestamp   not null,
    match_details           varchar(255),
    organizer_uuid          varchar(64)
        constraint settings_match_organizer_fk
            references settings_user
            on update restrict on delete set null,
    registration_start_time timestamp,
    registration_end_time   timestamp,
    match_status            varchar(16) not null default 'PUBLISHED',
    review_status           varchar(16) not null default 'APPROVED',
    review_comment          varchar(255),
    reviewed_by             varchar(64),
    reviewed_at             timestamp
);

comment on table settings_match is '赛事基础信息表';

comment on column settings_match.match_uuid is '比赛UUID';
comment on column settings_match.game_uuid is '关联游戏UUID';
comment on column settings_match.track_uuid is '关联赛道UUID';
comment on column settings_match.car_uuid is '关联赛车UUID';
comment on column settings_match.match_name is '比赛名称';
comment on column settings_match.match_time is '比赛开始时间';
comment on column settings_match.match_details is '比赛简介与说明';
comment on column settings_match.organizer_uuid is '主办方用户UUID';
comment on column settings_match.registration_start_time is '报名开始时间';
comment on column settings_match.registration_end_time is '报名结束时间';
comment on column settings_match.match_status is '赛事当前状态';
comment on column settings_match.review_status is '管理员审核状态';
comment on column settings_match.review_comment is '审核备注';
comment on column settings_match.reviewed_by is '审核人UUID';
comment on column settings_match.reviewed_at is '审核时间';

create index if not exists settings_match_game_idx on settings_match (game_uuid);
create index if not exists settings_match_status_idx on settings_match (match_status);
create index if not exists settings_match_review_status_idx on settings_match (review_status);

alter table if exists settings_match
    owner to "racingSettings";
