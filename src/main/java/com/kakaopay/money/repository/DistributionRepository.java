package com.kakaopay.money.repository;

import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import com.kakaopay.money.model.Distribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Repository
public interface DistributionRepository extends JpaRepository<Distribution, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "10000")})
    Optional<Distribution> findById(Long id);
}
