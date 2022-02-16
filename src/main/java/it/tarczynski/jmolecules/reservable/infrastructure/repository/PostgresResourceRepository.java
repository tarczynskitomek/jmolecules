package it.tarczynski.jmolecules.reservable.infrastructure.repository;

import it.tarczynski.jmolecules.infrastructure.generated.tables.records.ReservableResourcesRecord;
import it.tarczynski.jmolecules.reservable.domain.ReservableResource;
import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservable.domain.exception.ExpectedResourceNotFoundException;
import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

import static it.tarczynski.jmolecules.infrastructure.generated.tables.ReservableResources.RESERVABLE_RESOURCES;

@AllArgsConstructor
public class PostgresResourceRepository implements ReservableResourceRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PostgresResourceRepository.class);

    private final DSLContext context;

    @Override
    public ReservableResource save(ReservableResource resource) {
        final var record = context.newRecord(RESERVABLE_RESOURCES);
        record.setId(resource.id().value().toString());
        record.setTokensAvailable(resource.tokens().available());
        record.setTokensReserved(resource.tokens().reserved());
        record.setTokensTaken(resource.tokens().taken());
        record.setCreatedAt(resource.createdAt().atOffset(ZoneOffset.UTC));
        record.store();
        return resource;
    }

    @Override
    public ReservableResource update(ReservableResource resource) {
        final int recordsUpdated = context.update(RESERVABLE_RESOURCES)
                .set(RESERVABLE_RESOURCES.TOKENS_AVAILABLE, resource.tokens().available())
                .set(RESERVABLE_RESOURCES.TOKENS_RESERVED, resource.tokens().reserved())
                .set(RESERVABLE_RESOURCES.TOKENS_TAKEN, resource.tokens().taken())
                .where(RESERVABLE_RESOURCES.ID.eq(resource.id().value().toString()))
                .execute();
        LOG.info("Updated resource [{}], records updated [{}]", resource.id().value(), recordsUpdated);
        return resource;
    }

    @Override
    public ReservableResource get(ResourceId id) {
        final ReservableResourcesRecord record = getResourceRecord(id);
        return new ReservableResource(
                new ResourceId(UUID.fromString(record.getId())),
                new ReservableTokens(
                        record.getTokensAvailable(),
                        record.getTokensReserved(),
                        record.getTokensTaken()
                ),
                record.getCreatedAt().toInstant()
        );
    }

    @Override
    public boolean exists(ResourceId resourceId) {
        return context.fetchExists(RESERVABLE_RESOURCES, RESERVABLE_RESOURCES.ID.eq(resourceId.value().toString()));
    }

    private ReservableResourcesRecord getResourceRecord(ResourceId id) {
        final var record = context.fetchOne(RESERVABLE_RESOURCES, RESERVABLE_RESOURCES.ID.eq(id.value().toString()));
        if (Objects.isNull(record)) {
            throw new ExpectedResourceNotFoundException(id);
        }
        return record;
    }
}
