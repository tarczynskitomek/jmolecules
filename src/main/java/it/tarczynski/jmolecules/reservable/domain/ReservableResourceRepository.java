package it.tarczynski.jmolecules.reservable.domain;

public interface ReservableResourceRepository {

    ReservableResource save(ReservableResource resource);

    ReservableResource update(ReservableResource resource);

    ReservableResource get(ResourceId id);
}
