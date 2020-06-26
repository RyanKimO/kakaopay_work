package com.kakaopay.money.common.exception.token;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
