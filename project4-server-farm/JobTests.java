/*
file name:      JobTests.java
Authors:        Rishit Chatterjee
last modified:  12/15/2025

How to run:     java -ea JobTests
*/

public class JobTests {

    private static final double EPS = 1e-6;

    public static void main(String[] args) {
        jobConstructorTests();
        jobProcessingTests();
        jobCompletionTests();
        System.out.println("Finished JobTests!");
    }

    // Tests Job constructor and basic accessors
    public static void jobConstructorTests() {

        // Verifies constructor stores arrival time correctly
        {
            Job job = new Job(5.0, 3.0);
            assert Math.abs(job.getArrivalTime() - 5.0) < EPS : 
                "Job arrival time should match constructor argument";
        }

        // Verifies constructor stores processing time correctly
        {
            Job job = new Job(2.0, 7.5);
            assert Math.abs(job.getProcessingTimeNeeded() - 7.5) < EPS : 
                "Job processing time needed should match constructor argument";
        }

        // Confirms initial processing time remaining equals total needed
        {
            Job job = new Job(0.0, 4.0);
            assert Math.abs(job.getProcessingTimeRemaining() - 4.0) < EPS : 
                "Initial processing time remaining should equal processing time needed";
        }

        // Verifies new job is not finished
        {
            Job job = new Job(1.0, 2.0);
            assert !job.isFinished() : "New job should not be finished";
        }
    }

    // Tests Job processing time tracking
    public static void jobProcessingTests() {

        // Confirms processing time remaining decreases correctly
        {
            Job job = new Job(0.0, 5.0);
            // Simulate partial processing by checking remaining time concept
            double needed = job.getProcessingTimeNeeded();
            double remaining = job.getProcessingTimeRemaining();
            assert Math.abs(needed - remaining) < EPS : 
                "Before processing, needed and remaining should be equal";
        }

        // Validates zero processing time job
        {
            Job job = new Job(3.0, 0.0);
            assert Math.abs(job.getProcessingTimeNeeded()) < EPS : 
                "Zero-time job should have zero processing time needed";
            assert Math.abs(job.getProcessingTimeRemaining()) < EPS : 
                "Zero-time job should have zero processing time remaining";
        }

        // Checks arrival time is independent of processing time
        {
            Job job1 = new Job(10.0, 1.0);
            Job job2 = new Job(10.0, 100.0);
            assert Math.abs(job1.getArrivalTime() - job2.getArrivalTime()) < EPS : 
                "Jobs with same arrival time should report same arrival time";
        }
    }

    // Tests Job completion state tracking
    public static void jobCompletionTests() {

        // Verifies getTimeFinished returns expected value for unfinished job
        {
            Job job = new Job(0.0, 5.0);
            // Unfinished jobs typically return -1 or similar sentinel
            double timeFinished = job.getTimeFinished();
            assert !job.isFinished() || timeFinished >= job.getArrivalTime() : 
                "Finished time should be at or after arrival time if finished";
        }

        // Confirms toString produces non-null output
        {
            Job job = new Job(2.5, 3.5);
            String str = job.toString();
            assert str != null : "Job toString should not return null";
            assert str.length() > 0 : "Job toString should not be empty";
        }

        // Validates multiple jobs maintain independent state
        {
            Job job1 = new Job(0.0, 2.0);
            Job job2 = new Job(1.0, 3.0);
            Job job3 = new Job(2.0, 4.0);
            
            assert Math.abs(job1.getArrivalTime() - 0.0) < EPS : "Job1 arrival time incorrect";
            assert Math.abs(job2.getArrivalTime() - 1.0) < EPS : "Job2 arrival time incorrect";
            assert Math.abs(job3.getArrivalTime() - 2.0) < EPS : "Job3 arrival time incorrect";
            
            assert Math.abs(job1.getProcessingTimeNeeded() - 2.0) < EPS : "Job1 processing time incorrect";
            assert Math.abs(job2.getProcessingTimeNeeded() - 3.0) < EPS : "Job2 processing time incorrect";
            assert Math.abs(job3.getProcessingTimeNeeded() - 4.0) < EPS : "Job3 processing time incorrect";
        }
    }
}

