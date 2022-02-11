package it.tarczynski.jmolecules.test


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.jdbc.JdbcTestUtils
import spock.lang.Specification

import static it.tarczynski.jmolecules.infrastructure.generated.tables.ReservableResources.RESERVABLE_RESOURCES
import static it.tarczynski.jmolecules.infrastructure.generated.tables.Reservations.RESERVATIONS
import static it.tarczynski.jmolecules.infrastructure.generated.tables.Timeslots.TIMESLOTS
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BaseIntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    JdbcTemplate jdbcTemplate

    def setup() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate,
                RESERVATIONS.name,
                RESERVABLE_RESOURCES.name,
                TIMESLOTS.name,
        )
    }

}
