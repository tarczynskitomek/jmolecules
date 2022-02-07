package it.tarczynski.jmolecules.reservable.application.dto;

import lombok.Data;

@Data
public class CreateReservableResourceRequest {

    private Integer tokens;

    public Integer tokens() {
        return tokens;
    }
}
