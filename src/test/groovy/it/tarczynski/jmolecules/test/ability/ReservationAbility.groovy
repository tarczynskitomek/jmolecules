package it.tarczynski.jmolecules.test.ability

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.MediaType.APPLICATION_JSON

trait ReservationAbility {

    @Autowired
    TestRestTemplate restTemplate

    ResponseEntity<Map> response

    RequestEntity<Map> reservationRequest(String resourceId, String timeSlotId) {
        RequestEntity
                .post('/reservations')
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body([resourceId: resourceId, timeSlotId: timeSlotId])
    }

    String createReservationAndReturnId(Map<String, Integer> config = [availableResourceTokens: 20, availableSlotTokens: 5]) {
        String resourceId = aReservableResource(config.availableResourceTokens ?: 20)
        String slotId = aTimeSlot(config.availableSlotTokens ?: 5)
        createReservationAndReturnId(reservationRequest(resourceId, slotId))
    }

    RequestEntity<Map> placeReservationRequest(String reservationId) {
        RequestEntity
                .put("/reservations/$reservationId/place")
                .body([commandId: UUID.randomUUID()])
    }


    RequestEntity<Map> confirmReservationRequest(String reservationId) {
        RequestEntity
                .put("/reservations/$reservationId/confirm")
                .body([commandId: UUID.randomUUID()])
    }

    String createReservationAndReturnId(RequestEntity<Map> request) {
        ResponseEntity<Map> response = execute(request)
        assert response.statusCode == CREATED
        response.body.id
    }

    ResponseEntity<Map> execute(RequestEntity<Map> request) {
        ResponseEntity<Map> response = restTemplate.exchange(request, Map)
        response
    }

    ResponseEntity<Map> fetchReservation(String id) {
        restTemplate.exchange(
                RequestEntity.get("/reservations/$id")
                        .accept(APPLICATION_JSON)
                        .build(), Map
        )
    }

    ResponseEntity<Map> fetchReservations() {
        restTemplate.exchange(
                RequestEntity.get("/reservations")
                        .accept(APPLICATION_JSON)
                        .build(), Map
        )
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

    String aTimeSlot(int tokens = 5) {
        RequestEntity<Map> request = RequestEntity
                .post('/timeslots')
                .body([from: '2022-02-06T07:00:00.000Z', to: '2022-02-06T09:00:00.000Z', tokens: tokens])
        ResponseEntity<Map> response = restTemplate.exchange(request, Map)
        assert response.statusCode == CREATED
        response.body.id
    }

    ReservationAbility withReservationResponse(ResponseEntity<Map> response) {
        this.response = response
        this
    }

    ReservationAbility hasStatus(HttpStatus expected) {
        assert response.statusCode == expected
        this
    }

    ReservationAbility hasId() {
        assert response.body.id != null
        this
    }

    ReservationAbility hasSlotId(String expected) {
        assert response.body.timeSlot.id == expected
        this
    }

    ReservationAbility hasResourceId(String expected) {
        assert response.body.resource.id == expected
        this
    }

    ReservationAbility hasErrorMessage(String expected) {
        assert response.body.errors*.message == [expected]
        this
    }

    ReservationAbility isEmpty() {
        assert response.body.reservations.isEmpty()
        this
    }

    ReservationAbility hasResourceWithAvailableCapacity(int expected) {
        assert response.body.resource.availableTokens == expected
        this
    }

    ReservationAbility hasResourceWithReservedCapacity(int expected) {
        assert response.body.resource.reservedTokens == expected
        this
    }

    ReservationAbility hasResourceWithTakenCapacity(int expected) {
        assert response.body.resource.takenTokens == expected
        this
    }

    ReservationAbility hasTimeSlotWithAvailableCapacity(int expected) {
        assert response.body.timeSlot.availableTokens == expected
        this
    }

    ReservationAbility hasTimeSlotWithReservedCapacity(int expected) {
        assert response.body.timeSlot.reservedTokens == expected
        this
    }

    ReservationAbility hasTimeSlotWithTakenCapacity(int expected) {
        assert response.body.timeSlot.takenTokens == expected
        this
    }

    ReservationAbility isPlaced() {
        assert response.body.placedAt != null
        this
    }

    ReservationAbility isConfirmed() {
        assert response.body.confirmedAt != null
        this
    }
}
