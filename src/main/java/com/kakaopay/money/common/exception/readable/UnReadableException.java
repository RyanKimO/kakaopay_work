package com.kakaopay.money.common.exception.readable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public abstract class UnReadableException extends RuntimeException {

    public UnReadableException(String message) {
        super(message);
    }
}
