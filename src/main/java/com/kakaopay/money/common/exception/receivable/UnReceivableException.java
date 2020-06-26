package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public abstract class UnReceivableException extends RuntimeException {

    public UnReceivableException(String message) {
        super(message);
    }
}
