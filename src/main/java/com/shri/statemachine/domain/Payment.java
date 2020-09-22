/*
 * Created by zeeroiq on 9/22/20, 7:02 AM.
 */

package com.shri.statemachine.domain;

import com.shri.statemachine.domain.enums.PaymentState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment implements Serializable {

    private static final long serialVersionUID = 879946501691569264L;

    @Id
    @GeneratedValue
    private Long id;
    @Enumerated(value = EnumType.STRING)
    private PaymentState state;
    private BigDecimal amount;
}
