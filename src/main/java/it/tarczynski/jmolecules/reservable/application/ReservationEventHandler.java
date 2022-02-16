package it.tarczynski.jmolecules.reservable.application;

import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ReservationEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationEventHandler.class);

    private final ReservableResourceRepository resourceRepository;

    public void handle(ReservationCreatedEvent event) {
        LOG.info("Received event [{}]", event);
        final var resource = resourceRepository.get(event.resourceId());
        final var reservedResource = resource.reserve();
        LOG.info("Updated resource [{}] availability", resource.id().value());
        resourceRepository.update(reservedResource);
    }
}
