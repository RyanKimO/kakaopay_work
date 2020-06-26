package com.kakaopay.money.common.exception.readable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class ReadableTimeExpiredException extends UnReadableException {

    public ReadableTimeExpiredException() {
        super("뿌린 건에 대한 조회가능 시간이 만료되었습니다.");
    }
}
