package com.yhkj.utils;

import java.util.Random;

/**
 * @Author: Loulq
 * @Date: 2019/3/5 0005 11:29
 */
public class RandomUtils {
    public static String randomByLen(int len) {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < len; i++) {
            result += random.nextInt(10);
        }
        return result;
    }
}