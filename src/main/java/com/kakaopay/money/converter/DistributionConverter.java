package com.kakaopay.money.converter;

import java.util.stream.Collectors;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.dto.DividendDTO;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import org.springframework.stereotype.Component;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Component
public class DistributionConverter {

    public DistributionDTO convert(Distribution distribution) {
        DistributionDTO dto = new DistributionDTO();
        dto.setInitialAmount(distribution.getAmount());
        dto.setDistributedAt(distribution.getCreatedAt());

        dto.setDistributedHistory(
                distribution.getDividends().stream().filter(e -> e.getReceiverId() != null)
                        .map(this::convertDividend).collect(Collectors.toList()));

        dto.setDistributedAmount(
                dto.getDistributedHistory().stream().mapToLong(DividendDTO::getAmount).sum());
        return dto;
    }


    private DividendDTO convertDividend(Dividend dividend) {
        DividendDTO dividendDTO = new DividendDTO();
        dividendDTO.setAmount(dividend.getAmount());
        dividendDTO.setReceiverId(dividend.getReceiverId());
        return dividendDTO;
    }
}
