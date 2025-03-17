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
        String memberUrl = "jdbc:mysql://localhost:3306/member_module";
        String boardUrl = "jdbc:mysql://localhost:3306/board_module";
        String username = "root";
        String password = "admin";

        // when
        String encryptedMemberUrl = encryptor.encrypt(memberUrl);
        String encryptedBoardUrl = encryptor.encrypt(boardUrl);
        String encryptedUsername = encryptor.encrypt(username);
        String encryptedPassword = encryptor.encrypt(password);

        String decryptedMemberUrl = encryptor.decrypt(encryptedMemberUrl);
        String decryptedBoardUrl = encryptor.decrypt(encryptedBoardUrl);
        String decryptedUsername = encryptor.decrypt(encryptedUsername);
        String decryptedPassword = encryptor.decrypt(encryptedPassword);

        // then
        System.out.println("encryptedMemberUrl = " + encryptedMemberUrl);
        System.out.println("encryptedBoardUrl = " + encryptedBoardUrl);
        System.out.println("encryptedUsername = " + encryptedUsername);
        System.out.println("encryptedPassword = " + encryptedPassword);

        System.out.println("decryptedMemberUrl = " + decryptedMemberUrl);
        System.out.println("decryptedBoardUrl = " + decryptedBoardUrl);
        System.out.println("decryptedUsername = " + decryptedUsername);
        System.out.println("decryptedPassword = " + decryptedPassword);

    }

}