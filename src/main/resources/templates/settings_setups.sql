-- auto-generated definition
create table settings_setups
(
    setups_uuid varchar(64)           not null
        constraint setting_game_car_map_pk
            primary key,
    game_uuid   varchar(64)           not null
        constraint setting_game_car_map_settings_game_game_uuid_fk
            references settings_game
            on update restrict on delete restrict,
    track_uuid  varchar(64)           not null
        constraint settings_setups_settings_track_track_uuid_fk
            references settings_track
            on update restrict on delete restrict,
    car_uuid    varchar(64)           not null
        constraint setting_game_car_map_settings_car_car_uuid_fk
            references settings_car
            on update restrict on delete restrict,
    user_uuid   varchar(64)           not null
        constraint settings_setups_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    setups_name varchar(32)           not null,
    setups      varchar               not null,
    recommend   boolean default false not null
);

comment on table settings_setups is '赛车设置表';

comment on column settings_setups.setups_uuid is '赛车设置UUID';

comment on column settings_setups.track_uuid is '车辆UUID';

comment on column settings_setups.car_uuid is '车辆UUID';

comment on column settings_setups.user_uuid is '用户UUID';

comment on column settings_setups.setups_name is '赛车设置名称';

comment on column settings_setups.setups is '赛车具体设置';

comment on column settings_setups.recommend is '是否被推荐';

alter table settings_setups
    owner to "racingSettings";

