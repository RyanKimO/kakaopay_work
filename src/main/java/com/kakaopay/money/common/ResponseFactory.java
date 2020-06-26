package com.kakaopay.money.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class ResponseFactory {

    public static <T> ResponseEntity<ResponseData<T>> createResponse(HttpStatus status,
            String message) {
        return new ResponseEntity<>(new ResponseData<>(null, message), status);
    }


    public static <T> ResponseEntity<ResponseData<T>> createResponse(T data, HttpStatus status,
            String message) {
        return new ResponseEntity<>(new ResponseData<>(data, message), status);
    }
}
