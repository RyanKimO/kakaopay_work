package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class OwnDistributionException extends UnReceivableException {

    public OwnDistributionException() {
        super("뿌린 사람 자신은 받기가 불가능합니다.");
    }
}
