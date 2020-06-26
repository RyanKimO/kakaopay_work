package com.kakaopay.money.common;

import lombok.Getter;
import lombok.Setter;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
@Setter
@Getter
public class ResponseData<T> {

    private T data;
    private String message;


    public ResponseData(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
