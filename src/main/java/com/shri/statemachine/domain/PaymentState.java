/*
 * Created by zeeroiq on 9/21/20, 9:08 PM.
 */

/*
 * Created by zeeroiq on 9/21/20, 9:08 PM.
 */

package com.shri.statemachine.domain;

public enum PaymentState {
    NEW(0),
    PRE_AUTH(1),
    PRE_AUTH_ERROR(2),
    AUTH(3),
    AUTH_ERROR(4);

    private Integer state;

    PaymentState(Integer state) {
        this.state = state;
    }

    public Integer getState() {
        return state;
    }
}
