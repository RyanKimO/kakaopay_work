package com.kakaopay.money.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import com.kakaopay.money.common.exception.readable.NotDistributionOwnerException;
import com.kakaopay.money.common.exception.readable.ReadableTimeExpiredException;
import com.kakaopay.money.common.exception.receivable.AlreadyReceivedException;
import com.kakaopay.money.common.exception.receivable.DistributeDoneException;
import com.kakaopay.money.common.exception.receivable.OutSideOfRoomException;
import com.kakaopay.money.common.exception.receivable.OwnDistributionException;
import com.kakaopay.money.common.exception.receivable.ReceivableTimeExpiredException;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DistributionRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
class DistributionServiceTest {

    @Autowired
    private DistributionService sut;

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
    @Transactional
    public void getDistributionDTO() {
        // Given
        DistributionDTO expected = new DistributionDTO();
        expected.setInitialAmount(sample.getAmount());
        expected.setDistributedAt(sample.getCreatedAt());
        expected.setDistributedAmount(500L);

        // When
        DistributionDTO res =
                sut.getDistributionDTO(String.valueOf(sample.getId()), sample.getOwnerId());

        // Then
        assertEquals(expected.getDistributedAmount(), res.getDistributedAmount());
        assertEquals(expected.getInitialAmount(), res.getInitialAmount());
        assertEquals(expected.getDistributedAt(), res.getDistributedAt());
    }


    @Test
    @Transactional
    public void getDistributionDTO_throws_UnReadableException() {
        Long noOwnerId = 777L;
        assertThrows(NotDistributionOwnerException.class,
                () -> sut.getDistributionDTO(String.valueOf(sample.getId()), noOwnerId));

        sample.setCreatedAt(LocalDateTime.now().minusDays(10));
        assertThrows(ReadableTimeExpiredException.class,
                () -> sut.getDistributionDTO(String.valueOf(sample.getId()), sample.getOwnerId()));
    }


    @Test
    @Transactional
    public void receiveDividend() {
        // Given
        Long receiverId = 3L;

        // When
        Long receiveAmount =
                sut.receiveDividend(receiverId, sample.getRoomId(), String.valueOf(sample.getId()));

        // Then
        assertEquals(500L, receiveAmount);
    }


    @Test
    @Transactional
    public void receiveDividend_throws_UnReceivableException() {
        assertThrows(OwnDistributionException.class, () -> sut
                .receiveDividend(sample.getOwnerId(), sample.getRoomId(),
                        String.valueOf(sample.getId())));

        Long alreadyReceived = 2L;
        assertThrows(AlreadyReceivedException.class, () -> sut
                .receiveDividend(alreadyReceived, sample.getRoomId(),
                        String.valueOf(sample.getId())));

        sample.setCreatedAt(LocalDateTime.now().minusDays(10));
        assertThrows(ReceivableTimeExpiredException.class,
                () -> sut.receiveDividend(3L, sample.getRoomId(), String.valueOf(sample.getId())));

        sample.setCreatedAt(LocalDateTime.now());
        String outSideRoomId = "room5";
        assertThrows(OutSideOfRoomException.class,
                () -> sut.receiveDividend(3L, outSideRoomId, String.valueOf(sample.getId())));

        sample.getDividends().forEach(e -> e.setReceiverId(1L));
        assertThrows(DistributeDoneException.class,
                () -> sut.receiveDividend(3L, sample.getRoomId(), String.valueOf(sample.getId())));
    }


}