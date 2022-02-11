-- domain model
create table reservable_resources
(
    id               varchar                  not null primary key,
    tokens_available integer                  not null,
    tokens_reserved  integer                  not null,
    tokens_taken     integer                  not null,
    created_at       timestamp with time zone not null
);

create table timeslots
(
    id        varchar                  not null primary key,
    from_time timestamp with time zone not null,
    to_time   timestamp with time zone not null
);

create table reservations
(
    id           varchar                  not null primary key,
    resource_id  varchar                  not null references reservable_resources (id),
    timeslot_id  varchar                  not null references timeslots (id),
    created_at   timestamp with time zone not null,
    placed_at    timestamp with time zone,
    confirmed_at timestamp with time zone
);

-- read model view
create view reservations_view as
select r.*, rr.tokens_available, rr.tokens_reserved, rr.tokens_taken, rr.created_at as resource_created_at, from_time, to_time
from reservations r
         join reservable_resources rr on r.resource_id = rr.id
         join timeslots t on r.timeslot_id = t.id;
