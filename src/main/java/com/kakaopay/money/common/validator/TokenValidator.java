package com.kakaopay.money.common.validator;

import com.kakaopay.money.common.exception.token.InvalidTokenException;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
public class TokenValidator {

    private static final int TOKEN_LENGTH = 3;


    private TokenValidator() {}


    public static void validateToken(String token) {
        if(token.length() != TOKEN_LENGTH)
            throw new InvalidTokenException();
    }
}
