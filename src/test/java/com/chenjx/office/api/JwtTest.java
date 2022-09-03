package com.chenjx.office.api;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.chenjx.office.api.common.util.JwtUtil.createJWT;
import static com.chenjx.office.api.common.util.JwtUtil.parseJWT;

@SpringBootTest
public class JwtTest {

    //jwt生成测试
    @Test
    public void JwtGenerator(){
        String jwt = createJWT("2123");
        System.out.println("jwt:" + jwt);
    }

    //通过jwt获取信息
    @Test
    public void JwtGetObject() throws Exception {
        Claims claims = parseJWT("");
        String subject = claims.getSubject();
        System.out.println(subject);
        System.out.println(claims);
    }
}
