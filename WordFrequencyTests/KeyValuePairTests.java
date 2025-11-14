/*
file name:      KeyValuePairTests.java
Authors:        Samuel Apoya
last modified:  11/14/2025

How to run:     java -ea KeyValuePairTests
*/

public class KeyValuePairTests {

    public static void keyValuePairTests() {

        // CASE 1: testing KeyValuePair() constructor
        {
            // GIVEN
            KeyValuePair<String, Integer> kvp = new KeyValuePair<>("apple", 5);

            // WHEN
            System.out.println(kvp.getKey() + " == apple");
            System.out.println(kvp.getValue() + " == 5");

            // THEN
            assert kvp.getKey().equals("apple") : "Error in KeyValuePair::getKey()";
            assert kvp.getValue() == 5 : "Error in KeyValuePair::getValue()";
        }

        // CASE 2: testing setValue()
        {
            // GIVEN
            KeyValuePair<String, Integer> kvp = new KeyValuePair<>("banana", 2);

            // WHEN
            kvp.setValue(10);
            System.out.println(kvp.getValue() + " == 10");

            // THEN
            assert kvp.getValue() == 10 : "Error in KeyValuePair::setValue()";
        }

        // CASE 3: testing equals() same key/value
        {
            // GIVEN
            KeyValuePair<String, Integer> kvp1 = new KeyValuePair<>("cat", 1);
            KeyValuePair<String, Integer> kvp2 = new KeyValuePair<>("cat", 1);

            // WHEN
            boolean result = kvp1.equals(kvp2);
            System.out.println(result + " == true");

            // THEN
            assert result == true : "Error in KeyValuePair::equals()";
        }

        // CASE 4: testing equals() different key or value
        {
            // GIVEN
            KeyValuePair<String, Integer> kvp1 = new KeyValuePair<>("dog", 2);
            KeyValuePair<String, Integer> kvp2 = new KeyValuePair<>("dog", 3);
            KeyValuePair<String, Integer> kvp3 = new KeyValuePair<>("cat", 2);

            // WHEN
            boolean r1 = kvp1.equals(kvp2);
            boolean r2 = kvp1.equals(kvp3);
            System.out.println(r1 + " == false");
            System.out.println(r2 + " == false");

            // THEN
            assert !r1 : "Error in KeyValuePair::equals()";
            assert !r2 : "Error in KeyValuePair::equals()";
        }

        System.out.println("*** Done testing KeyValuePair! ***\n");
    }

    public static void main(String[] args) {
        keyValuePairTests();
    }
}
