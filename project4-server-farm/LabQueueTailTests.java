/*
file name:      LabQueueTailTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea LabQueueTailTests
*/

public class LabQueueTailTests {

    public static void main(String[] args) {
        labQueueTailTests();
    }

    public static void labQueueTailTests() {

        // Ensures addLast updates tail reference
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            list.addLast(10);
            assert list.getLast() == 10 : "Tail should follow first element";
            list.addLast(20);
            assert list.getLast() == 20 : "Tail should move to newest node";
            assert list.size() == 2 : "addLast must grow list";
        }

        // Confirms removeLast shrinks tail safely
        {
            LinkedList<String> list = new LinkedList<String>();
            list.addLast("alpha");
            list.addLast("beta");
            String removed = list.removeLast();
            assert "beta".equals(removed) : "removeLast should pop newest";
            assert list.size() == 1 : "removeLast must shrink list";
            removed = list.removeLast();
            assert "alpha".equals(removed) : "removeLast should handle single";
            assert list.size() == 0 : "List should become empty";
            list.addLast("gamma");
            assert list.getLast().equals("gamma") : "Tail should reset after empty";
        }

        // Validates getLast mirrors final element
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            for (int i = 0; i < 5; i++) {
                list.addLast(i);
            }
            assert list.getLast() == 4 : "getLast should view final node";
            assert list.get(list.size() - 1) == list.getLast() : "Indices should match tail value";
        }

        // Uses tail helpers within indexed add/remove
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            list.add(0, 1);
            list.add(1, 2);
            list.add(list.size(), 3); // should delegate to addLast
            assert list.getLast() == 3 : "add at size should hit tail";
            int removed = list.remove(list.size() - 1); // should use removeLast
            assert removed == 3 : "remove at end should mirror tail";
            list.addLast(99);
            assert list.get(list.size() - 1) == 99 : "Direct tail insertion should succeed";
        }

        // Ensures remove() resets tail when empty
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            list.add(42);
            int removed = list.remove();
            assert removed == 42 : "remove should pop head";
            assert list.size() == 0 : "List should be empty after remove";
            list.add(84);
            assert list.getLast() == 84 : "Tail must follow new singleton";
        }

        // Verifies clear nulls out both endpoints
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            list.addLast(5);
            list.addLast(6);
            list.clear();
            assert list.isEmpty() : "clear should empty list";
            list.addLast(7);
            assert list.getLast() == 7 : "Tail should reinitialize after clear";
        }

        // Confirms get handles tail index properly
        {
            LinkedList<Integer> list = new LinkedList<Integer>();
            for (int i = 0; i < 8; i++) {
                list.addLast(i);
            }
            int tailIndex = list.size() - 1;
            assert list.get(tailIndex) == 7 : "get should handle tail index";
            list.addLast(9);
            assert list.get(list.size() - 1) == 9 : "get must keep tail accessible";
        }

        System.out.println("Finished LabQueueTailTests!");
    }
}
