package it.tarczynski.jmolecules.test.ability

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON

trait ReservationAbility {

    @Autowired
    TestRestTemplate restTemplate

    RequestEntity<Map> reservationRequest(String resourceId, String timeSlotId) {
        RequestEntity
                .post('/reservations')
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([resourceId: resourceId, timeSlotId: timeSlotId])
    }

    String aReservableResource(int tokens = 20) {
        RequestEntity<Map> request = RequestEntity
                .post('/resources')
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([tokens: tokens])
        ResponseEntity<Map> response = restTemplate.exchange(request, Map)
        assert response.statusCode == CREATED
        response.body.id
    }

    String aTimeSlot() {
        RequestEntity<Map> request = RequestEntity
                .post('/timeslots')
                .body([from: '2022-02-06T07:00:00.000Z', to: '2022-02-06T09:00:00.000Z'])
        ResponseEntity<Map> response = restTemplate.exchange(request, Map)
        assert response.statusCode == CREATED
        response.body.id
    }
}
