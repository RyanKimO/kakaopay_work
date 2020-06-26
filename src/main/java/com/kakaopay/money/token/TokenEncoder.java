package com.kakaopay.money.token;

/**
 * Developer : ryan kim
 * Date : 2020-06-26
 */
public interface TokenEncoder {

    String encode(Long distributionId);

    Long decode(String token);
}
