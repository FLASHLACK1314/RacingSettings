-- auto-generated definition
create table settings_match_registration
(
    registration_uuid   varchar(36) not null
        constraint settings_match_registration_pk
            primary key,
    match_uuid          varchar(36) not null
        constraint settings_match_registration_settings_match_match_uuid_fk
            references settings_match
            on update restrict on delete restrict,
    user_uuid           varchar(36) not null
        constraint settings_match_registration_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    registration_time   timestamp default CURRENT_TIMESTAMP,
    registration_status varchar(20) default 'PENDING',
    contact_info        varchar(200),
    remarks             text,
    review_time         timestamp,
    review_reason       varchar(500),
    constraint settings_match_registration_unique
        unique (match_uuid, user_uuid)
);

comment on table settings_match_registration is '报名记录表';

comment on column settings_match_registration.registration_uuid is '报名记录唯一标识';

comment on column settings_match_registration.match_uuid is '赛事UUID';

comment on column settings_match_registration.user_uuid is '参赛者UUID';

comment on column settings_match_registration.registration_time is '报名时间';

comment on column settings_match_registration.registration_status is '报名状态(PENDING:待审核, APPROVED:审核通过, REJECTED:审核拒绝, CANCELLED:已取消)';

comment on column settings_match_registration.contact_info is '联系方式';

comment on column settings_match_registration.remarks is '备注信息';

comment on column settings_match_registration.review_time is '审核时间';

comment on column settings_match_registration.review_reason is '审核说明';

comment on constraint settings_match_registration_unique on settings_match_registration is '同一用户不能重复报名同一赛事';

alter table settings_match_registration
    owner to "racingSettings";
