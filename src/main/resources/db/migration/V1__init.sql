create table reservable_resources
(
    id               varchar not null primary key,
    tokens_available integer not null,
    tokens_reserved  integer not null,
    tokens_taken     integer not null,
    created_at       timestamp with time zone not null
);

create table timeslots
(
    id varchar not null primary key
);

create table reservations
(
    id           varchar                  not null primary key,
    resource_id  varchar                  not null,
    timeslot_id  varchar                  not null,
    created_at   timestamp with time zone not null,
    placed_at    timestamp with time zone,
    confirmed_at timestamp with time zone
);
