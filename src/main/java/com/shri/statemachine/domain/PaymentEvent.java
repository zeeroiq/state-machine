/*
 * Created by zeeroiq on 9/21/20, 9:08 PM.
 */

package com.shri.statemachine.domain;

public enum PaymentEvent {

    NEW(1),
    PRE_AUTH(2),
    PRE_AUTH_ERROR(3),
    ATU(4),
    AUTH_ERROR(5);

    private Integer event;

    PaymentEvent(Integer event) {
        this.event = event;
    }

    public Integer getEvent() {
        return event;
    }
}
