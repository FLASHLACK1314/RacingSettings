-- auto-generated definition
create table settings_game_car_track
(
    game_car_track_uuid varchar(64) not null
        constraint setting_game_car_map_pk
            primary key,
    game_uuid           varchar(64) not null
        constraint setting_game_car_map_settings_game_game_uuid_fk
            references settings_game
            on update restrict on delete restrict,
    car_uuid            varchar(64) not null
        constraint setting_game_car_map_settings_car_car_uuid_fk
            references settings_car
            on update restrict on delete restrict,
    track_uuid          varchar(64) not null
        constraint settings_game_car_track_settings_track_track_uuid_fk
            references settings_track
            on update restrict on delete restrict,
    game_name           varchar(32) not null,
    car_name            varchar(32) not null,
    track_name          varchar(32) not null
);

comment on table settings_game_car_track is '具体游戏车辆地图';

comment on column settings_game_car_track.game_car_track_uuid is '准确车辆UUID';

comment on column settings_game_car_track.car_uuid is '车辆UUID';

comment on column settings_game_car_track.track_uuid is '车辆UUID';

comment on column settings_game_car_track.game_name is '游戏名称';

comment on column settings_game_car_track.car_name is '车辆名称';

comment on column settings_game_car_track.track_name is '地图名称';

alter table settings_game_car_track
    owner to "racingSettings";

