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
            int size = map.size();
            System.out.println(value + " == 5");
            System.out.println(size + " == 1");

            // THEN
            assert value == 5 : "Error in BSTMap::put() or get()";
            assert size == 1 : "Error in BSTMap::size() after single insertion";
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

        System.out.println("*** Done testing BSTMap! ***\n");
    }


    public static void main(String[] args) {
        bstMapTests();
    }
}
