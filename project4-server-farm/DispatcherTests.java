/*
file name:      DispatcherTests.java
Authors:        Najam Tariq
last modified:  12/13/2025

How to run:     java -ea DispatcherTests
*/

import java.util.ArrayList;

public class DispatcherTests {

    // ========== RandomDispatcher Tests ==========
    public static void randomDispatcherTests() {
        System.out.println("========== RandomDispatcher Tests ==========\n");

        // case 1: testing RandomDispatcher constructor
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(3, false);

            // when
            System.out.println("=== case 1: RandomDispatcher constructor ===");
            System.out.println("Dispatcher created with 3 servers");
            System.out.println("Server list size: " + dispatcher.getServerList().size() + " == 3");
            System.out.println("Initial time: " + dispatcher.getTime() + " == 0.0");

            // then
            assert dispatcher != null : "Error in RandomDispatcher constructor";
            assert dispatcher.getServerList().size() == 3 : "Error: should have 3 servers";
            assert dispatcher.getTime() == 0.0 : "Error: initial time should be 0";
        }

        // case 2: testing pickServer returns a valid server
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(5, false);
            Job job = new Job(0.0, 10.0);

            // when
            Server picked = dispatcher.pickServer(job);
            ArrayList<Server> servers = dispatcher.getServerList();

            System.out.println("\n=== case 2: pickServer returns valid server ===");
            System.out.println("Picked server is not null: " + (picked != null));
            System.out.println("Picked server is in server list: " + servers.contains(picked));

            // then
            assert picked != null : "Error in RandomDispatcher::pickServer() - returned null";
            assert servers.contains(picked) : "Error in RandomDispatcher::pickServer() - server not in list";
        }

        // case 3: testing randomness - pickServer should pick different servers over many calls
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(5, false);
            int[] pickCounts = new int[5];

            // when - pick 100 times
            for (int i = 0; i < 100; i++) {
                Job job = new Job(i, 1.0);
                Server picked = dispatcher.pickServer(job);
                int idx = dispatcher.getServerList().indexOf(picked);
                pickCounts[idx]++;
            }

            System.out.println("\n=== case 3: randomness distribution ===");
            int serversUsed = 0;
            for (int i = 0; i < 5; i++) {
                System.out.println("Server " + i + " picked: " + pickCounts[i] + " times");
                if (pickCounts[i] > 0) serversUsed++;
            }

            // then - at least 3 different servers should have been picked
            assert serversUsed >= 3 : "Error: RandomDispatcher should use multiple servers, only used " + serversUsed;
        }

        System.out.println("\n*** Done testing RandomDispatcher! ***\n");
    }

    // ========== RoundRobinDispatcher Tests ==========
    public static void roundRobinDispatcherTests() {
        System.out.println("========== RoundRobinDispatcher Tests ==========\n");

        // case 1: testing RoundRobinDispatcher constructor
        {
            // given
            RoundRobinDispatcher dispatcher = new RoundRobinDispatcher(4, false);

            // when
            System.out.println("=== case 1: RoundRobinDispatcher constructor ===");
            System.out.println("Server list size: " + dispatcher.getServerList().size() + " == 4");

            // then
            assert dispatcher.getServerList().size() == 4 : "Error: should have 4 servers";
        }

        // case 2: testing round-robin behavior
        {
            // given
            RoundRobinDispatcher dispatcher = new RoundRobinDispatcher(3, false);
            ArrayList<Server> servers = dispatcher.getServerList();

            // when - pick 6 times (should cycle through twice)
            Server pick1 = dispatcher.pickServer(new Job(0, 1));
            Server pick2 = dispatcher.pickServer(new Job(1, 1));
            Server pick3 = dispatcher.pickServer(new Job(2, 1));
            Server pick4 = dispatcher.pickServer(new Job(3, 1));
            Server pick5 = dispatcher.pickServer(new Job(4, 1));
            Server pick6 = dispatcher.pickServer(new Job(5, 1));

            System.out.println("\n=== case 2: round-robin order ===");
            System.out.println("Pick 1 == Server 0: " + (pick1 == servers.get(0)));
            System.out.println("Pick 2 == Server 1: " + (pick2 == servers.get(1)));
            System.out.println("Pick 3 == Server 2: " + (pick3 == servers.get(2)));
            System.out.println("Pick 4 == Server 0: " + (pick4 == servers.get(0)));
            System.out.println("Pick 5 == Server 1: " + (pick5 == servers.get(1)));
            System.out.println("Pick 6 == Server 2: " + (pick6 == servers.get(2)));

            // then
            assert pick1 == servers.get(0) : "Error: first pick should be server 0";
            assert pick2 == servers.get(1) : "Error: second pick should be server 1";
            assert pick3 == servers.get(2) : "Error: third pick should be server 2";
            assert pick4 == servers.get(0) : "Error: fourth pick should cycle back to server 0";
            assert pick5 == servers.get(1) : "Error: fifth pick should be server 1";
            assert pick6 == servers.get(2) : "Error: sixth pick should be server 2";
        }

        System.out.println("\n*** Done testing RoundRobinDispatcher! ***\n");
    }

    // ========== ShortestQueueDispatcher Tests ==========
    public static void shortestQueueDispatcherTests() {
        System.out.println("========== ShortestQueueDispatcher Tests ==========\n");

        // case 1: testing ShortestQueueDispatcher constructor
        {
            // given
            ShortestQueueDispatcher dispatcher = new ShortestQueueDispatcher(3, false);

            // when
            System.out.println("=== case 1: ShortestQueueDispatcher constructor ===");
            System.out.println("Server list size: " + dispatcher.getServerList().size() + " == 3");

            // then
            assert dispatcher.getServerList().size() == 3 : "Error: should have 3 servers";
        }

        // case 2: testing pickServer picks server with shortest queue
        {
            // given
            ShortestQueueDispatcher dispatcher = new ShortestQueueDispatcher(3, false);
            ArrayList<Server> servers = dispatcher.getServerList();

            // Manually add jobs to servers to create different queue sizes
            servers.get(0).addJob(new Job(0, 5));
            servers.get(0).addJob(new Job(0, 5));  // Server 0 has 2 jobs
            servers.get(1).addJob(new Job(0, 5));  // Server 1 has 1 job
            // Server 2 has 0 jobs (shortest)

            // when
            Server picked = dispatcher.pickServer(new Job(0, 10));

            System.out.println("\n=== case 2: picks shortest queue ===");
            System.out.println("Server 0 size: " + servers.get(0).size());
            System.out.println("Server 1 size: " + servers.get(1).size());
            System.out.println("Server 2 size: " + servers.get(2).size());
            System.out.println("Picked server is Server 2 (shortest): " + (picked == servers.get(2)));

            // then
            assert picked == servers.get(2) : "Error: should pick server with shortest queue (Server 2)";
        }

        // case 3: testing with equal queue sizes (ties handled arbitrarily)
        {
            // given
            ShortestQueueDispatcher dispatcher = new ShortestQueueDispatcher(3, false);
            ArrayList<Server> servers = dispatcher.getServerList();
            // All servers have 0 jobs initially

            // when
            Server picked = dispatcher.pickServer(new Job(0, 10));

            System.out.println("\n=== case 3: equal queue sizes ===");
            System.out.println("All servers have 0 jobs");
            System.out.println("Picked a valid server: " + servers.contains(picked));

            // then - should pick any server (ties are arbitrary)
            assert servers.contains(picked) : "Error: should pick a valid server";
        }

        System.out.println("\n*** Done testing ShortestQueueDispatcher! ***\n");
    }

    // ========== LeastWorkDispatcher Tests ==========
    public static void leastWorkDispatcherTests() {
        System.out.println("========== LeastWorkDispatcher Tests ==========\n");

        // case 1: testing LeastWorkDispatcher constructor
        {
            // given
            LeastWorkDispatcher dispatcher = new LeastWorkDispatcher(3, false);

            // when
            System.out.println("=== case 1: LeastWorkDispatcher constructor ===");
            System.out.println("Server list size: " + dispatcher.getServerList().size() + " == 3");

            // then
            assert dispatcher.getServerList().size() == 3 : "Error: should have 3 servers";
        }

        // case 2: testing pickServer picks server with least remaining work
        {
            // given
            LeastWorkDispatcher dispatcher = new LeastWorkDispatcher(3, false);
            ArrayList<Server> servers = dispatcher.getServerList();

            // Add jobs with different processing times
            servers.get(0).addJob(new Job(0, 100));  // Server 0 has 100 units of work
            servers.get(1).addJob(new Job(0, 50));   // Server 1 has 50 units of work
            servers.get(2).addJob(new Job(0, 10));   // Server 2 has 10 units (least work)

            // when
            Server picked = dispatcher.pickServer(new Job(0, 20));

            System.out.println("\n=== case 2: picks server with least work ===");
            System.out.println("Server 0 work: " + servers.get(0).remainingWorkInQueue());
            System.out.println("Server 1 work: " + servers.get(1).remainingWorkInQueue());
            System.out.println("Server 2 work: " + servers.get(2).remainingWorkInQueue());
            System.out.println("Picked server is Server 2 (least work): " + (picked == servers.get(2)));

            // then
            assert picked == servers.get(2) : "Error: should pick server with least remaining work (Server 2)";
        }

        // case 3: testing least work considers total processing time, not just job count
        {
            // given
            LeastWorkDispatcher dispatcher = new LeastWorkDispatcher(2, false);
            ArrayList<Server> servers = dispatcher.getServerList();

            // Server 0: 1 job with 100 units
            servers.get(0).addJob(new Job(0, 100));
            // Server 1: 5 jobs with 10 units each = 50 units total
            for (int i = 0; i < 5; i++) {
                servers.get(1).addJob(new Job(0, 10));
            }

            // when
            Server picked = dispatcher.pickServer(new Job(0, 20));

            System.out.println("\n=== case 3: least work vs job count ===");
            System.out.println("Server 0: 1 job, " + servers.get(0).remainingWorkInQueue() + " units");
            System.out.println("Server 1: 5 jobs, " + servers.get(1).remainingWorkInQueue() + " units");
            System.out.println("Picked Server 1 (less total work): " + (picked == servers.get(1)));

            // then - Server 1 has more jobs but less total work
            assert picked == servers.get(1) : "Error: should pick server with least total work, not fewest jobs";
        }

        System.out.println("\n*** Done testing LeastWorkDispatcher! ***\n");
    }

    // ========== JobDispatcher Common Methods Tests ==========
    public static void jobDispatcherCommonTests() {
        System.out.println("========== JobDispatcher Common Methods Tests ==========\n");

        // Using RandomDispatcher to test common inherited methods
        // (could use any dispatcher since these methods are inherited)

        // case 1: testing getTime()
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(2, false);

            // when
            double initialTime = dispatcher.getTime();

            System.out.println("=== case 1: getTime() ===");
            System.out.println("Initial time: " + initialTime + " == 0.0");

            // then
            assert initialTime == 0.0 : "Error: initial time should be 0.0";
        }

        // case 2: testing advanceTimeTo()
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(2, false);

            // when
            dispatcher.advanceTimeTo(5.0);
            double newTime = dispatcher.getTime();

            System.out.println("\n=== case 2: advanceTimeTo() ===");
            System.out.println("Time after advanceTimeTo(5.0): " + newTime + " == 5.0");

            // then
            assert newTime == 5.0 : "Error in advanceTimeTo()";
        }

        // case 3: testing handleJob() and getNumJobsHandled()
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(2, false);
            Job job1 = new Job(1.0, 10.0);
            Job job2 = new Job(2.0, 5.0);

            // when
            dispatcher.handleJob(job1);
            int countAfterFirst = dispatcher.getNumJobsHandled();
            dispatcher.handleJob(job2);
            int countAfterSecond = dispatcher.getNumJobsHandled();

            System.out.println("\n=== case 3: handleJob() and getNumJobsHandled() ===");
            System.out.println("Jobs handled after first: " + countAfterFirst + " == 1");
            System.out.println("Jobs handled after second: " + countAfterSecond + " == 2");

            // then
            assert countAfterFirst == 1 : "Error in handleJob() or getNumJobsHandled()";
            assert countAfterSecond == 2 : "Error in handleJob() or getNumJobsHandled()";
        }

        // case 4: testing handleJob() advances time to job arrival
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(2, false);
            Job job = new Job(5.0, 10.0);  // arrives at time 5.0

            // when
            dispatcher.handleJob(job);
            double timeAfterHandle = dispatcher.getTime();

            System.out.println("\n=== case 4: handleJob() advances time ===");
            System.out.println("Time after handling job arriving at 5.0: " + timeAfterHandle + " == 5.0");

            // then
            assert timeAfterHandle == 5.0 : "Error: handleJob should advance time to job arrival";
        }

        // case 5: testing finishUp()
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(1, false);  // 1 server for simplicity
            Job job = new Job(0.0, 10.0);  // needs 10 units of processing
            dispatcher.handleJob(job);

            // when
            dispatcher.finishUp();

            System.out.println("\n=== case 5: finishUp() ===");
            System.out.println("Time after finishUp: " + dispatcher.getTime() + " >= 10.0");
            System.out.println("Job is finished: " + job.isFinished());

            // then
            assert dispatcher.getTime() >= 10.0 : "Error: finishUp should advance time until all jobs done";
            assert job.isFinished() : "Error: job should be finished after finishUp";
        }

        // case 6: testing getAverageWaitingTime()
        {
            // given
            RoundRobinDispatcher dispatcher = new RoundRobinDispatcher(1, false);  // 1 server
            Job job1 = new Job(0.0, 5.0);   // needs 5 units, finishes at 5, wait = 5
            Job job2 = new Job(0.0, 5.0);   // needs 5 units, finishes at 10, wait = 10
            dispatcher.handleJob(job1);
            dispatcher.handleJob(job2);

            // when
            dispatcher.finishUp();
            double avgWait = dispatcher.getAverageWaitingTime();

            System.out.println("\n=== case 6: getAverageWaitingTime() ===");
            System.out.println("Average waiting time: " + avgWait + " == 7.5");

            // then
            // job1 waits 5, job2 waits 10 -> average = (5+10)/2 = 7.5
            assert avgWait == 7.5 : "Error in getAverageWaitingTime() expected 7.5, got " + avgWait;
        }

        // case 7: testing getServerList()
        {
            // given
            RandomDispatcher dispatcher = new RandomDispatcher(5, false);

            // when
            ArrayList<Server> servers = dispatcher.getServerList();

            System.out.println("\n=== case 7: getServerList() ===");
            System.out.println("Server list size: " + servers.size() + " == 5");
            System.out.println("Servers are not null: " + (servers.get(0) != null));

            // then
            assert servers.size() == 5 : "Error in getServerList()";
            for (Server s : servers) {
                assert s != null : "Error: server in list should not be null";
            }
        }

        System.out.println("\n*** Done testing JobDispatcher common methods! ***\n");
    }

    public static void main(String[] args) {
        randomDispatcherTests();
        roundRobinDispatcherTests();
        shortestQueueDispatcherTests();
        leastWorkDispatcherTests();
        jobDispatcherCommonTests();

        System.out.println("========================================");
        System.out.println("All Dispatcher tests completed!");
        System.out.println("========================================");
    }
}
