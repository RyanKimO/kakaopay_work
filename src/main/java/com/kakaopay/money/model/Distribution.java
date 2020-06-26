package com.kakaopay.money.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Developer : ryan kim
 * Date : 2020-06-25
 */
@Entity(name = "distribution")
@NoArgsConstructor
@Getter
@Setter
public class Distribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @Column(name = "room_id", nullable = false)
    private String roomId;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "divided_cnt", nullable = false)
    private Integer dividedCnt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "distribution")
    private Set<Dividend> dividends = new HashSet<>();


    public void setDistributionToDividends() {
        dividends.forEach(e -> e.setDistribution(this));
    }

    // expire 계산해야함

}
