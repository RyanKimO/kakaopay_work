package com.kakaopay.money.service;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.dto.DividendDTO;
import com.kakaopay.money.model.Distribution;
import com.kakaopay.money.model.Dividend;
import com.kakaopay.money.repository.DistributionRepository;
import com.kakaopay.money.token.TokenEncoder;
import com.kakaopay.money.utils.DistributionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */


/**
 * ○ 뿌리기 시 발급된 token을 요청값으로 받습니다.
 * ○ token에 해당하는 뿌리기 건의 현재 상태를 응답값으로 내려줍니다. 현재
 * 상태는 다음의 정보를 포함합니다.
 * ○ 뿌린시각,뿌린금액,받기완료된금액,받기완료된정보([받은금액,받은
 * 사용자 아이디] 리스트)
 * ○ 뿌린 사람 자신만 조회를 할 수 있습니다. 다른사람의 뿌리기건이나 유효하지
 * 않은 token에 대해서는 조회 실패 응답이 내려가야 합니다.
 * ○ 뿌린건에대한조회는7일동안할수있습니다.
 */
@Service
public class DistributionService {

    private final DistributionRepository distributionRepository;
    private final TokenEncoder tokenEncoder;


    @Autowired
    public DistributionService(DistributionRepository distributionRepository,
            @Qualifier("defaultTokenEncoder") TokenEncoder tokenEncoder) {
        this.distributionRepository = distributionRepository;
        this.tokenEncoder = tokenEncoder;
    }


    public DistributionDTO getDistributionDTO(String token, Long userId) {
        // token 디코딩
        Long distributionId = tokenEncoder.decode(token);

        Distribution distribution = distributionRepository.findById(distributionId)
                .orElseThrow(NoSuchElementException::new);

        // validate 거침
        DistributionValidator.isReadable(distribution, userId);

        return convert(distribution);
    }


    private DistributionDTO convert(Distribution distribution) {
        DistributionDTO dto = new DistributionDTO();
        dto.setInitialAmount(distribution.getAmount());
        dto.setDistributedAt(distribution.getCreatedAt());

        dto.setDividends(distribution.getDividends().stream().filter(e -> e.getReceiverId() != null)
                .map(this::convertDividend).collect(Collectors.toList()));

        dto.setDistributedAmount(
                dto.getDividends().stream().mapToLong(DividendDTO::getAmount).sum());
        return dto;
    }


    private DividendDTO convertDividend(Dividend dividend) {
        DividendDTO dividendDTO = new DividendDTO();
        dividendDTO.setAmount(dividend.getAmount());
        dividendDTO.setReceiverId(dividend.getAmount());
        return dividendDTO;
    }


}
