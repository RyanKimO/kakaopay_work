package com.kakaopay.money.repository;

import com.kakaopay.money.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {}
