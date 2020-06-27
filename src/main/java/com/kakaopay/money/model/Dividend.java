package com.kakaopay.money.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Entity(name = "dividend")
@NoArgsConstructor
@Getter
@Setter
public class Dividend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Nullable
    @Column(name = "receiver_id")
    private Long receiverId;

    @ManyToOne
    @JoinColumn(name = "distribution_id", nullable = false)
    private Distribution distribution;


    public boolean isNotReceived() {
        return receiverId == null;
    }

}
