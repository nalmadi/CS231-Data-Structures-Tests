/*
file name:      HashMapLoadFactorTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea HashMapLoadFactorTests
*/

import java.util.ArrayList;
import java.util.Collections;

public class HashMapLoadFactorTests {

    private static class BadHashKey {
        private final String key;
        BadHashKey(String key) { this.key = key; }
        @Override public int hashCode() { return 42; }
        @Override public boolean equals(Object o) { return (o instanceof BadHashKey) && ((BadHashKey) o).key.equals(this.key); }
        @Override public String toString() { return key; }
    }

    public static void main(String[] args) {
        hashMapLoadFactorTests();
    }

    public static void hashMapLoadFactorTests() {

        // Inserting beyond load factor should grow capacity
        {
            HashMap<Integer, Integer> map = new HashMap<>(4, 0.75);
            int originalCapacity = map.capacity();
            for (int i = 0; i < 4; i++) {
                map.put(i, i);
            }
            assert map.capacity() > originalCapacity : "Capacity should increase after exceeding load";
            assert map.size() == 4 : "Size should track inserted items";
        }

        // Removing entries should shrink capacity when below quarter-full
        {
            HashMap<Integer, Integer> map = new HashMap<>(8, 0.75);
            for (int i = 0; i < 12; i++) {
                map.put(i, i);
            }
            int expanded = map.capacity();
            for (int i = 0; i < 10; i++) {
                map.remove(i);
            }
            assert map.capacity() < expanded : "Capacity should decrease after removals";
            assert map.size() == 2 : "Size should reflect remaining items";
        }

        // containsKey / get should work with heavy collisions
        {
            HashMap<BadHashKey, Integer> map = new HashMap<>(4, 0.75);
            for (int i = 0; i < 5; i++) {
                map.put(new BadHashKey("key" + i), i);
            }
            assert map.maxDepth() >= 5 : "Single bucket should capture all collisions";
            assert map.containsKey(new BadHashKey("key3")) : "containsKey should traverse chain";
            assert map.get(new BadHashKey("key3")) == 3 : "get should return expected value";
        }

        // entrySet/keySet/values should align even when order unspecified
        {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("c", 3);
            ArrayList<String> keys = map.keySet();
            ArrayList<Integer> values = map.values();
            ArrayList<HashMap.KeyValuePair<String, Integer>> entries = map.entrySet();
            assert keys.size() == values.size() && values.size() == entries.size() : "Collection sizes should match";
            Collections.sort(keys);
            Collections.sort(entries, (e1, e2) -> e1.getKey().compareTo(e2.getKey()));
            for (int i = 0; i < keys.size(); i++) {
                assert map.get(keys.get(i)).equals(map.get(entries.get(i).getKey())) : "Entries should align with map";
            }
        }

        System.out.println("Finished HashMapLoadFactorTests!");
    }
}
