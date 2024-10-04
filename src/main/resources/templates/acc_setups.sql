-- auto-generated definition
create table acc_setups
(
    setups_uuid         varchar(32) not null
        constraint acc_setups_pk
            primary key,
    game_car_track_uuid varchar(64) not null
        constraint acc_setups_settings_game_car_track_game_car_track_uuid_fk
            references settings_game_car_track
            on update restrict on delete restrict,
    user_uuid           varchar(32) not null
        constraint acc_setups_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    acc_setups          varchar(32) not null
);

comment on table acc_setups is 'ACC赛车设置';

comment on column acc_setups.setups_uuid is '赛车设置UUID';

comment on column acc_setups.game_car_track_uuid is '具体车赛道地图';

comment on column acc_setups.user_uuid is '用户UUID';

comment on column acc_setups.acc_setups is '具体赛车设置';

alter table acc_setups
    owner to "racingSettings";

