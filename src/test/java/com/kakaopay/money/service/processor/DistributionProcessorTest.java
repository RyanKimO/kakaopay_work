package com.kakaopay.money.service.processor;

import java.util.NoSuchElementException;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.service.processor.strategy.UnFairDistributionStrategy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@SpringBootTest
class DistributionProcessorTest {

    @Autowired
    private DistributionProcessor sut;

    @Autowired
    private DistributionRepository repository;


    @Test
    @Transactional
    void process() {
        Distribution res = sut.process(1L, "room1", 1000L, 10, new UnFairDistributionStrategy());

        Distribution founded =
                repository.findById(res.getId()).orElseThrow(NoSuchElementException::new);

        long resSum = res.getDividends().stream().mapToLong(Dividend::getAmount).sum();
        long foundedSum = founded.getDividends().stream().mapToLong(Dividend::getAmount).sum();

        assertEquals(res.getId(), founded.getId());
        assertEquals(resSum, foundedSum);
    }
}