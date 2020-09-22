/*
 * Created by zeeroiq on 9/22/20, 7:06 AM.
 */

package com.shri.statemachine.repositories;

import com.shri.statemachine.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
