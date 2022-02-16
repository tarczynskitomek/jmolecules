package it.tarczynski.jmolecules.timeslot.domain;

public interface TimeSlotRepository {

    TimeSlot save(TimeSlot slot);

    boolean exists(TimeSlotId id);

    TimeSlot get(TimeSlotId id);

    TimeSlot update(TimeSlot slot);
}
