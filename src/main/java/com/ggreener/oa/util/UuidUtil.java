package com.ggreener.oa.util;

import java.util.UUID;

/**
 * Created by lifu on 2018/10/7.
 * <p>
 * XXX
 */
public class UuidUtil {
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return uuid;
    }
}
