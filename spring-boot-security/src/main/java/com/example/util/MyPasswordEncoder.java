package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Slf4j
public class MyPasswordEncoder implements PasswordEncoder {
    //private final static String SALT = RandomStringUtils.randomAlphanumeric(6);
    @Override
    public String encode(CharSequence charSequence) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(charSequence,s);
    }
}
