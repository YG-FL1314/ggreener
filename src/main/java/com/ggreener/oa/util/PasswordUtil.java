package com.ggreener.oa.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by lifu on 2018/10/7.
 * <p>
 * XXX
 */
public class PasswordUtil {
    public static String Encrypt(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            String pwd = new BigInteger(1, md.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
