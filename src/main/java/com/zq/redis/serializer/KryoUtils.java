package com.zq.redis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: dengdl
 * @create: 2020-06-28 16:31
 **/
public class KryoUtils {
    static ReentrantLock lockSer = new ReentrantLock();
    static ReentrantLock lockDser = new ReentrantLock();
    /**
     * 序列化
     */
    private static Kryo serKryo = KryoInstance.INSTANCE.getSerKryo();
    /**
     * 反序列化
     */
    private static Kryo dserKryo = KryoInstance.INSTANCE.getDserKryo();

    public static byte[] syncSerialize(final Object rpc) {
        lockSer.lock();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Output output = new Output(out);
            serKryo.writeObjectOrNull(output, rpc, rpc.getClass());
            output.close();
            return out.toByteArray();
        } finally {
            lockSer.unlock();
        }
    }

    public static byte[] syncSerializeObject(final Object rpc) {
        lockSer.lock();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Output output = new Output(out);
            serKryo.writeClassAndObject(output, rpc);
            output.close();
            return out.toByteArray();
        } finally {
            lockSer.unlock();
        }
    }

    public static <T> T syncDeserialize(final byte[] buf, final Class<T> type) {
        Input input = null;
        lockDser.lock();
        try {
            ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
            input = new Input(bIn);
            return dserKryo.readObject(input, type);
        } finally {
            if (input != null) {
                input.close();
            }
            lockDser.unlock();
        }
    }

    public static Object syncDeserializeObject(final byte[] buf) {
        Input input = null;
        lockDser.lock();
        try {
            ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
            input = new Input(bIn);
            return dserKryo.readClassAndObject(input);
        } finally {
            if (input != null) {
                input.close();
            }
            lockDser.unlock();
        }
    }

    /**
     * 类非线安全，所以需要加同步，也可以每次传一个新的类Kryo的实例，但需要消耗更多的性能
     *
     * @param rpc
     * @return
     */
    public static byte[] serialize(final Object rpc) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Output output = new Output(out);
        serKryo.writeObjectOrNull(output, rpc, rpc.getClass());
        output.close();
        return out.toByteArray();

    }

    /**
     * 序列化
     *
     * @param obj
     * @return
     * 下午6:04:28
     */
    public static byte[] serializeObject(final Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Output output = new Output(out);
        serKryo.writeClassAndObject(output, obj);
        output.close();
        return out.toByteArray();

    }

    /**
     * 反序列化
     *
     * @param buf  反序列化字节数组
     * @param type 返回类型
     * @return
     * @throws Exception
     */
    public static <T> T deserialize(final byte[] buf, final Class<T> type) {
        ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
        Input input = new Input(bIn);
        try {
            return dserKryo.readObject(input, type);
        } finally {
            input.close();
        }

    }

    /**
     * 反序列化对象
     *
     * @param buf
     * @return 下午6:03:28
     */
    public static Object deserializeObject(final byte[] buf) {
        ByteArrayInputStream bIn = new ByteArrayInputStream(buf);
        Input input = new Input(bIn);
        try {
            return dserKryo.readClassAndObject(input);
        } finally {
            input.close();
        }

    }

}
