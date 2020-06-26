package com.kakaopay.money.service.processor;

import java.util.Set;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.service.processor.strategy.DistributionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Component
public class DistributionProcessor {

    private final DistributionRepository distributionRepository;


    @Autowired
    public DistributionProcessor(DistributionRepository distributionRepository) {
        this.distributionRepository = distributionRepository;
    }


    @Transactional
    public Distribution process(Long userId, String roomId, Long amount, Integer divCount,
            DistributionStrategy strategy) {
        Distribution distribution = new Distribution();
        distribution.setOwnerId(userId);
        distribution.setDividedCnt(divCount);
        distribution.setRoomId(roomId);
        distribution.setAmount(amount);

        Set<Dividend> dividends = strategy.distribute(distribution);
        distribution.setDividends(dividends);
        distribution.setDistributionToDividends();

        return distributionRepository.save(distribution);
    }
}
