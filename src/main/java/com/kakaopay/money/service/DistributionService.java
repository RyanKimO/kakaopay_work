package com.kakaopay.money.service;

import com.kakaopay.money.common.exception.DataNotFoundException;
import com.kakaopay.money.common.validator.DistributionValidator;
import com.kakaopay.money.converter.DistributionConverter;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.service.processor.DistributionProcessor;
import com.kakaopay.money.service.processor.ReceiveProcessor;
import com.kakaopay.money.service.processor.strategy.distribution.UnFairDistributionStrategy;
import com.kakaopay.money.service.processor.strategy.pick.SequentialPickStrategy;
import com.kakaopay.money.token.TokenEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final DistributionConverter distributionConverter;
    private final DistributionProcessor distributionProcessor;
    private final ReceiveProcessor receiveProcessor;
    private final TokenEncoder tokenEncoder;


    @Autowired
    public DistributionService(DistributionRepository distributionRepository,
            DistributionConverter distributionConverter,
            DistributionProcessor distributionProcessor, ReceiveProcessor receiveProcessor,
            @Qualifier("defaultTokenEncoder") TokenEncoder tokenEncoder) {
        this.distributionRepository = distributionRepository;
        this.tokenEncoder = tokenEncoder;
        this.distributionConverter = distributionConverter;
        this.distributionProcessor = distributionProcessor;
        this.receiveProcessor = receiveProcessor;
    }


    @Transactional
    public String createDistribution(Long userId, String roomId, Long amount, Integer divCount) {
        Distribution distribution = distributionProcessor
                .process(userId, roomId, amount, divCount, new UnFairDistributionStrategy());
        return tokenEncoder.encode(distribution.getId());
    }


    @Transactional(readOnly = true)
    public DistributionDTO getDistributionDTO(String token, Long userId) {
        Distribution distribution = getDistributionByToken(token);

        DistributionValidator.validateReadable(distribution, userId);

        return distributionConverter.convert(distribution);
    }


    @Transactional
    public Long receiveDividend(Long userId, String roomId, String token) {
        Distribution distribution = getDistributionByToken(token);

        DistributionValidator.validateReceivable(distribution, userId, roomId);

        return receiveProcessor.process(distribution, userId, new SequentialPickStrategy());
    }


    private Distribution getDistributionByToken(String token) {
        Long distributionId = tokenEncoder.decode(token);

        return distributionRepository.findById(distributionId)
                .orElseThrow(() -> new DataNotFoundException(token + " 조회결과 없음"));
    }

}
