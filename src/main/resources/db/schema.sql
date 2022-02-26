-- auto-generated definition
create table currency
(
    id            serial
        primary key,
    code          varchar(3)   not null
        constraint uk_h84pd2rtr12isnifnj655rkra
            unique,
    creation_date timestamp    not null,
    modified_by   varchar(60),
    name          varchar(100) not null
        constraint uk_ou8q9939fa4k88wjh17qwcmre
            unique
);

alter table currency
    owner to postgres;

-- auto-generated definition
create table exchange_rate
(
    id            serial primary key,
    created_by    varchar(60)      not null,
    creation_date timestamp        not null,
    modified_date timestamp        null,
    destiny_rate  double precision not null
        constraint exchange_rate_destiny_rate_check
            check (destiny_rate <= (10000)::double precision),
    disabled_date timestamp,
    enabled       boolean          not null,
    source_unit   smallint         not null
        constraint exchange_rate_source_unit_check
            check ((source_unit >= 1) AND (source_unit <= 1)),
    destiny_id    integer          not null
        constraint fkk2x88cjny8knuorjmo8lu3m5v
            references currency,
    source_id     integer          not null
        constraint fk8762pmay9au1qs93k5drfm975
            references currency
);

alter table exchange_rate
    owner to postgres;

