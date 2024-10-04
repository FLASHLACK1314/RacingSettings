-- auto-generated definition
create table settings_car
(
    car_uuid varchar(64) not null
        constraint settings_car_pk
            primary key,
    car_name varchar(36) not null
);

comment on table settings_car is '基础车辆表';

comment on column settings_car.car_uuid is '车辆UUID';

comment on column settings_car.car_name is '车辆名称';

alter table settings_car
    owner to "racingSettings";

