package com.cg.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Transfer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal transferAmount;
    private BigDecimal total;
    private BigDecimal fee;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer recipient;

    public Transfer(BigDecimal transferAmount, BigDecimal total, BigDecimal fee, Customer sender, Customer recipient) {
        this.transferAmount = transferAmount;
        this.total = total;
        this.fee = fee;
        this.sender = sender;
        this.recipient = recipient;
    }

}

