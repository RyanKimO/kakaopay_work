package com.kakaopay.money.controller;

import com.kakaopay.money.common.ResponseData;
import com.kakaopay.money.common.ResponseFactory;
import com.kakaopay.money.common.exception.DataNotFoundException;
import com.kakaopay.money.common.exception.readable.UnReadableException;
import com.kakaopay.money.common.exception.token.InvalidTokenException;
import com.kakaopay.money.common.validator.TokenValidator;
import com.kakaopay.money.dto.DistributionDTO;
import com.kakaopay.money.service.DistributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@RestController
@RequestMapping("/distribution")
public class DistributionController {

    public static final String USER_ID_ATTR = "X-USER-ID";
    public static final String ROOM_ID_ATTR = "X-ROOM-ID";

    private final DistributionService distributionService;


    @Autowired
    public DistributionController(DistributionService distributionService) {
        this.distributionService = distributionService;
    }


    @GetMapping("/{token}")
    public ResponseEntity<ResponseData<DistributionDTO>> getDistribution(@PathVariable String token,
            @RequestHeader(name = USER_ID_ATTR) Long userId) {
        try {
            TokenValidator.validateToken(token);
            DistributionDTO dto = distributionService.getDistributionDTO(token, userId);
            return ResponseFactory.createResponse(dto, HttpStatus.OK, "뿌리기 조회가 완료되었습니다.");
        } catch(UnReadableException | InvalidTokenException | DataNotFoundException e) {
            return ResponseFactory.createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<ResponseData<String>> createDistribution(
            @RequestHeader(name = USER_ID_ATTR) Long userId,
            @RequestHeader(name = ROOM_ID_ATTR) String roomId, @RequestParam Long amount,
            @RequestParam Integer divCount) {

        String token = distributionService.createDistribution(userId, roomId, amount, divCount);

        return ResponseFactory.createResponse(token, HttpStatus.OK, "뿌리기가 완료되었습니다.");
    }


    @PutMapping("/{token}/dividends")
    public ResponseEntity<ResponseData<String>> receiveDividend(
            @RequestHeader(name = USER_ID_ATTR) Long userId,
            @RequestHeader(name = ROOM_ID_ATTR) String roomId, @PathVariable String token) {
        TokenValidator.validateToken(token);

        Long receviedAmount = distributionService.receiveDividend(userId, roomId, token);

        return ResponseFactory.createResponse(token, HttpStatus.OK, "뿌리기가 완료되었습니다.");
    }

}
