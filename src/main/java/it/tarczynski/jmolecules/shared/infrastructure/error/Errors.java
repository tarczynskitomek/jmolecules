package it.tarczynski.jmolecules.shared.infrastructure.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

    private final List<Error> errors;

    public static Errors of(String... messages) {
        return new Errors(
                Arrays.stream(messages)
                        .map(Error::new)
                        .collect(Collectors.toList())
        );
    }

    @Getter
    @AllArgsConstructor
    private static class Error {

        private final String message;
    }
}
