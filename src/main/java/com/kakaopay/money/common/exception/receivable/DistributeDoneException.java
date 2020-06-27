package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class DistributeDoneException extends UnReceivableException {

    public DistributeDoneException() {
        super("해당 뿌리기에 대한 받기가 모두 완료되었습니다.");
    }
}
