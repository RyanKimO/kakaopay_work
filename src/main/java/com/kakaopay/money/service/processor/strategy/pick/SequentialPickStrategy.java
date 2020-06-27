package com.kakaopay.money.service.processor.strategy.pick;

import com.kakaopay.money.common.exception.receivable.DistributeDoneException;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class SequentialPickStrategy implements DividendPickStrategy {

    public Dividend pick(Distribution distribution) {
        return distribution.getDividends().stream().filter(Dividend::isNotReceived).findFirst()
                .orElseThrow(DistributeDoneException::new);
    }
}
