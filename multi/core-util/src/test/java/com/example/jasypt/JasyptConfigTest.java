package com.example.jasypt;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "spring.config.location = " +
    "classpath:/application-core.yml"
)
class JasyptConfigTest {

    @Autowired
    StringEncryptor encryptor;

    @Test
    void jwtSecretEncodeTest() {

        // given
        String secret = "j6hkdAS6dhv57TV7dUl3VV2S5W6CdCC+d6VR7PzlwrV9Q=";

        // when
        String encryptedSecret = encryptor.encrypt(secret);
        String decrypt = encryptor.decrypt(encryptedSecret);

        // then
        System.out.println("encryptedSecret = " + encryptedSecret);
        System.out.println("decrypt = " + decrypt);

    }

    @Test
    void dbInfoEncodeTest() {

        // given
        String url = "jdbc:mysql://localhost:3306/member_module";
        String username = "root";
        String password = "admin";

        // when
        String encryptedUrl = encryptor.encrypt(url);
        String encryptedUsername = encryptor.encrypt(username);
        String encryptedPassword = encryptor.encrypt(password);

        String decryptedUrl = encryptor.decrypt(encryptedUrl);
        String decryptedUsername = encryptor.decrypt(encryptedUsername);
        String decryptedPassword = encryptor.decrypt(encryptedPassword);

        // then
        System.out.println("encryptedUrl = " + encryptedUrl);
        System.out.println("encryptedUsername = " + encryptedUsername);
        System.out.println("encryptedPassword = " + encryptedPassword);

        System.out.println("decryptedUrl = " + decryptedUrl);
        System.out.println("decryptedUsername = " + decryptedUsername);
        System.out.println("decryptedPassword = " + decryptedPassword);

    }

}