package it.tarczynski.jmolecules.shared.infrastructure.event;

import it.tarczynski.jmolecules.shared.EventBus;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@AllArgsConstructor
public class SpringEventBus implements EventBus {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publish(Object event) {
        eventPublisher.publishEvent(event);
    }
}
