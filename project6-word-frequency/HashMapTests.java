/*
file name:      HashMapTests.java
Authors:        Samuel Apoya
last modified:  11/14/2025

How to run:     java -ea HashMapTests
*/

import java.util.ArrayList;

public class HashMapTests {

    public static void hashMapTests() {

        // CASE 1: empty map size
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();

            // WHEN
            int size = map.size();
            System.out.println(size + " == 0");

            // THEN
            assert size == 0 : "Error in HashMap::size() for empty map";
        }

        // CASE 2: put & get
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();

            // WHEN
            map.put("apple", 5);
            Integer value = map.get("apple");
            System.out.println(value + " == 5");

            // THEN
            assert value == 5 : "Error in HashMap::put() or get()";
        }

        // CASE 3: overwrite value
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();
            map.put("banana", 2);

            // WHEN
            map.put("banana", 10);
            Integer value = map.get("banana");
            System.out.println(value + " == 10");

            // THEN
            assert value == 10 : "Error in HashMap::put() overwrite";
        }

        // CASE 4: containsKey
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();
            map.put("cat", 1);

            // WHEN
            boolean hasKey = map.containsKey("cat");
            boolean missingKey = map.containsKey("dog");
            System.out.println(hasKey + " == true");
            System.out.println(missingKey + " == false");

            // THEN
            assert hasKey : "Error in HashMap::containsKey()";
            assert !missingKey : "Error in HashMap::containsKey() for missing key";
        }

        // CASE 5: remove key
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();
            map.put("dog", 3);

            // WHEN
            map.remove("dog");
            boolean contains = map.containsKey("dog");
            int size = map.size();
            System.out.println(contains + " == false");
            System.out.println(size + " == 0");

            // THEN
            assert !contains : "Error in HashMap::remove()";
            assert size == 0 : "Error in HashMap::size() after remove() on single-element map";
        }

        // CASE 6: remove key from map with multiple entries
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();
            map.put("apple", 5);
            map.put("banana", 10);
            map.put("cherry", 15);

            // WHEN
            map.remove("banana");
            boolean containsBanana = map.containsKey("banana");
            boolean containsApple = map.containsKey("apple");
            boolean containsCherry = map.containsKey("cherry");
            int size = map.size();
            System.out.println(containsBanana + " == false");
            System.out.println(containsApple + " == true");
            System.out.println(containsCherry + " == true");
            System.out.println(size + " == 2");

            // THEN
            assert !containsBanana : "Error in HashMap::remove() — failed to remove 'banana' from multi-element map";
            assert containsApple : "Error in HashMap::remove() — 'apple' was incorrectly removed when removing 'banana'";
            assert containsCherry : "Error in HashMap::remove() — 'cherry' was incorrectly removed when removing 'banana'";
            assert size == 2 : "Error in HashMap::size() wrong size after removing one element from multi-element map";
        }

        // CASE 7: collision handling and maxDepth()
        {
            // GIVEN
            HashMap<Integer, String> map = new HashMap<>(2); // small capacity to force collisions

            // WHEN
            map.put(0, "zero");
            map.put(2, "two");   // same bucket as 0 (0,2,4,...)
            map.put(4, "four");

            boolean has0 = map.containsKey(0);
            boolean has2 = map.containsKey(2);
            boolean has4 = map.containsKey(4);
            int size = map.size();
            int depth = map.maxDepth();

            System.out.println("size = " + size + " == 3");
            System.out.println("depth = " + depth + " (should be >= 3)");

            // THEN
            assert has0 && has2 && has4 : "Error in HashMap::put()/containsKey() under collisions";
            assert size == 3 : "Error in HashMap::size() under collisions";
            assert depth >= 3 : "Error in HashMap::maxDepth() — chain length should reflect collisions";
        }

        // CASE 8: keySet(), values(), entrySet(), and clear()
        {
            // GIVEN
            HashMap<String, Integer> map = new HashMap<>();
            map.put("a", 1);
            map.put("b", 2);
            map.put("c", 3);

            // WHEN
            ArrayList<String> keys = map.keySet();
            ArrayList<Integer> values = map.values();
            ArrayList<MapSet.KeyValuePair<String, Integer>> entries = map.entrySet();

            boolean hasA = false, hasB = false, hasC = false;
            for (String k : keys) {
                if (k.equals("a")) hasA = true;
                if (k.equals("b")) hasB = true;
                if (k.equals("c")) hasC = true;
            }

            boolean has1 = false, has2 = false, has3 = false;
            for (Integer v : values) {
                if (v == 1) has1 = true;
                if (v == 2) has2 = true;
                if (v == 3) has3 = true;
            }

            int entryCount = entries.size();
            System.out.println("keys size = " + keys.size() + " == 3");
            System.out.println("values size = " + values.size() + " == 3");
            System.out.println("entries size = " + entryCount + " == 3");

            // THEN
            assert keys.size() == 3 : "Error in HashMap::keySet() size";
            assert values.size() == 3 : "Error in HashMap::values() size";
            assert entryCount == 3 : "Error in HashMap::entrySet() size";
            assert hasA && hasB && hasC : "Error in HashMap::keySet() contents";
            assert has1 && has2 && has3 : "Error in HashMap::values() contents";

            // clear and re-check
            map.clear();
            int sizeAfterClear = map.size();
            boolean containsA = map.containsKey("a");
            int depthAfterClear = map.maxDepth();

            System.out.println("sizeAfterClear = " + sizeAfterClear + " == 0");
            System.out.println("depthAfterClear = " + depthAfterClear + " == -1");

            assert sizeAfterClear == 0 : "Error in HashMap::clear() size not reset";
            assert !containsA : "Error in HashMap::clear() key still present";
            assert depthAfterClear == -1 : "Error in HashMap::maxDepth() after clear() should be -1 for empty map";
        }

        System.out.println("*** Done testing HashMap! ***\n");
    }

    public static void main(String[] args) {
        hashMapTests();
    }
}
