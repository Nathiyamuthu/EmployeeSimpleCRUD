package com.example.Employee1SimpleCRUD.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
public class RedisEncryptionSerializer implements RedisSerializer<Object> {

    private final RedisSerializer<Object> serializer;
    private final SecretKeySpec secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public RedisEncryptionSerializer(
            @Value("${redis.encryption.key}") String key) {

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        BasicPolymorphicTypeValidator validator =
                BasicPolymorphicTypeValidator.builder()
                        .allowIfSubType("com.example.Employee1SimpleCRUD")
                        .allowIfSubType("java.util")
                        .allowIfSubType("java.lang")
                        .build();

        objectMapper.activateDefaultTyping(
                validator,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        this.serializer =
                new GenericJackson2JsonRedisSerializer(objectMapper);

        if (key == null || key.length() != 32) {
            throw new IllegalArgumentException(
                    "Redis encryption key must be exactly 32 characters"
            );
        }

        this.secretKey =
                new SecretKeySpec(
                        key.getBytes(StandardCharsets.UTF_8),
                        "AES"
                );
    }

    @Override
    public byte[] serialize(Object value) {

        if (value == null) {
            return new byte[0];
        }

        try {
            byte[] jsonBytes = serializer.serialize(value);

            byte[] iv = new byte[12];
            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec spec =
                    new GCMParameterSpec(128, iv);

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    spec
            );

            byte[] encryptedBytes =
                    cipher.doFinal(jsonBytes);

            byte[] result =
                    new byte[iv.length + encryptedBytes.length];

            System.arraycopy(
                    iv,
                    0,
                    result,
                    0,
                    iv.length
            );

            System.arraycopy(
                    encryptedBytes,
                    0,
                    result,
                    iv.length,
                    encryptedBytes.length
            );

            return result;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Redis encryption failed",
                    e
            );
        }
    }

    @Override
    public Object deserialize(byte[] bytes) {

        if (bytes == null || bytes.length == 0) {
            return null;
        }

        try {
            byte[] iv = new byte[12];

            System.arraycopy(
                    bytes,
                    0,
                    iv,
                    0,
                    12
            );

            byte[] encryptedBytes =
                    new byte[bytes.length - 12];

            System.arraycopy(
                    bytes,
                    12,
                    encryptedBytes,
                    0,
                    encryptedBytes.length
            );

            Cipher cipher =
                    Cipher.getInstance("AES/GCM/NoPadding");

            GCMParameterSpec spec =
                    new GCMParameterSpec(128, iv);

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    spec
            );

            byte[] decryptedBytes =
                    cipher.doFinal(encryptedBytes);

            return serializer.deserialize(decryptedBytes);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Redis decryption failed",
                    e
            );
        }
    }
}