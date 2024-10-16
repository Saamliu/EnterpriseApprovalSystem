package org.example;

import org.example.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTtest {
    @Test
    public void test01() {
        String encryptPassword = JWTUtil.encryptPassword("123456");
        System.out.println(encryptPassword);
    }

    @Test
    public void test02() {
        String decryptPassword = JWTUtil.decryptPassword("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJrZXkiOiIxMjM0NTYifQ.S_Bfb9Wiur_EKOe8ugm9uemHQMcq13gf6d8p3QBTim4");
        System.out.println(decryptPassword);
    }
}
