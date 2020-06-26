package com.kakaopay.money.utils;

import com.kakaopay.money.model.Distribution;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
public class DistributionValidator {

    private static final int TOKEN_LENGTH = 3;
    private static final int READABLE_DAYS = 7;
    private static final int DISTRIBUTABLE_MINUTES = 10;


    private DistributionValidator() {}


    public static boolean isValidToken(String token) {
        return token.length() == TOKEN_LENGTH;
    }


    public static boolean isReadable(Distribution distribution, Long userId) {
        // userId 맞는지확인
        distribution.getOwnerId();

        distribution.getCreatedAt();
        // 지금이 7일 안쪽인지 확인
        return true;
    }


    public static boolean isReceivable(Distribution distribution, Long userId) {
        // 자기 아닌지 확인

        // 방번호 확인
        distribution.getCreatedAt();
        // 지금이 10분 안쪽인지 확인
        return true;
    }
}
