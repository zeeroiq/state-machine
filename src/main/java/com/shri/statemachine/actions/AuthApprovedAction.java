/*
 * Created by zeeroiq on 9/22/20, 10:28 AM.
 */

package com.shri.statemachine.actions;

import com.shri.statemachine.domain.enums.PaymentEvent;
import com.shri.statemachine.domain.enums.PaymentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthApprovedAction implements Action<PaymentState, PaymentEvent> {
    @Override
    public void execute(StateContext<PaymentState, PaymentEvent> context) {
        log.info(">>>>> Auth Approved");
    }
}
