/*
 * Created by zeeroiq on 9/22/20, 7:02 AM.
 */

/*
 * Created by zeeroiq on 9/21/20, 9:08 PM.
 */

package com.shri.statemachine.domain.enums;

public enum PaymentEvent {

    PRE_AUTHORIZE(1),
    PRE_AUTH_APPROVED(2),
    PRE_AUTH_DECLINED(3),
    AUTHORIZE(4),
    AUTH_APPROVED(5),
    AUTH_DECLINED(6);

    private Integer event;

    PaymentEvent(Integer event) {
        this.event = event;
    }

    public Integer getEvent() {
        return event;
    }
}
