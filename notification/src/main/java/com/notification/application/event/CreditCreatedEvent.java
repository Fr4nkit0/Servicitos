package com.notification.application.event;

import com.commons.event.Event;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCreatedEvent extends Event<EventCredit> {

}