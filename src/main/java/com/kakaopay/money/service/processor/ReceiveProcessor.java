package com.kakaopay.money.service.processor;

import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DividendRepository;
import com.kakaopay.money.service.processor.strategy.pick.DividendPickStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Component
public class ReceiveProcessor {

    private final DividendRepository dividendRepository;


    @Autowired
    public ReceiveProcessor(
            DividendRepository dividendRepository) {
        this.dividendRepository = dividendRepository;
    }


    @Transactional
    public Long process(Distribution distribution, Long receiverId, DividendPickStrategy strategy) {

        Dividend picked = strategy.pick(distribution);
        picked.setReceiverId(receiverId);
        picked.setDistribution(distribution);
        dividendRepository.save(picked);

        return picked.getAmount();
    }

}
