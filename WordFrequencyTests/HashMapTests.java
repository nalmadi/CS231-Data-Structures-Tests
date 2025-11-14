/*
file name:      HashMapTests.java
Authors:        Samuel Apoya
last modified:  11/14/2025

How to run:     java -ea HashMapTests
*/

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
            int value = map.get("apple");
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
            int value = map.get("banana");
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
            System.out.println(contains + " == false");

            // THEN
            assert !contains : "Error in HashMap::remove()";
        }

        System.out.println("*** Done testing HashMap! ***\n");
    }

    public static void main(String[] args) {
        hashMapTests();
    }
}
