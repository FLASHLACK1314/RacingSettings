-- auto-generated definition
create table settings_track
(
    track_uuid varchar(64) not null
        constraint settings_track_pk
            primary key,
    track_name varchar(36) not null
);

comment on table settings_track is '基础地图表';

comment on column settings_track.track_uuid is '地图UUID';

comment on column settings_track.track_name is '地图名称';

alter table settings_track
    owner to "racingSettings";

