package com.kakaopay.money.service.processor.strategy.pick;

import java.util.Arrays;
import java.util.HashSet;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
class SequentialPickStrategyTest {

    private Distribution sample;


    @BeforeEach
    public void setUp() {
        Dividend dividend1 = new Dividend();
        Dividend dividend2 = new Dividend();

        dividend1.setAmount(500L);
        dividend2.setAmount(500L);
        dividend2.setReceiverId(2L);

        sample = new Distribution();
        sample.setOwnerId(1L);
        sample.setDividedCnt(2);
        sample.setRoomId("room1");
        sample.setAmount(1000L);
        sample.setDividends(new HashSet<>(Arrays.asList(dividend1, dividend2)));
        sample.setDistributionToDividends();
    }


    @Test
    public void pick() {
        SequentialPickStrategy sequentialPickStrategy = new SequentialPickStrategy();
        Dividend res = sequentialPickStrategy.pick(sample);

        Assert.assertTrue(sample.getDividends().contains(res));
    }
}