package com.zq.redis.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.serializers.DefaultSerializers;
import de.javakaffee.kryoserializers.*;

import java.lang.reflect.InvocationHandler;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: dengdl
 * @create: 2020-06-28 16:33
 **/
public enum KryoInstance {
    /**
     * 单例
     */
    INSTANCE;

    private static final Set<Class<?>> REGISTERS = new LinkedHashSet<Class<?>>();

    private static volatile Kryo serKryo;

    private static Kryo dserKryo;

    /**
     * 双检锁实现单例
     *
     * @return Kryo
     */
    public Kryo getSerKryo() {
        if (serKryo == null) {
            synchronized (KryoInstance.class) {
                if (serKryo == null) {
                    serKryo = createKryo();

                }
            }
        }
        return serKryo;
    }

    public Kryo getDserKryo() {
        if (dserKryo == null) {
            synchronized (KryoInstance.class) {
                if (dserKryo == null) {
                    dserKryo = createKryo();
                }
            }
        }
        return dserKryo;
    }


    private Kryo createKryo() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.register(Collections.singletonList("").getClass(),
                new DefaultSerializers.ArraysAsListSerializer());
        kryo.register(GregorianCalendar.class,
                new GregorianCalendarSerializer());
        kryo.register(InvocationHandler.class, new JdkProxySerializer());
        kryo.register(BigDecimal.class,
                new DefaultSerializers.BigDecimalSerializer());
        kryo.register(BigInteger.class,
                new DefaultSerializers.BigIntegerSerializer());
        kryo.register(Pattern.class, new RegexSerializer());
        kryo.register(BitSet.class, new DefaultSerializers.BitSetSerializer());
        kryo.register(URI.class, new URISerializer());
        kryo.register(UUID.class, new UUIDSerializer());
        UnmodifiableCollectionsSerializer.registerSerializers(kryo);
        SynchronizedCollectionsSerializer.registerSerializers(kryo);

        kryo.register(HashMap.class);
        kryo.register(ArrayList.class);
        kryo.register(LinkedList.class);
        kryo.register(HashSet.class);
        kryo.register(TreeSet.class);
        kryo.register(Hashtable.class);
        kryo.register(Date.class);
        kryo.register(Calendar.class);
        kryo.register(ConcurrentHashMap.class);
        kryo.register(SimpleDateFormat.class);
        kryo.register(GregorianCalendar.class);
        kryo.register(Vector.class);
        kryo.register(BitSet.class);
        kryo.register(StringBuffer.class);
        kryo.register(StringBuilder.class);
        kryo.register(Object.class);
        kryo.register(Object[].class);
        kryo.register(String[].class);
        kryo.register(byte[].class);
        kryo.register(char[].class);
        kryo.register(int[].class);
        kryo.register(float[].class);
        kryo.register(double[].class);

        for (Class<?> clazz : REGISTERS) {
            kryo.register(clazz);
        }
        return kryo;
    }


    public void registerClass(Class<?> clazz) {
        REGISTERS.add(clazz);
    }


}
