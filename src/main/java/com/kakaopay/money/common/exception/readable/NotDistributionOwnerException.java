package com.kakaopay.money.common.exception.readable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class NotDistributionOwnerException extends UnReadableException {

    public NotDistributionOwnerException() {
        super("뿌린 사람 자신만 조회가 가능합니다.");
    }
}
