package top.cusie.core.utill;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cusie
 * @date 2024/10/30
 */
public class MapUtils {

    public static <K, V> Map<K, V> create(K k, V v, Object... kvs) {
        Map<K, V> map = new HashMap<>(kvs.length + 1);
        map.put(k, v);
        for (int i = 0; i < kvs.length; i += 2) {
            map.put((K) kvs[i], (V) kvs[i + 1]);
        }
        return map;
    }

}