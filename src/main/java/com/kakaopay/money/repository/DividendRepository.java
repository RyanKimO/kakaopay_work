package com.kakaopay.money.repository;

import com.kakaopay.money.model.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public interface DividendRepository extends JpaRepository<Dividend, Long> {}
