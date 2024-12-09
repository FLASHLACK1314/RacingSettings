-- auto-generated definition
create table settings_match
(
    match_uuid    varchar(64) not null
        constraint settings_match_pk
            primary key,
    game_uuid     varchar(64) not null
        constraint settings_match_fk
            references settings_game
            on update restrict on delete restrict,
    track_uuid    varchar(64) not null
        constraint settings_match_see__fk
            references settings_track
            on update restrict on delete restrict,
    car_uuid      varchar(64) not null
        constraint settings_match___fk
            references settings_car
            on update restrict on delete restrict,
    match_name    varchar(32) not null,
    match_time    timestamp   not null,
    match_details varchar(64) not null
);

comment on table settings_match is '比赛推荐表';

comment on column settings_match.match_uuid is '比赛UUID';

comment on column settings_match.game_uuid is '比赛UUID';

comment on column settings_match.track_uuid is '赛道UUID';

comment on column settings_match.car_uuid is '赛车UUID';

comment on column settings_match.match_name is '比赛简述';

comment on column settings_match.match_time is '比赛时间';

comment on column settings_match.match_details is '比赛详情';


alter table settings_match
    owner to "racingSettings";

