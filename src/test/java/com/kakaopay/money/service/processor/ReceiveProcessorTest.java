package com.kakaopay.money.service.processor;

import java.util.Arrays;
import java.util.HashSet;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.service.processor.strategy.pick.SequentialPickStrategy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@SpringBootTest
class ReceiveProcessorTest {

    @Autowired
    private ReceiveProcessor sut;

    @Autowired
    private DistributionRepository repository;


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

        repository.save(sample);
    }


    @Test
    public void process_success() {
        Long amount = sut.process(sample, 2L, new SequentialPickStrategy());
        assertNotNull(amount);
    }

}