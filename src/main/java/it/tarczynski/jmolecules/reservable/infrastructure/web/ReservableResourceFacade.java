package it.tarczynski.jmolecules.reservable.infrastructure.web;

import it.tarczynski.jmolecules.reservable.domain.ReservableResource;
import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.shared.TimeMachine;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservableResourceFacade {

    private final TimeMachine timeMachine;
    private final ReservableResourceRepository resourceRepository;

    public ReservableResource createWith(ReservableTokens tokens) {
        final var createdAt = timeMachine.now();
        final var id = ResourceId.next();
        return resourceRepository.save(new ReservableResource(id, tokens, createdAt));
    }
}
