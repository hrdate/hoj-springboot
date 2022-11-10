package com.hrdate.oj.utils;

import java.util.UUID;


public class IdUtils {

    private IdUtils() {

    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String id() {
        return UUID.randomUUID().toString();
    }
}
