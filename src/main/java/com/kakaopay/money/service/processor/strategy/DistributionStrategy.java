package com.kakaopay.money.service.processor.strategy;

import java.util.Set;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public interface DistributionStrategy {

    Set<Dividend> distribute(Distribution distribution);
}
