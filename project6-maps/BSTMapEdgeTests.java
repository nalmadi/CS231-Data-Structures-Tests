/*
file name:      BSTMapEdgeTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea BSTMapEdgeTests
*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BSTMapEdgeTests {

    public static void main(String[] args) {
        bstMapEdgeTests();
    }

    public static void bstMapEdgeTests() {

        // Reverse comparator should drive keySet order
        {
            BSTMap<String, Integer> map = new BSTMap<>(Comparator.reverseOrder());
            map.put("alpha", 1);
            map.put("beta", 2);
            map.put("charlie", 3);
            List<String> keys = map.keySet();
            assert keys.equals(Arrays.asList("charlie", "beta", "alpha")) : "keySet should follow comparator ordering";
        }

        // put should return previous value without inflating size
        {
            BSTMap<String, Integer> map = new BSTMap<>();
            assert map.put("id", 1) == null : "First insert should return null";
            assert map.put("id", 7) == 1 : "Replacing value should return old";
            assert map.size() == 1 : "Size should stay constant when replacing";
            assert map.get("id") == 7 : "Value should update to latest";
        }

        // remove should handle leaf, one-child, and two-child nodes
        {
            BSTMap<Integer, String> map = new BSTMap<>();
            map.put(10, "root");
            map.put(5, "left");
            map.put(15, "right");
            map.put(2, "leaf");
            map.put(7, "mid");
            map.put(12, "inner");
            map.put(20, "outer");
            assert map.remove(2).equals("leaf") : "Removing leaf should return value";
            assert !map.containsKey(2) : "Leaf should be gone";
            assert map.remove(15).equals("right") : "Removing node with children";
            assert map.containsKey(12) : "Successor should still exist";
            assert map.remove(10).equals("root") : "Removing root should return value";
            assert !map.containsKey(10) : "Root should be removed";
        }

        // maxDepth should reflect skewed insertion order
        {
            BSTMap<Integer, Integer> map = new BSTMap<>();
            int count = 8;
            for (int i = 0; i < count; i++) {
                map.put(i, i);
            }
            assert map.maxDepth() == count : "Skewed insertion should yield depth == size";
        }

        // values() should align with keySet() ordering
        {
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("a", 4);
            map.put("b", 2);
            map.put("c", 9);
            ArrayList<String> keys = map.keySet();
            ArrayList<Integer> values = map.values();
            assert keys.size() == values.size() : "Keys/values should have same length";
            for (int i = 0; i < keys.size(); i++) {
                assert map.get(keys.get(i)).equals(values.get(i)) : "Values should match key order";
            }
        }

        System.out.println("Finished BSTMapEdgeTests!");
    }
}
