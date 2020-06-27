package com.kakaopay.money.service.processor.picker;

import com.kakaopay.money.common.exception.receivable.DistributeDoneException;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import org.springframework.stereotype.Component;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Component
public class SequentialDividendPicker implements DividendPicker {

    public Dividend pick(Distribution distribution) {
        return distribution.getDividends().stream().filter(Dividend::isNotReceived).findAny()
                .orElseThrow(DistributeDoneException::new);
    }
}
