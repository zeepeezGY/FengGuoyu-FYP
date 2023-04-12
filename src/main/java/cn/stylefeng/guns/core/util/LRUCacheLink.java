package cn.stylefeng.guns.core.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheLink {
    private Map<Integer, Integer> cacheMap = null;

    public LRUCacheLink(int capacity) {
        // 参数设置true，当removeEldestEntry()返回true，则删除最旧的数据
        cacheMap = new LinkedHashMap<Integer, Integer>(capacity,0.75F,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > capacity;
            }
        };
    }

    public int get(int key) {
        return cacheMap.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        cacheMap.put(key, value);
    }

}
