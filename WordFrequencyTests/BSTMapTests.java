/*
file name:      BSTMapTests.java
Authors:        Samuel Apoya
last modified:  11/14/2025

How to run:     java -ea BSTMapTests
*/

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
            int value = map.get("apple");
            System.out.println(value + " == 5");

            // THEN
            assert value == 5 : "Error in BSTMap::put() or get()";
        }

        // CASE 3: overwrite value
        {
            // GIVEN
            BSTMap<String, Integer> map = new BSTMap<>();
            map.put("banana", 2);

            // WHEN
            map.put("banana", 10);
            int value = map.get("banana");
            System.out.println(value + " == 10");

            // THEN
            assert value == 10 : "Error in BSTMap::put() overwrite";
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
            System.out.println(contains + " == false");

            // THEN
            assert !contains : "Error in BSTMap::remove()";
        }

        System.out.println("*** Done testing BSTMap! ***\n");
    }

    public static void main(String[] args) {
        bstMapTests();
    }
}
