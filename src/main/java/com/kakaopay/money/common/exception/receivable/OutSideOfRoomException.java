package com.kakaopay.money.common.exception.receivable;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class OutSideOfRoomException extends UnReceivableException {

    public OutSideOfRoomException() {
        super("뿌리기 받기는 같은 방에서만 가능합니다.");
    }
}
