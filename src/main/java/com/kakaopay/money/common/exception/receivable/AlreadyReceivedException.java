package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class AlreadyReceivedException extends UnReceivableException {

    public AlreadyReceivedException() {
        super("해당 뿌리기에서 이미 받으셨습니다.");
    }
}
