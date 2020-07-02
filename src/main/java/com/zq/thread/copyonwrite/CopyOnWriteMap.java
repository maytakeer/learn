package com.zq.thread.copyonwrite;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zhangqing
 * @Package com.zq.thread.copyonwrite
 * @date 2020/6/30 14:52
 */
public class CopyOnWriteMap<K, V> implements Map<K, V>, Cloneable {

    private volatile Map<K, V> internalMap;

    public CopyOnWriteMap(){
        internalMap = new HashMap<K, V>();
    }

    public CopyOnWriteMap(int size){
        internalMap = new HashMap<K, V>(size);
    }

    @Override
    public V put(K key, V value) {
        synchronized (this){
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }
    }

    @Override
    public V get(Object key) {
        return internalMap.get(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> newData) {
        synchronized (this){
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            newMap.putAll(newData);
            internalMap = newMap;
        }
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }


    @Override
    public V remove(Object key) {
        return null;
    }


    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}
