package it.tarczynski.jmolecules.timeslot.infrastructure.web;

import it.tarczynski.jmolecules.timeslot.application.TimeSlotFacade;
import it.tarczynski.jmolecules.timeslot.application.dto.CreateTimeSlotRequest;
import it.tarczynski.jmolecules.timeslot.application.dto.TimeSlotResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timeslots")
@AllArgsConstructor
public class TimeSlotController {

    private final TimeSlotFacade timeSlotFacade;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public TimeSlotResponse createTimeSlot(@RequestBody CreateTimeSlotRequest request) {
        final var timeSlot = timeSlotFacade.create(request);
        return TimeSlotResponse.from(timeSlot);
    }
}
