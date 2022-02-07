package it.tarczynski.jmolecules.reservation

import it.tarczynski.jmolecules.test.BaseIntegrationSpec
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON

class ReservationIntegrationSpec extends BaseIntegrationSpec {

    def 'should create a reservation'() {
        given: 'there exists a reservable resource'
            String resourceId = aReservableResource()

        and: 'a timeslot'
            String timeSlotId = aTimeSlot()

        and: 'we receive a request to create new reservation'
            RequestEntity<Map> request = RequestEntity
                    .post(uri('/reservations'))
                    .contentType(APPLICATION_JSON)
                    .accept(APPLICATION_JSON)
                    .body([resourceId: resourceId, timeSlotId: timeSlotId])

        when: 'the request is consumed'
            ResponseEntity<Map> response = restTemplate.exchange(request, Map)

        then: 'a new reservation is created'
            response.statusCode == CREATED
    }

    String aReservableResource(int tokens = 20) {
        RequestEntity<Map> request = RequestEntity
                .post(uri("/resources"))
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([tokens: tokens])
        ResponseEntity<Map> response = restTemplate.exchange(request, Map)
        assert response.statusCode == CREATED
        response.body.id
    }

    String aTimeSlot() {
//        RequestEntity<String> request = RequestEntity
//                .post(uri("/timeslots"))
//                .body("""{ "from":"2022-02-06T07:00:00.000Z", "to":"2022-02-06T09:00:00.000Z" }""".toString())
//        restTemplate.exchange(request, Map).body.id
        "489f0eea-f526-4d42-a4dd-03513bdeb059"
    }
}
