package com.kakaopay.money.service.processor.picker;

import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public interface DividendPicker {

    Dividend pick(Distribution distribution);
}
