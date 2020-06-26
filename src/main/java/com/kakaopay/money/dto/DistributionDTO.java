package com.kakaopay.money.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 * <p>
 * 뿌린시각,뿌린금액,받기완료된금액,받기완료된정보([받은금액,받은
 * 사용자 아이디] 리스트)
 */
@Data
public class DistributionDTO {

    private Long initialAmount;
    private Long distributedAmount;
    private LocalDateTime distributedAt;

    private List<DividendDTO> distributedHistory = new ArrayList<>();
}
