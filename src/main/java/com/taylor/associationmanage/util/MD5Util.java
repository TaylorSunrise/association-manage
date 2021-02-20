package com.taylor.associationmanage.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.Random;

public class MD5Util {

    public static String hashed(String password, String salt){
        Md5Hash md5Hash = new Md5Hash(password, salt);
        return md5Hash.toString();
    }

    public static String getSalt(){
        String srt = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer salt = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            salt.append(srt.charAt(random.nextInt(62)));
        }
        return salt.toString();
    }
}
