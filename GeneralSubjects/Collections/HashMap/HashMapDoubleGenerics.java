package GeneralSubjects.Collections.HashMap;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
public class HashMapDoubleGenerics {
}

class MyTypeSafeMap<T> {
    public static class Key<K>{
        K k;
        Key(K k){
            this.k = k;
        }
        @Override
        public boolean equals(Object obj) {
            return k.equals(obj);
        }
        @Override
        public int hashCode() {
            return k.hashCode();
        }
    }
    public static class Value<V>{
        V v;
        Value(V v){
            this.v = v;
        }
    }
    private Map<Key<? extends T>, Value<? extends T>> map = new HashMap<>();
    public <S extends T> void put(Key<S> key, Value<S> value) {
        map.put(key, value);
    }
    public <S extends T> Value<S> get(Key<S> key) {
        return (Value) map.get(key);
    }
    public static void main(String[] args) {
        MyTypeSafeMap<Number> myTypeSafeMap = new MyTypeSafeMap<>();
        Key<Integer> key = new Key<>(1);
        myTypeSafeMap.put(key, new Value<Integer>(1));
        Value<Integer> v = myTypeSafeMap.get(key);
        System.out.println(v.v);
    }
}
