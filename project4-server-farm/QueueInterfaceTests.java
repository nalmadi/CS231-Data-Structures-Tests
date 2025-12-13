/*
file name:      QueueInterfaceTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea QueueInterfaceTests
*/

public class QueueInterfaceTests {

    public static void main(String[] args) {
        queueInterfaceTests();
    }

    public static void queueInterfaceTests() {

        // Validates offer then peek preserves front
        {
            LinkedList<String> queue = new LinkedList<String>();
            queue.offer("first");
            queue.offer("second");
            assert "first".equals(queue.peek()) : "peek should view earliest";
            assert queue.size() == 2 : "offer should increase size";
        }

        // Ensures poll removes elements in FIFO
        {
            LinkedList<Integer> queue = new LinkedList<Integer>();
            for (int i = 0; i < 4; i++) {
                queue.offer(i);
            }
            for (int expected = 0; expected < 4; expected++) {
                Integer value = queue.poll();
                assert value != null : "poll should return value";
                assert value == expected : "Queue must maintain FIFO order";
            }
            assert queue.poll() == null : "Empty poll should return null";
        }

        // Confirms peek handles empty queue
        {
            LinkedList<Double> queue = new LinkedList<Double>();
            assert queue.peek() == null : "Empty peek should return null";
            queue.offer(Math.PI);
            assert queue.peek() == Math.PI : "Non-empty peek should succeed";
            queue.poll();
            assert queue.peek() == null : "Peek should revert to null";
        }

        // Stress tests alternating offer/poll operations
        {
            LinkedList<Integer> queue = new LinkedList<Integer>();
            int rounds = 1000;
            for (int i = 0; i < rounds; i++) {
                queue.offer(i);
                Integer v = queue.poll();
                assert v == i : "Immediate poll should yield offered value";
                queue.offer(i);
            }
            for (int i = 0; i < rounds; i++) {
                Integer v = queue.poll();
                assert v == i : "Deferred poll should retain ordering";
            }
            assert queue.isEmpty() : "Queue should drain entirely";
        }

        // Checks offer works after repeated drains
        {
            LinkedList<Integer> queue = new LinkedList<Integer>();
            for (int cycle = 0; cycle < 5; cycle++) {
                for (int i = 0; i < 10; i++) {
                    queue.offer(cycle * 10 + i);
                }
                for (int i = 0; i < 10; i++) {
                    int expected = cycle * 10 + i;
                    assert queue.poll() == expected : "Cycle polling must stay ordered";
                }
                assert queue.peek() == null : "Queue should be empty between cycles";
            }
        }

        System.out.println("Finished QueueInterfaceTests!");
    }
}
