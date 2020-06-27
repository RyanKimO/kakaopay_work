package com.kakaopay.money.common.validator;

import com.kakaopay.money.common.exception.token.InvalidTokenException;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class TokenValidator {

    private static final int TOKEN_LENGTH = 3;
    private static final int TEMP_TOKEN_LENGTH = 1;


    private TokenValidator() {}


    public static void validateToken(String token) {
        if(token.length() < TEMP_TOKEN_LENGTH)
            throw new InvalidTokenException();
    }
}
