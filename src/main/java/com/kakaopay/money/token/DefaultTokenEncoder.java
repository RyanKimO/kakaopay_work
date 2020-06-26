package com.kakaopay.money.token;

import org.springframework.stereotype.Component;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
@Component
public class DefaultTokenEncoder implements TokenEncoder {

    @Override
    public String encode(Long distributionId) {
        return String.valueOf(distributionId);
    }


    @Override
    public Long decode(String token) {
        return Long.valueOf(token);
    }
}
