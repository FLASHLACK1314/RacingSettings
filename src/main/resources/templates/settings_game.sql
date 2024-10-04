-- auto-generated definition
create table settings_game
(
    game_uuid varchar(64) not null
        constraint settings_game_pk
            primary key,
    game_name varchar(36) not null
);

comment on table settings_game is '游戏表';

comment on column settings_game.game_uuid is '游戏UUID';

comment on column settings_game.game_name is '游戏名';

alter table settings_game
    owner to "racingSettings";

