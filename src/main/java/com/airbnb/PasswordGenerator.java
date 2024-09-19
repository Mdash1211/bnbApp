package com.airbnb;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordGenerator {
    public static void main(String[] args) {
        String gensalt = BCrypt.gensalt(8);
        String password = BCrypt.hashpw("testing123", gensalt);
        System.out.println(password);
    }
}
