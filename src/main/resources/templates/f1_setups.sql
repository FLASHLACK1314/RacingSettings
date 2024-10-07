
create table f1_setups
(
    setups_uuid         varchar(64) not null
        constraint f1_setups_pk
            primary key,
    game_car_track_uuid varchar(64) not null
        constraint f1_setups_settings_game_car_track_game_car_track_uuid_fk
            references settings_game_car_track
            on update restrict on delete restrict,
    user_uuid           varchar(32) not null
        constraint f1_setups_settings_user_user_uuid_fk
            references settings_user
            on update restrict on delete restrict,
    f1_setups           text        not null
);

comment on table f1_setups is '存放F1系列赛车设置的表';

comment on column f1_setups.setups_uuid is '赛车设置UUID';

comment on column f1_setups.game_car_track_uuid is '准确游戏，赛道，赛道的UUID';

comment on column f1_setups.user_uuid is '用户UUID';

comment on column f1_setups.f1_setups is 'F1游戏设置';

alter table f1_setups
    owner to "racingSettings";

