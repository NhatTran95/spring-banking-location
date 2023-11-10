package com.cg.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "histories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer sender;

    @ManyToOne
    private Customer recipient;

    @Column(precision = 10, scale = 0)
    private BigDecimal transactionAmount;

    @Column(precision = 10, scale = 0)
    private BigDecimal fee;

    @Column(precision = 10, scale = 0)
    private BigDecimal transferAmount;

    private Date transactionDate;
}
