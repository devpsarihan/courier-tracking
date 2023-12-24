package com.couriertracking.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class CourierLocationCreatedEvent extends ApplicationEvent {

    private CourierLocationPayload payload;

    public CourierLocationCreatedEvent(Object source, CourierLocationPayload payload) {
        super(source);
        this.payload = payload;
    }
}
