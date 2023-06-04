package com.nhn.minidooray.accountapi.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtills {
  private static final int DEFAULT_ITERATIONS = 1024;

  private PasswordUtills() {
    throw new IllegalStateException("Utility class");
  }

  public static String simple(String rawPassword) {
    byte[] digest = null;

    return bytesToHex(digest);
  }

  /**
   * TODO 암호화 적용
   */
  public static String encode(String rawPassword){
    return rawPassword;
  }
  public static String encode(String rawPassword, byte[] salt) {
    return encode(rawPassword, salt, DEFAULT_ITERATIONS);
  }

  public static String encode(String rawPassword, byte[] salt, int iterations) {
    byte[] digest = null;

    return bytesToHex(digest);
  }

  private static byte[] sha256(String rawPassword, byte[] salt, int iterations) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    digest.reset();
    digest.update(salt);

    byte[] input = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
    for (int i = 0; i < iterations; i++) {
      digest.reset();
      input = digest.digest(input);
    }

    return input;
  }

  private static byte[] sha256WithoutSaltAndIterations(String rawPassword) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    digest.reset();
    return digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder();
    for (byte b: bytes) {
      sb.append(String.format("%02x", b));
    }
    return sb.toString();
  }

}
