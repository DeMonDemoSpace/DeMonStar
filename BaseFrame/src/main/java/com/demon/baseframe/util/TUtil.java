package com.demon.baseframe.util;

import java.lang.reflect.ParameterizedType;

/**
 * @author DeMonnnnnn
 * @date 2019/10/8
 * @email 757454343@qq.com
 * @description
 */
public class TUtil {

    public static <T> T getT(Object o) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[0]).newInstance();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }
}
