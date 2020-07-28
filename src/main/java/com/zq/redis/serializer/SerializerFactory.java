package com.zq.redis.serializer;

/**
 * @description:
 * @author: dengdl
 * @create: 2020-06-28 16:29
 **/
public class SerializerFactory {
    /**
     * 序列化
     */
    public static byte[] serialize(Object obj) {
        if (obj == null) {
            return null;
        }
        return KryoUtils.serializeObject(obj);
    }

    /**
     * 反序列化
     */
    public static Object deserialize(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return KryoUtils.deserializeObject(bArr);
    }

    public static <T> T deserialize(byte[] bArr, Class<T> returnType) {
        return returnType.cast(deserialize(bArr));
    }
}
