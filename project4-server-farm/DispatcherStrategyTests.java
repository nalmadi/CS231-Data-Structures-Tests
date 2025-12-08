/*
file name:      DispatcherStrategyTests.java
Authors:        Rishit Chatterjee
last modified:  11/14/2025

How to run:     java -ea DispatcherStrategyTests
*/

import java.util.ArrayList;

public class DispatcherStrategyTests {

    public static void main(String[] args) {
        dispatcherStrategyTests();
    }

    public static void dispatcherStrategyTests() {

        // Validates single server dispatcher waiting averages
        {
            JobDispatcher dispatcher = new RandomDispatcher(1, false);
            dispatcher.handleJob(new Job(0.0, 2.0));
            dispatcher.handleJob(new Job(1.0, 1.0));
            dispatcher.handleJob(new Job(2.0, 3.0));
            dispatcher.finishUp();
            assert dispatcher.getNumJobsHandled() == 3 : "Should account for every job";
            assert dispatcher.getAverageWaitingTime() >= 3.0 : "Average wait should include processing";
        }

        // Checks round robin cycles predictably
        {
            JobDispatcher dispatcher = new RoundRobinDispatcher(3, false);
            dispatcher.handleJob(new Job(0.0, 1.0));
            dispatcher.handleJob(new Job(0.0, 1.0));
            dispatcher.handleJob(new Job(0.0, 1.0));
            dispatcher.handleJob(new Job(0.0, 1.0));
            ArrayList<Server> servers = dispatcher.getServerList();
            assert servers.get(0).size() == 2 : "Server zero should have two jobs";
            assert servers.get(1).size() == 1 : "Server one should have one job";
            assert servers.get(2).size() == 1 : "Server two should have one job";
        }

        // Ensures shortest queue favors lighter queues
        {
            JobDispatcher dispatcher = new ShortestQueueDispatcher(2, false);
            dispatcher.handleJob(new Job(0.0, 4.0)); // goes to server0
            dispatcher.handleJob(new Job(0.0, 2.0)); // should go to server1
            dispatcher.handleJob(new Job(0.5, 1.0)); // queues equal, tie arbitrary
            dispatcher.handleJob(new Job(1.0, 5.0)); // should go to server with fewer jobs
            ArrayList<Server> servers = dispatcher.getServerList();
            int difference = Math.abs(servers.get(0).size() - servers.get(1).size());
            assert difference <= 1 : "Shortest queue should balance job counts";
        }

        // Confirms least work honors remaining processing
        {
            JobDispatcher dispatcher = new LeastWorkDispatcher(2, false);
            dispatcher.handleJob(new Job(0.0, 8.0)); // likely server0
            dispatcher.handleJob(new Job(0.0, 1.0)); // should prefer other server
            dispatcher.handleJob(new Job(0.0, 2.0)); // should still prefer lighter work
            ArrayList<Server> servers = dispatcher.getServerList();
            double work0 = servers.get(0).remainingWorkInQueue();
            double work1 = servers.get(1).remainingWorkInQueue();
            assert work0 != work1 : "Workloads should diverge";
            dispatcher.handleJob(new Job(5.0, 2.0)); // after processing, should go to smaller work
            dispatcher.finishUp();
            assert dispatcher.getAverageWaitingTime() >= 0.0 : "Average waiting time should be computable";
        }

        // Verifies finishUp drains every queue
        {
            JobDispatcher dispatcher = new ShortestQueueDispatcher(3, false);
            for (int i = 0; i < 6; i++) {
                dispatcher.handleJob(new Job(i * 0.5, 1.0));
            }
            dispatcher.finishUp();
            for (Server server : dispatcher.getServerList()) {
                assert server.size() == 0 : "All queues should be empty after finishUp";
                assert server.remainingWorkInQueue() == 0.0 : "No remaining work should remain";
            }
        }

        System.out.println("Finished DispatcherStrategyTests!");
    }
}
