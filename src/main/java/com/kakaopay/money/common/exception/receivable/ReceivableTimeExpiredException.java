package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class ReceivableTimeExpiredException extends UnReceivableException {

    public ReceivableTimeExpiredException() {
        super("뿌린 건에 대한 받기 가능시간이 만료되었습니다.");
    }
}
