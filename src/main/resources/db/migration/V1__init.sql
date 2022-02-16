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
    id               varchar                  not null primary key,
    from_time        timestamp with time zone not null,
    to_time          timestamp with time zone not null,
    tokens_available integer                  not null,
    tokens_reserved  integer                  not null,
    tokens_taken     integer                  not null
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
select r.*,
       rr.tokens_available as resource_tokens_available,
       rr.tokens_reserved  as resource_tokens_reserved,
       rr.tokens_taken     as resource_tokens_taken,
       rr.created_at       as resource_created_at,
       t.from_time,
       t.to_time,
       t.tokens_available  as slot_tokens_available,
       t.tokens_reserved   as slot_tokens_reserved,
       t.tokens_taken      as slot_tokens_taken
from reservations r
         join reservable_resources rr on r.resource_id = rr.id
         join timeslots t on r.timeslot_id = t.id;
