package com.hrdate.oj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class OjApplicationTests {
    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    void contextLoads() {
        System.out.println(bCryptPasswordEncoder.encode("admin"));
        System.out.println(BCrypt.hashpw("admin", BCrypt.gensalt()));
    }

}
