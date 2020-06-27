package com.kakaopay.money.token;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Developer : ryan kim
 * Date : 2020-06-27
 */
class DefaultTokenEncoderTest {

    @Test
    public void getUniCode() {
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < 99999; i++) {
            String numCode = Character.toString(i);
            Integer count = map.get(numCode);
            if(count == null) {
                map.put(numCode, 1);
            } else {
                map.put(numCode, ++count);
            }
        }
        boolean res = map.entrySet().stream().anyMatch(e -> e.getValue() > 1);
        System.out.println(res);

        map.entrySet().stream().forEach(e -> {
            if(e.getKey().length() == 2)
                System.out.println("@@@@ : " + e.getKey());
        });

        //System.out.println(allMatch);


    }

}