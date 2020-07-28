package com.zq.redis.util;

import java.util.List;
import java.util.Map;

/**
 * @author zhangqing
 * @Package com.zq.redis.util
 * @date 2020/7/28 15:54
 */
public class EmptyUtils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Object[]) {
            return isEmpty((Object[]) obj);
        } else if (obj instanceof String) {
            return isEmpty((String) obj);
        } else if (obj instanceof String[]) {
            return isEmpty((String[]) obj);
        } else if (obj instanceof Map<?, ?>) {
            return isEmpty((Map<?, ?>) obj);
        } else if (obj instanceof List<?>) {
            return isEmpty((List<?>) obj);
        }
        return false;
    }

    public static boolean isEmpty(Object[] obj) {
        return obj == null || obj.length == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(String[] str) {
        return str == null || str.length == 0;
    }

    public static boolean isEmpty(Map<?, ?> map) {

        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(EmptyUtils.isEmpty(" "));

        System.out.println(EmptyUtils.isEmpty("  "));
    }
}
