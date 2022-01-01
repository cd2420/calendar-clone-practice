package com.lim.core;

import com.lim.core.domain.entity.User;
import com.lim.core.util.Encryptor;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private final Encryptor alwaysMatchEncryptor = new Encryptor() {
        @Override
        public String encrypt(String origin) {
            return origin;
        }

        @Override
        public boolean isMatch(String origin, String hashed) {
            return true;
        }
    };

    @Test
    void isMatchTest() {
        final User me = new User("meme", "email", "pw", LocalDateTime.now());
        assertEquals(true, me.isMatch(alwaysMatchEncryptor, me.getPassword()));
    }
}
