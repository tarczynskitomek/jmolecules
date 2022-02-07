package it.tarczynski.jmolecules.reservable.infrastructure.config;

import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservable.infrastructure.web.ReservableResourceFacade;
import it.tarczynski.jmolecules.shared.TimeMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservableResourceFacadeConfiguration {

    @Bean
    ReservableResourceFacade reservableResourceFacade(
            TimeMachine timeMachine,
            ReservableResourceRepository resourceRepository
    ) {
        return new ReservableResourceFacade(timeMachine, resourceRepository);
    }
}
