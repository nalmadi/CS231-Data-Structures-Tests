/*
file name:      ServerBehaviorTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea ServerBehaviorTests
*/

public class ServerBehaviorTests {

    private static final double EPS = 1e-6;

    public static void main(String[] args) {
        serverBehaviorTests();
    }

    public static void serverBehaviorTests() {

        // Confirms addJob tracks size and work
        {
            Server server = new Server();
            Job job = new Job(0.0, 5.0);
            server.processTo(job.getArrivalTime());
            server.addJob(job);
            assert server.size() == 1 : "Server should report one job";
            assert Math.abs(server.remainingWorkInQueue() - 5.0) < EPS : "Remaining work should match processing time";
        }

        // Verifies processTo consumes partial workload
        {
            Server server = new Server();
            Job job = new Job(0.0, 4.0);
            server.processTo(job.getArrivalTime());
            server.addJob(job);
            server.processTo(1.5);
            double expectedRemaining = 2.5;
            assert Math.abs(server.remainingWorkInQueue() - expectedRemaining) < EPS : "Processing should reduce remaining work";
            assert server.size() == 1 : "Job should remain until finished";
        }

        // Ensures sequential jobs accumulate waiting statistics
        {
            Server server = new Server();
            Job first = new Job(0.0, 3.0);
            Job second = new Job(1.0, 2.0);
            server.processTo(first.getArrivalTime());
            server.addJob(first);
            server.processTo(second.getArrivalTime());
            server.addJob(second);
            server.processTo(6.0);
            assert server.remainingWorkInQueue() == 0.0 : "All work should be processed";
            double totalWaiting = server.getTotalWaitingTime();
            assert totalWaiting > 0.0 : "Waiting time should accumulate";
            assert totalWaiting < 8.0 : "Waiting time should remain bounded";
        }

        // Checks completion removes job from queue
        {
            Server server = new Server();
            Job job = new Job(0.0, 1.0);
            server.processTo(job.getArrivalTime());
            server.addJob(job);
            server.processTo(2.0);
            assert server.size() == 0 : "Finished jobs should exit queue";
            assert server.remainingWorkInQueue() == 0.0 : "Remaining work should be zero";
        }

        // Validates waiting totals after staggered arrivals
        {
            Server server = new Server();
            Job j1 = new Job(0.0, 2.5);
            Job j2 = new Job(0.5, 1.0);
            Job j3 = new Job(3.0, 4.0);
            server.processTo(j1.getArrivalTime());
            server.addJob(j1);
            server.processTo(j2.getArrivalTime());
            server.addJob(j2);
            server.processTo(j3.getArrivalTime());
            server.addJob(j3);
            server.processTo(10.0);
            assert server.remainingWorkInQueue() == 0.0 : "All jobs should complete";
            assert server.getTotalWaitingTime() >= j1.getProcessingTimeNeeded() + j2.getProcessingTimeNeeded() + j3.getProcessingTimeNeeded() : "Waiting time should include processing";
        }

        System.out.println("Finished ServerBehaviorTests!");
    }
}
