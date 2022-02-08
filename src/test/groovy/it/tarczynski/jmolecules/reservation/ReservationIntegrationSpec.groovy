package it.tarczynski.jmolecules.reservation

import it.tarczynski.jmolecules.test.BaseIntegrationSpec
import it.tarczynski.jmolecules.test.ability.ReservationAbility
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.CREATED

class ReservationIntegrationSpec
        extends BaseIntegrationSpec
        implements ReservationAbility {

    def 'should create a reservation'() {
        given: 'there exists a reservable resource'
            String resourceId = aReservableResource()

        and: 'a timeslot'
            String timeSlotId = aTimeSlot()
            println timeSlotId

        and: 'we receive a request to create new reservation'
            RequestEntity<Map> request = reservationRequest(resourceId, timeSlotId)

        when: 'the request is consumed'
            ResponseEntity<Map> response = restTemplate.exchange(request, Map)

        then: 'a new reservation is created'
            response.statusCode == CREATED
    }

}
