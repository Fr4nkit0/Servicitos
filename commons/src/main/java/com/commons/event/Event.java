package com.commons.event;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public abstract class Event<T> {
    private String id;
    private LocalDateTime date;
    private EventType type;
    private T data;

}
