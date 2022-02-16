package it.tarczynski.jmolecules.reservation

import it.tarczynski.jmolecules.test.BaseIntegrationSpec
import it.tarczynski.jmolecules.test.ability.ReservationAbility
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity

import static org.springframework.http.HttpStatus.*

class ReservationIntegrationSpec
        extends BaseIntegrationSpec
        implements ReservationAbility {

    public static final int UNAVAILABLE_TOKENS = 0

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

        and:
            ResponseEntity<Map> reservationsResponse = fetchReservations()

        then:
            withReservationResponse(creationResponse)
                    .hasStatus(TOO_MANY_REQUESTS)
                    .hasErrorMessage("Resource [$resourceId] exhausted")

        and:
            withReservationResponse(reservationsResponse)
                    .hasStatus(OK)
                    .isEmpty()
    }
}
