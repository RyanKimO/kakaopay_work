package com.kakaopay.money.service.processor.strategy.pick;

import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public interface DividendPickStrategy {

    Dividend pick(Distribution distribution);
}
