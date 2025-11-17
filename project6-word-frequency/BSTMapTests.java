/*
file name:      BSTMapTests.java
Authors:        Samuel Apoya
last modified:  11/14/2025

How to run:     java -ea BSTMapTests
*/

import java.util.ArrayList;

public class BSTMapTests {

    public static void bstMapTests() {

        // CASE 1: empty map size
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();

            // WHEN
            int size = map.size();
            System.out.println(size + " == 0");

            // THEN
            assert size == 0 : "Error in BSTMap::size() for empty map";
        }

        // CASE 2: put & get
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();

            // WHEN
            map.put("apple", 5);
            Integer value = map.get("apple");
            int size = map.size();
            System.out.println(value + " == 5");
            System.out.println(size + " == 1");

            // THEN
            assert value == 5 : "Error in BSTMap::put() or get()";
            assert size == 1 : "Error in BSTMap::size() after single insertion";
        }

        // CASE 3: overwrite a value
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("banana", 2);

            // WHEN
            map.put("banana", 10);
            Integer value = map.get("banana");
            System.out.println(value + " == 10");

            // THEN
            assert value == 10 : "Error in BSTMap::put() old value not overwritten";
        }

        // CASE 4: containsKey
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("cat", 1);

            // WHEN
            boolean hasKey = map.containsKey("cat");
            boolean missingKey = map.containsKey("dog");
            System.out.println(hasKey + " == true");
            System.out.println(missingKey + " == false");

            // THEN
            assert hasKey : "Error in BSTMap::containsKey()";
            assert !missingKey : "Error in BSTMap::containsKey() for missing key";
        }

        // CASE 5: remove key
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("dog", 3);

            // WHEN
            map.remove("dog");
            boolean contains = map.containsKey("dog");
            int size = map.size();
            System.out.println(contains + " == false");
            System.out.println(size + " == 0");

            // THEN
            assert !contains : "Error in BSTMap::remove()";
            assert size == 0 : "Error in BSTMap::size() after remove() on single-element tree";
        }

        // CASE 6: remove node in left subtree (not root)
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("c", 3);  // root
            map.put("b", 2);  // left child of c
            map.put("a", 1);  // left child of b

            // WHEN
            map.remove("b");

            boolean removed = !map.containsKey("b");
            boolean aPresent = map.containsKey("a");
            boolean cPresent = map.containsKey("c");

            // THEN
            assert removed    : "Error in BSTMap::remove() — failed to remove left child";
            assert aPresent   : "Error in BSTMap::remove() — left subtree lost after removing its parent";
            assert cPresent   : "Error in BSTMap::remove() — root lost after removing its left child";
        }

        // CASE 7: remove node in right subtree (not root)
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("c", 3);  // root
            map.put("d", 4);  // right child of c
            map.put("e", 5);  // right child of d

            // WHEN
            map.remove("d");

            boolean removed = !map.containsKey("d");
            boolean ePresent = map.containsKey("e");
            boolean cPresent = map.containsKey("c");

            // THEN
            assert removed  : "Error in BSTMap::remove() — failed to remove right child";
            assert ePresent : "Error in BSTMap::remove() — right subtree lost after removing its parent";
            assert cPresent : "Error in BSTMap::remove() — root lost after removing its right child";
        }

        // CASE 8: update and remove internal node with two children
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("b", 2);  // root
            map.put("a", 1);  // left child of b
            map.put("d", 4);  // right child of b
            map.put("c", 3);  // left child of d
            map.put("e", 5);  // right child of d

            // WHEN
            map.put("d", 40);  // update internal node
            int updated = map.get("d");

            map.remove("d");   // remove internal node

            boolean removed = !map.containsKey("d");
            boolean childrenPresent = map.containsKey("c") && map.containsKey("e");
            boolean othersPresent = map.containsKey("b") && map.containsKey("a");

            // THEN
            assert updated == 40 : "Error in BSTMap::put() — failed to update value of internal node 'd'";
            assert removed : "Error in BSTMap::remove() — failed to remove internal node 'd' with two children";
            assert childrenPresent : "Error in BSTMap::remove() — children 'c' and 'e' of removed node 'd' were lost";
            assert othersPresent : "Error in BSTMap::remove() — unrelated nodes 'a' or 'b' were affected by removal of 'd'";
        }

        // CASE 9: get() on missing key returns null
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("apple", 1);

            // WHEN
            Integer v1 = map.get("apple");
            Integer v2 = map.get("banana");
            System.out.println(v1 + " == 1");
            System.out.println(v2 + " == null");

            // THEN
            assert v1 == 1 : "Error in BSTMap::get() existing key";
            assert v2 == null : "Error in BSTMap::get() missing key should return null";
        }

        // CASE 10: keySet(), values(), and entrySet() in sorted order
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("b", 2);
            map.put("a", 1);
            map.put("c", 3);

            // WHEN
            ArrayList<String> keys = map.keySet();
            ArrayList<Integer> values = map.values();
            ArrayList<MapSet.KeyValuePair<String, Integer>> entries = map.entrySet();

            System.out.println("keys: " + keys);
            System.out.println("values: " + values);
            System.out.println("entries size: " + entries.size() + " == 3");

            // THEN
            assert keys.size() == 3 : "Error in BSTMap::keySet() size";
            assert keys.get(0).equals("a") && keys.get(1).equals("b") && keys.get(2).equals("c")
                : "Error in BSTMap::keySet() order";

            assert values.size() == 3 : "Error in BSTMap::values() size";
            assert values.get(0) == 1 && values.get(1) == 2 && values.get(2) == 3
                : "Error in BSTMap::values() order";

            assert entries.size() == 3 : "Error in BSTMap::entrySet() size";
            assert entries.get(0).getKey().equals("a") && entries.get(0).getValue() == 1
                : "Error in BSTMap::entrySet() first entry";
            assert entries.get(1).getKey().equals("b") && entries.get(1).getValue() == 2
                : "Error in BSTMap::entrySet() second entry";
            assert entries.get(2).getKey().equals("c") && entries.get(2).getValue() == 3
                : "Error in BSTMap::entrySet() third entry";
        }

        // CASE 11: clear() resets tree
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("b", 2);
            map.put("a", 1);
            map.put("c", 3);

            // WHEN
            map.clear();
            int size = map.size();
            int depth = map.maxDepth();
            boolean hasB = map.containsKey("b");
            System.out.println(size + " == 0");
            System.out.println(depth + " == 0");

            // THEN
            assert size == 0 : "Error in BSTMap::clear() size not reset";
            assert depth == 0 : "Error in BSTMap::maxDepth() after clear() should be 0";
            assert !hasB : "Error in BSTMap::clear() keys not removed";
        }

        // CASE 12: maxDepth() on skewed vs balanced trees
        {
            // GIVEN
            BSTMap<Integer, Integer> skewed = new BSTMap<>();
            skewed.put(1, 1);
            skewed.put(2, 2);
            skewed.put(3, 3);
            skewed.put(4, 4);

            BSTMap<Integer, Integer> balanced = new BSTMap<>();
            balanced.put(2, 2);
            balanced.put(1, 1);
            balanced.put(3, 3);

            // WHEN
            int skewedDepth = skewed.maxDepth();
            int balancedDepth = balanced.maxDepth();
            System.out.println("skewedDepth = " + skewedDepth);
            System.out.println("balancedDepth = " + balancedDepth);

            // THEN
            assert skewedDepth > balancedDepth : "Error in BSTMap::maxDepth() skewed tree should be deeper than balanced";
        }

        System.out.println("*** Done testing BSTMap! ***\n");
    }

    public static void main(String[] args) {
        bstMapTests();
    }
}
