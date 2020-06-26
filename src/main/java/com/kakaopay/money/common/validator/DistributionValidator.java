package com.kakaopay.money.common.validator;

import java.time.LocalDateTime;
import com.kakaopay.money.common.exception.readable.NotDistributionOwnerException;
import com.kakaopay.money.common.exception.readable.ReadableTimeExpiredException;
import com.kakaopay.money.common.exception.receivable.OutSideOfRoomException;
import com.kakaopay.money.common.exception.receivable.OwnDistributionException;
import com.kakaopay.money.common.exception.receivable.ReceivableTimeExpiredException;
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


    public static void validateReceivable(Distribution distribution, Long userId, String roomId) {
        if(distribution.getOwnerId().equals(userId))
            throw new OwnDistributionException();
        if(!distribution.getRoomId().equals(roomId))
            throw new OutSideOfRoomException();

        if(!isReceivableTime(distribution))
            throw new ReceivableTimeExpiredException();
    }


    private static boolean isReceivableTime(Distribution distribution) {
        LocalDateTime receivableEnd = distribution.getCreatedAt().plusMinutes(RECEIVABLE_MINUTES);
        return LocalDateTime.now().isBefore(receivableEnd);
    }
}
