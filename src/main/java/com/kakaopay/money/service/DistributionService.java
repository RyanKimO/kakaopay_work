package com.kakaopay.money.service;

import com.kakaopay.money.common.exception.DataNotFoundException;
import com.kakaopay.money.common.validator.DistributionValidator;
import com.kakaopay.money.converter.DistributionConverter;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.service.processor.DistributionProcessor;
import com.kakaopay.money.service.processor.strategy.UnFairDistributionStrategy;
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
    private final TokenEncoder tokenEncoder;


    @Autowired
    public DistributionService(DistributionRepository distributionRepository,
            @Qualifier("defaultTokenEncoder") TokenEncoder tokenEncoder,
            DistributionConverter distributionConverter,
            DistributionProcessor distributionProcessor) {
        this.distributionRepository = distributionRepository;
        this.tokenEncoder = tokenEncoder;
        this.distributionConverter = distributionConverter;
        this.distributionProcessor = distributionProcessor;
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


    private Distribution getDistributionByToken(String token) {
        Long distributionId = tokenEncoder.decode(token);

        return distributionRepository.findById(distributionId)
                .orElseThrow(() -> new DataNotFoundException(token + " 조회결과 없음"));
    }

}
