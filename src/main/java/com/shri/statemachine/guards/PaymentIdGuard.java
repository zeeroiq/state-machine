/*
 * Created by zeeroiq on 9/22/20, 10:35 AM.
 */

package com.shri.statemachine.guards;

import com.shri.statemachine.domain.enums.PaymentEvent;
import com.shri.statemachine.domain.enums.PaymentState;
import com.shri.statemachine.services.PaymentServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;


@Component
public class PaymentIdGuard implements Guard<PaymentState, PaymentEvent> {
    @Override
    public boolean evaluate(StateContext<PaymentState, PaymentEvent> context) {
        return context.getMessageHeader(PaymentServiceImpl.PAYMENT_ID_HEADER) != null;
    }
}
