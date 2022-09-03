package com.chenjx.office.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootTest
public class BCryptPasswordTest {

    @Resource
    BCryptPasswordEncoder encoder;

    @Test
    public void encode(){
        String encodePassword = encoder.encode("123456");
        System.out.println(encodePassword);
        String encodePassword2 = encoder.encode("123456");
        System.out.println(encodePassword2);
    }
    @Test
    public void matches(){
        System.out.println(encoder.matches("",""));
    }
}
