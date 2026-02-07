/*
file name:      ServerTests.java
Authors:        Najam Tariq
last modified:  12/13/2025

How to run:     java -ea ServerTests
*/

public class ServerTests {

    public static void serverTests() {

        // case 1: testing Server() constructor
        {
            // given
            Server server = new Server();

            // when
            System.out.println("=== case 1: Server() constructor ===");
            System.out.println("Server created: " + server);

            // then
            assert server != null : "Error in Server::Server()";
            assert server.size() == 0 : "Error: new Server should have empty queue";
            assert server.remainingWorkInQueue() == 0.0 : "Error: new Server should have 0 remaining work";
            assert server.getTotalWaitingTime() == 0.0 : "Error: new Server should have 0 total waiting time";
        }

        // case 2: testing addJob() and size()
        {
            // given
            Server server = new Server();
            Job job1 = new Job(0.0, 10.0);  // arrives at 0, needs 10 units of processing
            Job job2 = new Job(1.0, 5.0);   // arrives at 1, needs 5 units of processing

            // when
            server.addJob(job1);
            int sizeAfterFirst = server.size();
            server.addJob(job2);
            int sizeAfterSecond = server.size();

            System.out.println("\n=== case 2: addJob() and size() ===");
            System.out.println("Size after first job: " + sizeAfterFirst + " == 1");
            System.out.println("Size after second job: " + sizeAfterSecond + " == 2");

            // then
            assert sizeAfterFirst == 1 : "Error in Server::addJob() or size()";
            assert sizeAfterSecond == 2 : "Error in Server::addJob() or size()";
        }

        // case 3: testing remainingWorkInQueue() after adding jobs
        {
            // given
            Server server = new Server();
            Job job1 = new Job(0.0, 10.0);  // needs 10 units
            Job job2 = new Job(1.0, 5.0);   // needs 5 units

            // when
            server.addJob(job1);
            double workAfterFirst = server.remainingWorkInQueue();
            server.addJob(job2);
            double workAfterSecond = server.remainingWorkInQueue();

            System.out.println("\n=== case 3: remainingWorkInQueue() ===");
            System.out.println("Work after first job: " + workAfterFirst + " == 10.0");
            System.out.println("Work after second job: " + workAfterSecond + " == 15.0");

            // then
            assert workAfterFirst == 10.0 : "Error in Server::remainingWorkInQueue()";
            assert workAfterSecond == 15.0 : "Error in Server::remainingWorkInQueue()";
        }

        // case 4: testing processTo() - processing a single job to completion
        {
            // given
            Server server = new Server();
            Job job = new Job(0.0, 10.0);  // arrives at 0, needs 10 units
            server.addJob(job);

            // when
            server.processTo(10.0);  // process for 10 units (enough to finish the job)

            System.out.println("\n=== case 4: processTo() - single job completion ===");
            System.out.println("Size after processing: " + server.size() + " == 0");
            System.out.println("Remaining work: " + server.remainingWorkInQueue() + " == 0.0");
            System.out.println("Job finished: " + job.isFinished() + " == true");

            // then
            assert server.size() == 0 : "Error in Server::processTo() - job not removed from queue";
            assert server.remainingWorkInQueue() == 0.0 : "Error in Server::processTo() - remaining work not updated";
            assert job.isFinished() : "Error in Server::processTo() - job not marked as finished";
        }

        // case 5: testing processTo() - partial processing
        {
            // given
            Server server = new Server();
            Job job = new Job(0.0, 10.0);  // needs 10 units
            server.addJob(job);

            // when
            server.processTo(5.0);  // only process for 5 units

            System.out.println("\n=== case 5: processTo() - partial processing ===");
            System.out.println("Size after partial processing: " + server.size() + " == 1");
            System.out.println("Remaining work: " + server.remainingWorkInQueue() + " == 5.0");
            System.out.println("Job finished: " + job.isFinished() + " == false");

            // then
            assert server.size() == 1 : "Error in Server::processTo() - job removed too early";
            assert server.remainingWorkInQueue() == 5.0 : "Error in Server::processTo() - remaining work incorrect";
            assert !job.isFinished() : "Error in Server::processTo() - job marked finished too early";
        }

        // case 6: testing processTo() - multiple jobs FIFO order
        {
            // given
            Server server = new Server();
            Job job1 = new Job(0.0, 5.0);   // needs 5 units
            Job job2 = new Job(0.0, 10.0);  // needs 10 units
            server.addJob(job1);
            server.addJob(job2);

            // when - process enough time to finish first job and partially process second
            server.processTo(8.0);

            System.out.println("\n=== case 6: processTo() - FIFO order ===");
            System.out.println("Size after processing: " + server.size() + " == 1");
            System.out.println("First job finished: " + job1.isFinished() + " == true");
            System.out.println("Second job finished: " + job2.isFinished() + " == false");
            System.out.println("Remaining work: " + server.remainingWorkInQueue() + " == 7.0");

            // then
            assert server.size() == 1 : "Error in Server::processTo() - FIFO order not followed";
            assert job1.isFinished() : "Error in Server::processTo() - first job should be finished";
            assert !job2.isFinished() : "Error in Server::processTo() - second job finished too early";
            assert server.remainingWorkInQueue() == 7.0 : "Error in Server::processTo() - remaining work incorrect";
        }

        // case 7: testing processTo() - finish all jobs
        {
            // given
            Server server = new Server();
            Job job1 = new Job(0.0, 5.0);
            Job job2 = new Job(0.0, 10.0);
            server.addJob(job1);
            server.addJob(job2);

            // when - process enough time to finish both jobs
            server.processTo(15.0);

            System.out.println("\n=== case 7: processTo() - finish all jobs ===");
            System.out.println("Size: " + server.size() + " == 0");
            System.out.println("Job1 finished: " + job1.isFinished() + " == true");
            System.out.println("Job2 finished: " + job2.isFinished() + " == true");
            System.out.println("Remaining work: " + server.remainingWorkInQueue() + " == 0.0");

            // then
            assert server.size() == 0 : "Error in Server::processTo()";
            assert job1.isFinished() : "Error in Server::processTo()";
            assert job2.isFinished() : "Error in Server::processTo()";
            assert server.remainingWorkInQueue() == 0.0 : "Error in Server::processTo()";
        }

        // case 8: testing getTotalWaitingTime() after processing jobs
        {
            // given
            Server server = new Server();
            Job job1 = new Job(0.0, 5.0);   // arrives at 0, needs 5 units -> finishes at 5, waits 5
            Job job2 = new Job(0.0, 5.0);   // arrives at 0, needs 5 units -> finishes at 10, waits 10
            server.addJob(job1);
            server.addJob(job2);

            // when
            server.processTo(10.0);
            double totalWait = server.getTotalWaitingTime();

            System.out.println("\n=== case 8: getTotalWaitingTime() ===");
            System.out.println("Total waiting time: " + totalWait + " == 15.0");

            // then
            // job1 waits 5 (0 to 5), job2 waits 10 (0 to 10) -> total = 15
            assert totalWait == 15.0 : "Error in Server::getTotalWaitingTime() expected 15.0, got " + totalWait;
        }

        // case 9: testing processTo() with empty queue (no jobs to process)
        {
            // given
            Server server = new Server();

            // when
            server.processTo(10.0);  // advance time with no jobs

            System.out.println("\n=== case 9: processTo() - empty queue ===");
            System.out.println("Size: " + server.size() + " == 0");
            System.out.println("Remaining work: " + server.remainingWorkInQueue() + " == 0.0");

            // then
            assert server.size() == 0 : "Error in Server::processTo() with empty queue";
            assert server.remainingWorkInQueue() == 0.0 : "Error in Server::processTo() with empty queue";
        }

        // case 10: testing processTo() called multiple times
        {
            // given
            Server server = new Server();
            Job job = new Job(0.0, 10.0);
            server.addJob(job);

            // when - process in chunks
            server.processTo(3.0);
            double workAfterFirst = server.remainingWorkInQueue();
            server.processTo(6.0);
            double workAfterSecond = server.remainingWorkInQueue();
            server.processTo(10.0);
            double workAfterThird = server.remainingWorkInQueue();

            System.out.println("\n=== case 10: processTo() - incremental processing ===");
            System.out.println("Remaining after processTo(3): " + workAfterFirst + " == 7.0");
            System.out.println("Remaining after processTo(6): " + workAfterSecond + " == 4.0");
            System.out.println("Remaining after processTo(10): " + workAfterThird + " == 0.0");

            // then
            assert workAfterFirst == 7.0 : "Error in Server::processTo() incremental";
            assert workAfterSecond == 4.0 : "Error in Server::processTo() incremental";
            assert workAfterThird == 0.0 : "Error in Server::processTo() incremental";
            assert job.isFinished() : "Error in Server::processTo() - job should be finished";
        }

        System.out.println("\n*** Done testing Server! ***\n");
    }

    public static void main(String[] args) {
        serverTests();
    }
}
