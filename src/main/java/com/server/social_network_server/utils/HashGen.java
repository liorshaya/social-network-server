package com.server.social_network_server.utils;

import jakarta.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashGen {

    public String hashSHA (String username, String password) {
        String source = username + password;
        try {
            return DatatypeConverter.printHexBinary( MessageDigest.getInstance("SHA-256").digest(source.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
