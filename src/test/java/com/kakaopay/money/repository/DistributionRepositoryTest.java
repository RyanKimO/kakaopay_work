package com.kakaopay.money.repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NoSuchElementException;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@SpringBootTest
class DistributionRepositoryTest {

    @Autowired
    private DistributionRepository sut;

    private Distribution sample;


    @BeforeEach
    public void setUp() {
        Dividend dividend1 = new Dividend();
        Dividend dividend2 = new Dividend();

        dividend1.setAmount(500L);
        dividend2.setAmount(500L);
        sample = new Distribution();
        sample.setOwnerId(1L);
        sample.setDividedCnt(2);
        sample.setRoomId("room1");
        sample.setAmount(1000L);
        sample.setDividends(new HashSet<>(Arrays.asList(dividend1, dividend2)));
        sample.setDistributionToDividends();
    }


    @Test
    @Transactional
    public void find_with_relations() {
        // Given
        sut.save(sample);

        // When
        Distribution res = sut.findById(1L).orElseThrow(NoSuchElementException::new);

        // Then
        assertEquals(sample.getId(), res.getId());
        assertEquals(sample.getDividends().size(), res.getDividends().size());
    }

}