package it.tarczynski.jmolecules.shared.infrastructure;

import it.tarczynski.jmolecules.shared.ChangeLogRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChangeLogRepositoryConfig {

    @Bean
    ChangeLogRepository changeLogRepository(DSLContext context) {
        return new PostgresChangeLogRepository(context);
    }
}
