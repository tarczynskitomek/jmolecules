package it.tarczynski.jmolecules.reservable.infrastructure.config;

import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservable.infrastructure.repository.PostgresResourceRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReservableResourceRepositoryConfiguration {

    @Bean
    ReservableResourceRepository resourceRepository(DSLContext context) {
        return new PostgresResourceRepository(context);
    }
}
