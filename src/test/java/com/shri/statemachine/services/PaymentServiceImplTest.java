/*
 * Created by zeeroiq on 9/22/20, 9:05 AM.
 */

package com.shri.statemachine.services;

import com.shri.statemachine.domain.Payment;
import com.shri.statemachine.domain.enums.PaymentEvent;
import com.shri.statemachine.domain.enums.PaymentState;
import com.shri.statemachine.repositories.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;
    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("234.32")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(this.payment);
        System.out.println(">>>>> State should be NEW");
        System.out.println(">>>>> " + savedPayment.getState());
        StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());
        Payment preAuthorizedPayment = paymentRepository.getOne(savedPayment.getId());
        System.out.println(">>>>> Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(">>>>> " + sm.getState().getId());
        System.out.println(">>>>> " + preAuthorizedPayment);

    }

    @Transactional
    @RepeatedTest(111)
    void auth() {
        Payment savedPayment = paymentService.newPayment(this.payment);
        StateMachine<PaymentState, PaymentEvent> preAuthSM = paymentService.preAuth(savedPayment.getId());
        if (preAuthSM.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println(">>>>> Payment is Pre Authorized");
            StateMachine<PaymentState, PaymentEvent> authSM = paymentService.authorizePayment(savedPayment.getId());
            System.out.println(">>>>> Result of Auth : " + authSM.getState().getId());
        } else {
            System.out.println(">>>>> Payment failed pre-auth");
        }
    }
}