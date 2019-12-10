package com.smartbird.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void generateTokenString() {
        Map<String,Long> timeClaims = new HashMap<>();
        timeClaims.put("name",100L);
        try {
            System.out.println(JwtUtil.generateTokenString("test",timeClaims));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}