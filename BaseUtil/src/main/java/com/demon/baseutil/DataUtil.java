package com.demon.baseutil;

import java.util.Random;

/**
 * @author DeMon
 * @date 2018/12/21
 * @email 757454343@qq.com
 * @description
 */
public class DataUtil {

    public static int getRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;

    }
}
