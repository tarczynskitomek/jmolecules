package it.tarczynski.jmolecules.reservation

import it.tarczynski.jmolecules.test.BaseIntegrationSpec
import it.tarczynski.jmolecules.test.ability.ReservationAbility
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.ACCEPTED
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS

class ReservationIntegrationSpec
        extends BaseIntegrationSpec
        implements ReservationAbility {

    private static final int UNAVAILABLE_TOKENS = 0

    def 'should create a reservation'() {
        given: 'there exists a reservable resource'
            String resourceId = aReservableResource()

        and: 'a timeslot'
            String timeSlotId = aTimeSlot()

        and: 'we receive a request to create new reservation'
            RequestEntity<Map> request = reservationRequest(resourceId, timeSlotId)

        when: 'the request is executed'
            String reservationId = createReservationAndReturnId(request)

        and: 'we request the newly created reservation'
            ResponseEntity<Map> response = fetchReservation(reservationId)

        then: 'a new reservation is created'
            withReservationResponse(response)
                    .hasStatus(OK)
                    .hasId()
                    .hasSlotId(timeSlotId)
                    .hasResourceId(resourceId)
    }

    def 'creating a reservation should decrease resource availability'() {
        given: 'a resource with a 2 tokens available'
            String resourceId = aReservableResource(2)

        and: 'an available slot'
            String timeSlotId = aTimeSlot()

        when: 'a reservation request is executed'
            String reservationId = createReservationAndReturnId(reservationRequest(resourceId, timeSlotId))

        and: 'we fetch a reservation'
            ResponseEntity<Map> fetchResponse = fetchReservation(reservationId)

        then: 'available capacity is decreased and a resource is reserved'
            withReservationResponse(fetchResponse)
                    .hasStatus(OK)
                    .hasResourceWithAvailableCapacity(1)
                    .hasResourceWithReservedCapacity(1)
                    .hasResourceWithTakenCapacity(0)
    }

    def 'creating a reservation should decrease timeslot availability'() {
        given: 'a resource'
            String resourceId = aReservableResource()

        and: 'a timeslot with 2 tokens available'
            String timeSlotId = aTimeSlot(2)

        when: 'reservation is created'
            String reservationId = createReservationAndReturnId(reservationRequest(resourceId, timeSlotId))

        and: 'an we get reservation data'
            ResponseEntity<Map> fetchResponse = fetchReservation(reservationId)

        then: 'available time slot capacity is decreased and reserved'
            withReservationResponse(fetchResponse)
                    .hasStatus(OK)
                    .hasTimeSlotWithAvailableCapacity(1)
                    .hasTimeSlotWithReservedCapacity(1)
                    .hasTimeSlotWithTakenCapacity(0)
    }

    def 'should not create a reservation if resource does not exist'() {
        given: 'a timeslot'
            String timeSlotId = aTimeSlot()

        and: 'a resource id that does not exist'
            String resourceId = UUID.randomUUID().toString()

        and: 'a request to create a reservation for a resource that does not exist'
            RequestEntity<Map> request = reservationRequest(resourceId, timeSlotId)

        when: 'the request is executed'
            ResponseEntity<Map> response = execute(request)

        and: 'and we read reservations'
            ResponseEntity<Map> reservationsResponse = fetchReservations()

        then: '412 response is returned'
            withReservationResponse(response)
                    .hasStatus(PRECONDITION_FAILED)
                    .hasErrorMessage("Precondition failed. Expected resource [$resourceId] does not exist")

        and: 'no new reservation is created'
            withReservationResponse(reservationsResponse)
                    .hasStatus(OK)
                    .isEmpty()
    }

    def "should not create a reservation if time slot does not exist"() {
        given: 'a reservable resource'
            String resourceId = aReservableResource()

        and: 'an id of a time slot that does not exist'
            String timeSlotId = UUID.randomUUID().toString()

        and: 'a request to create a reservation'
            RequestEntity<Map> request = reservationRequest(resourceId, timeSlotId)

        when: 'the request is executed'
            ResponseEntity<Map> createResponse = execute(request)

        and: 'we fetch reservations'
            ResponseEntity<Map> reservationsResponse = fetchReservations()

        then: '412 is returned'
            withReservationResponse(createResponse)
                    .hasStatus(PRECONDITION_FAILED)
                    .hasErrorMessage("Precondition failed. Expected time slot [$timeSlotId] does not exist")

        and: 'no new reservation is created'
            withReservationResponse(reservationsResponse)
                    .hasStatus(OK)
                    .isEmpty()
    }

    def "should not create a reservation if resource is unavailable"() {
        given: 'a timeslot'
            String timeSlotId = aTimeSlot()

        and: 'an unavailable resource'
            String resourceId = aReservableResource(UNAVAILABLE_TOKENS)

        when: 'a request to create a reservation is executed'
            ResponseEntity<Map> creationResponse = execute(
                    reservationRequest(resourceId, timeSlotId)
            )

        and: 'and we fetch all reservations'
            ResponseEntity<Map> reservationsResponse = fetchReservations()

        then: 'an error is returned indicating that the resource has already been exhausted'
            withReservationResponse(creationResponse)
                    .hasStatus(TOO_MANY_REQUESTS)
                    .hasErrorMessage("Resource [$resourceId] exhausted")

        and: 'no new reservation is created'
            withReservationResponse(reservationsResponse)
                    .hasStatus(OK)
                    .isEmpty()
    }

    def 'should not create a reservation if time slot is unavailable'() {
        given: 'a resource'
            String resourceId = aReservableResource()

        and: 'an unavailable time slot'
            String timeSlotId = aTimeSlot(UNAVAILABLE_TOKENS)

        and: 'a request to create a reservation'
            RequestEntity<Map> reservationRequest = reservationRequest(resourceId, timeSlotId)

        when: 'the request is executed'
            ResponseEntity<Map> createResponse = execute(reservationRequest)

        then: 'an error is returned'
            withReservationResponse(createResponse)
                    .hasStatus(TOO_MANY_REQUESTS)
                    .hasErrorMessage("TimeSlot [$timeSlotId] already exhausted")
    }

    def 'should place created reservation'() {
        given: 'a reservation'
            String reservationId = createReservationAndReturnId()

        when: 'the reservation is placed'
            ResponseEntity<Map> response = execute(placeReservationRequest(reservationId))

        then: 'saved reservation is marked as placed'
            withReservationResponse(response).hasStatus(ACCEPTED)

        and:
            withReservationResponse(fetchReservation(reservationId))
                    .hasStatus(OK)
                    .isPlaced()
    }

    def 'should confirm reservation'() {
        given: 'a reservation'
            String reservationId = createReservationAndReturnId()

        and: 'that has been placed'
            execute(placeReservationRequest(reservationId))

        when: 'the reservation is confirmed'
            ResponseEntity<Map> response = execute(confirmReservationRequest(reservationId))

        then: 'the operation is successful'
            withReservationResponse(response).hasStatus(ACCEPTED)

        and: 'saved reservation is marked as confirmed'
            withReservationResponse(fetchReservation(reservationId))
                    .hasStatus(OK)
                    .isConfirmed()
    }

    def 'confirming a reservation should reduce reserved and increase taken capacity on resource'() {
        given: 'a reservation'
            String reservationId = createReservationAndReturnId(availableResourceTokens: 2)

        and: 'that has been placed'
            execute(placeReservationRequest(reservationId))

        when: 'the reservation is confirmed'
            execute(confirmReservationRequest(reservationId))

        then: 'reserved capacity is reduced and taken increased'
            withReservationResponse(fetchReservation(reservationId))
                    .hasStatus(OK)
                    .hasResourceWithAvailableCapacity(1)
                    .hasResourceWithReservedCapacity(0)
                    .hasResourceWithTakenCapacity(1)
    }

    def 'confirming a reservation should reduce reserved and increase taken capacity on time slot'() {
        given: 'a reservation'
            String reservationId = createReservationAndReturnId(availableSlotTokens: 2)

        and: 'that has been placed'
            execute(placeReservationRequest(reservationId))

        when: 'the reservation is confirmed'
            execute(confirmReservationRequest(reservationId))

        then: 'reserved capacity on time slot is reduced and taken increased'
            withReservationResponse(fetchReservation(reservationId))
                    .hasStatus(OK)
                    .hasTimeSlotWithAvailableCapacity(1)
                    .hasTimeSlotWithReservedCapacity(0)
                    .hasTimeSlotWithTakenCapacity(1)
    }
}
