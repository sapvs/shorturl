package com.sapvs.shorturl.util;

import org.apache.commons.lang3.RandomStringUtils;

public class Util {

    public static String createID(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }



}
