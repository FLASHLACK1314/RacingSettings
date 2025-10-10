-- registration records for赛事报名流程
create table settings_match_registration
(
    registration_uuid   varchar(64) not null
        constraint settings_match_registration_pk
            primary key,
    match_uuid          varchar(64) not null
        constraint settings_match_registration_match_fk
            references settings_match
            on update restrict on delete cascade,
    user_uuid           varchar(64) not null
        constraint settings_match_registration_user_fk
            references settings_user
            on update restrict on delete cascade,
    registration_status varchar(16) not null default 'PENDING',
    applied_at          timestamp   not null default CURRENT_TIMESTAMP,
    reviewed_at         timestamp,
    reviewed_by         varchar(64),
    review_comment      varchar(255)
);

comment on table settings_match_registration is '报名记录表';

comment on column settings_match_registration.registration_uuid is '报名UUID';
comment on column settings_match_registration.match_uuid is '关联比赛UUID';
comment on column settings_match_registration.user_uuid is '参赛用户UUID';
comment on column settings_match_registration.registration_status is '报名状态';
comment on column settings_match_registration.applied_at is '报名时间';
comment on column settings_match_registration.reviewed_at is '审核时间';
comment on column settings_match_registration.reviewed_by is '审核人UUID';
comment on column settings_match_registration.review_comment is '审核备注';

create unique index if not exists settings_match_registration_unique_idx
    on settings_match_registration (match_uuid, user_uuid);

create index if not exists settings_match_registration_status_idx
    on settings_match_registration (registration_status);

alter table if exists settings_match_registration
    owner to "racingSettings";
