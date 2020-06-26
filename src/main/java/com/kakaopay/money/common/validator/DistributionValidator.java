package com.kakaopay.money.common.validator;

import java.time.LocalDateTime;
import com.kakaopay.money.common.exception.readable.NotDistributionOwnerException;
import com.kakaopay.money.common.exception.readable.ReadableTimeExpiredException;
import com.kakaopay.money.model.Distribution;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
public class DistributionValidator {

    private static final int READABLE_DAYS = 7;
    private static final int RECEIVABLE_MINUTES = 10;


    private DistributionValidator() {}


    public static void validateReadable(Distribution distribution, Long userId) {
        if(!distribution.getOwnerId().equals(userId))
            throw new NotDistributionOwnerException();
        if(!isReadableDate(distribution))
            throw new ReadableTimeExpiredException();
    }


    private static boolean isReadableDate(Distribution distribution) {
        LocalDateTime readableEnd = distribution.getCreatedAt().plusDays(READABLE_DAYS);
        return LocalDateTime.now().isBefore(readableEnd);
    }


    public static boolean isReceivable(Distribution distribution, String roomId, Long userId) {
        if(distribution.getOwnerId().equals(userId))
            return false;
        if(!distribution.getRoomId().equals(roomId))
            return false;

        return isReceivableTime(distribution);
    }


    private static boolean isReceivableTime(Distribution distribution) {
        LocalDateTime receivableEnd = distribution.getCreatedAt().plusMinutes(RECEIVABLE_MINUTES);
        return LocalDateTime.now().isBefore(receivableEnd);
    }
}
