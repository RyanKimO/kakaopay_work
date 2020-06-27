package com.kakaopay.money.service.processor.strategy;

import java.util.Set;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.service.processor.strategy.distribution.UnFairDistributionStrategy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
class UnFairDistributionStrategyTest {

    @Test
    public void distribute() {
        UnFairDistributionStrategy strategy = new UnFairDistributionStrategy();
        Distribution distribution = new Distribution();
        distribution.setAmount(999L);
        distribution.setDividedCnt(5);
        Set<Dividend> res = strategy.distribute(distribution);

        long resSum = res.stream().mapToLong(Dividend::getAmount).sum();

        assertEquals(distribution.getAmount(), resSum);
    }

}