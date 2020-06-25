package com.kakaopay.money.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Developer : ryan kim
 * Date : 2020-06-25
 */
@Entity
public class Distribution {

    @Id
    private Long id;

    private String token;

    private Long ownerId;

    private String roomId;

    private Long amount;

    private Integer dividedCnt;

    private LocalDateTime createdAt;

    @OneToMany
    private List<Dividend> dividends = new ArrayList<>();

    // expire 계산해야함

}
