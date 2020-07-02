package com.zq.thread.copyonwrite;

import java.util.Map;

/** CopyOnWrite容器有数据一致性的问题，它只能保证最终数据一致性。
 *  所以如果我们希望写入的数据马上能准确地读取，请不要使用CopyOnWrite容器。
 * @author zhangqing
 * @Package com.zq.thread.copyonwrite
 * @date 2020/6/30 15:04
 */
public class BlackListServiceImpl {

    private static CopyOnWriteMap<String, Boolean> blackListMap = new CopyOnWriteMap<>(1000);


    public static boolean isBlackList(String id){
        return blackListMap.get(id) == null ? false : true;
    }

    public static void addBlackList(String id){
        blackListMap.put(id, Boolean.TRUE);
    }

    /**
     * 批量添加黑名单
     * (使用批量添加。因为每次添加，容器每次都会进行复制，所以减少添加次数，可以减少容器的复制次数。
     * 如使用上面代码里的addBlackList方法)
     * @param ids
     */
    public static void addBlackList(Map<String,Boolean> ids) {
        blackListMap.putAll(ids);
    }

}
