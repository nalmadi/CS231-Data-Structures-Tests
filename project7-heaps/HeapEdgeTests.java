/*
file name:      HeapEdgeTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea HeapEdgeTests
*/

import java.util.Comparator;

public class HeapEdgeTests {

    private static class Task {
        String name;
        int priority;
        Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        public int getPriority() { return priority; }
        public void setPriority(int priority) { this.priority = priority; }
        @Override
        public String toString() { return name + ":" + priority; }
    }

    public static void main(String[] args) {
        heapEdgeTests();
    }

    public static void heapEdgeTests() {

        // Min-heap should respect comparator ordering
        {
            Heap<Task> heap = new Heap<>(Comparator.comparingInt(Task::getPriority));
            heap.offer(new Task("low", 1));
            heap.offer(new Task("mid", 5));
            heap.offer(new Task("high", 10));
            assert heap.size() == 3 : "Offer should increment size";
            assert heap.peek().name.equals("low") : "Peek should show smallest priority";
            assert heap.poll().name.equals("low") : "Poll should remove smallest priority";
            assert heap.poll().name.equals("mid") : "Poll should follow ascending order";
            assert heap.poll().name.equals("high") : "Last element should be max";
            assert heap.size() == 0 : "Heap should be empty after removals";
        }

        // Max-heap flag should invert comparator behavior
        {
            Heap<Integer> heap = new Heap<>(Comparator.naturalOrder(), true);
            for (int value : new int[]{3, 7, 1, 5}) {
                heap.offer(value);
            }
            assert heap.peek() == 7 : "Max heap should expose largest element";
            assert heap.poll() == 7;
            assert heap.poll() == 5;
            assert heap.poll() == 3;
            assert heap.poll() == 1;
        }

        // updatePriority should bubble up after priority improvement
        {
            Heap<Task> heap = new Heap<>(Comparator.comparingInt(Task::getPriority));
            Task slow = new Task("slow", 9);
            Task medium = new Task("medium", 5);
            Task fast = new Task("fast", 1);
            heap.offer(slow);
            heap.offer(medium);
            heap.offer(fast);
            slow.setPriority(0); // becomes highest priority
            heap.updatePriority(slow);
            assert heap.peek() == slow : "updatePriority should move item toward root";
            assert heap.poll() == slow : "slow should now poll first";
            assert heap.poll() == fast : "Remaining order should be preserved";
        }

        // bubbleDown should restore heap after replacing root
        {
            Heap<Task> heap = new Heap<>(Comparator.comparingInt(Task::getPriority));
            heap.offer(new Task("a", 2));
            heap.offer(new Task("b", 3));
            heap.offer(new Task("c", 4));
            heap.offer(new Task("d", 5));
            heap.offer(new Task("e", 6));
            Task root = heap.poll();
            assert root.name.equals("a") : "Initial root should leave first";
            assert heap.peek().name.equals("b") : "Next smallest should bubble up";
        }

        // peek() and poll() should return null on empty heap
        {
            Heap<Integer> heap = new Heap<>(Comparator.naturalOrder());
            assert heap.peek() == null : "Peek on empty should be null";
            assert heap.poll() == null : "Poll on empty should be null";
        }

        System.out.println("Finished HeapEdgeTests!");
    }
}
