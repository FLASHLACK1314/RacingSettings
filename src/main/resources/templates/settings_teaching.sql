

-- 为引用列添加唯一约束（如果它们不是主键）
ALTER TABLE settings_game ADD CONSTRAINT settings_game_game_uuid_unique UNIQUE (game_uuid);
ALTER TABLE settings_track ADD CONSTRAINT settings_track_track_uuid_unique UNIQUE (track_uuid);
ALTER TABLE settings_car ADD CONSTRAINT settings_car_car_uuid_unique UNIQUE (car_uuid);

create table settings_teaching
(
    teaching_uuid varchar(64) not null
        constraint settings_teaching_pk
            primary key,
    game_uuid     varchar(64) not null
        constraint settings_teaching_settings_game_game_uuid_fk
            references settings_game(game_uuid)
            on update restrict on delete restrict,
    track_uuid    varchar(64) not null
        constraint settings_teaching_settings_track_track_uuid_fk
            references settings_track(track_uuid)
            on update restrict on delete restrict,
    car_uuid      varchar(64) not null
        constraint settings_teaching_settings_car_car_uuid_fk
            references settings_car(car_uuid)
            on update restrict on delete restrict,
    teaching_name  varchar(64) not null,
    teaching_url  varchar(64) not null
);

comment on table settings_teaching is '赛道教学';
comment on column settings_teaching.teaching_uuid is '教学UUID';
comment on column settings_teaching.game_uuid is '游戏UUID';
comment on column settings_teaching.track_uuid is '赛道UUID';
comment on column settings_teaching.car_uuid is '车辆UUID';
comment on column settings_teaching.teaching_name is '教学视频名称';
comment on column settings_teaching.teaching_url is '教学网址';

alter table settings_teaching
    owner to "racingSettings";
