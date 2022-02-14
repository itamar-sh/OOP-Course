package GeneralSubjects.Collections.HashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class BasicOperation {
    public static void main(String[] args) {
        //BasicMap("a", 1);
        TreeMap("a", "b", 2, 1, "c", 1);
    }
    public static <K,V> void BasicMap(K k, V v){
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        map.get(k);
        System.out.println(map.containsKey(k));
        System.out.println(map.isEmpty());
        System.out.println(map.entrySet());
        System.out.println(map.keySet());
        System.out.println(map.size());
        System.out.println(map.values());
        System.out.println(map.remove(k,v));
        System.out.println(map.containsValue(v));
        System.out.println(map.replace(k,v,v));
    }
    public static <K,V> void HashMap(K k, V v){
        HashMap<K, V> map = new HashMap<>();
        map.put(k, v);
        map.get(k);
        System.out.println(map.containsKey(k));
        System.out.println(map.isEmpty());
        System.out.println(map.entrySet());
        System.out.println(map.keySet());
        System.out.println(map.size());
        System.out.println(map.values());
        System.out.println(map.remove(k,v));
        System.out.println(map.containsValue(v));
        System.out.println(map.replace(k,v,v));
        map.putAll(new HashMap<K, V>() { {put(k, v);put(k,v);}});
        System.out.println(map.isEmpty());
    }
    public static <K extends Comparable<K>,V extends Comparable<V>> void TreeMap(K k, K k2, V v, V v2, K k3, V v3){
        class KeyWrapper<K extends Comparable<K>,V extends Comparable<V>> implements Comparable<KeyWrapper<K, V>>{
            K k;
            V v;
            public KeyWrapper(K k, V v){
                this.k = k;
                this.v = v;
            }

            @Override
            public int compareTo(KeyWrapper<K, V> o) {
                int value = v.compareTo(o.v);
                if(k.compareTo(o.k) != 0 && value == 0){ // deal with the case of same value but not same key
                    return 1;
                }
                return value;
            }
            @Override
            public String toString(){
                return k.toString();
            }
        }
        TreeMap<KeyWrapper<K,V>, V> map = new TreeMap<>();
        map.put(new KeyWrapper<>(k,v), v);
        map.put(new KeyWrapper<>(k2,v2), v2);
        System.out.println(map);
        map.forEach((a,b)->{System.out.println(a.toString() + b);});
        map.put(new KeyWrapper<>(k3,v3), v3);
        System.out.println(map);
    }
    //
}
