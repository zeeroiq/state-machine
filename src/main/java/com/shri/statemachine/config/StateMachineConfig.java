/*
 * Created by zeeroiq on 9/22/20, 7:13 AM.
 */

package com.shri.statemachine.config;

import com.shri.statemachine.actions.*;
import com.shri.statemachine.domain.enums.PaymentEvent;
import com.shri.statemachine.domain.enums.PaymentState;
import com.shri.statemachine.guards.PaymentIdGuard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.EnumSet;

@Slf4j
@EnableStateMachineFactory
@RequiredArgsConstructor
@Configuration
public class StateMachineConfig extends StateMachineConfigurerAdapter<PaymentState, PaymentEvent> {
    private final AuthAction authAction;
    private final AuthApprovedAction authApprovedAction;
    private final AuthDeclinedAction authDeclinedAction;
    private final PreAuthAction preAuthAction;
    private final PreAuthApprovedAction preAuthApprovedAction;
    private final PreAuthDeclinedAction preAuthDeclinedAction;
    private final PaymentIdGuard paymentIdGuard;

    // configuration of state to be transitioned
    @Override
    public void configure(StateMachineStateConfigurer<PaymentState, PaymentEvent> states) throws Exception {
        states.withStates()
                .initial(PaymentState.NEW)
                .states(EnumSet.allOf(PaymentState.class))
                .end(PaymentState.AUTH)
                .end(PaymentState.PRE_AUTH_ERROR)
                .end(PaymentState.AUTH_ERROR);
    }

    // configuring the transitions
    // while making the transitions make sure the starting of the transition condition doesn't change the state, recommended
    @Override
    public void configure(StateMachineTransitionConfigurer<PaymentState, PaymentEvent> transitions) throws Exception {
        transitions.withExternal().source(PaymentState.NEW)
                .target(PaymentState.NEW)
                .event(PaymentEvent.PRE_AUTHORIZE) /* this will still make it to be in initial state and state does not change */
                .action(preAuthAction)
                .guard(paymentIdGuard)
                .and()
                .withExternal().source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH)
                .event(PaymentEvent.PRE_AUTH_APPROVED)
                .action(preAuthApprovedAction)
                .and()
                .withExternal().source(PaymentState.NEW)
                .target(PaymentState.PRE_AUTH_ERROR)
                .event(PaymentEvent.PRE_AUTH_DECLINED)
                .action(preAuthDeclinedAction)
                .and()
                .withExternal().source(PaymentState.PRE_AUTH)
                .target(PaymentState.PRE_AUTH)
                .event(PaymentEvent.AUTHORIZE)
                .action(authAction)
                .and()
                .withExternal().source(PaymentState.PRE_AUTH)
                .target(PaymentState.AUTH)
                .event(PaymentEvent.AUTH_APPROVED)
                .action(authApprovedAction)
                .and()
                .withExternal().source(PaymentState.PRE_AUTH)
                .target(PaymentState.AUTH_ERROR)
                .event(PaymentEvent.AUTH_DECLINED)
                .action(authDeclinedAction);
    }


    @Override
    public void configure(StateMachineConfigurationConfigurer<PaymentState, PaymentEvent> config) throws Exception {
        StateMachineListenerAdapter<PaymentState, PaymentEvent> adapter = new StateMachineListenerAdapter<>() {
            @Override
            public void stateChanged(State<PaymentState, PaymentEvent> from, State<PaymentState, PaymentEvent> to) {
                log.info(String.format(">>>>> stateChanged(from: %s, to: %s)", from, to));
            }
        };
        config.withConfiguration().listener(adapter);
    }

}
