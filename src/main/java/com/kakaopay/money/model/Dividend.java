package com.kakaopay.money.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Entity
public class Dividend {

    @Id
    private Long id;

    private Long receiverId;

    private LocalDateTime createdAt;

    @ManyToOne
    private Distribution distribution;







}
