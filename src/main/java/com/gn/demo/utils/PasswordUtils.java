package com.gn.demo.utils;

import com.gn.demo.config.security.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/*
 * @Description: 加密工具
 * @Author: GuiNing
 * @Date: 2023/9/5 15:24
 */
@Slf4j
public class PasswordUtils {

    /**
     * 校验密码是否一致
     *
     * @param password       前端传过来的密码
     * @param hashedPassword 数据库中储存的加密过后的密码
     * @param salt           盐值
     * @return boolean
     */
    public static boolean isValidPassword(String password, String hashedPassword, String salt) {
        return hashedPassword.equalsIgnoreCase(encodePassword(password, salt));
    }

    public static String encodePassword(String password, String salt) {
        String encodedPassword;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (salt != null) {
                digest.reset();
                digest.update(salt.getBytes());
            }

            byte[] hashed = digest.digest(password.getBytes());
            int iteration = Constant.HASH_ITERATIONS - 1;
            for (int i = 0; i < iteration; i++) {
                digest.reset();
                hashed = digest.digest(hashed);
            }
            encodedPassword = new String(Hex.encode(hashed));
        } catch (Exception e) {
            log.error("验证密码异常:", e);
            return null;
        }
        return encodedPassword;
    }

    public static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte[] messageDigest = digest.digest();
            //Create Hex String
            StringBuffer hexString = new StringBuffer();
            int length = messageDigest.length;
            for (int i = 0; i < length; i++) {
                if (i % 2 == 0) {
                    hexString.append(Integer.toHexString(messageDigest[i] & 0xff).toUpperCase());
                } else {
                    hexString.append(Integer.toHexString(messageDigest[i] & 0xff).toLowerCase());
                }
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("加密异常:", e);
        }
        return "";
    }
}
