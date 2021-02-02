import org.w3c.dom.html.HTMLImageElement;

import java.util.concurrent.*;

/**
 * Created by glenc on Feb 2021
 **/
public class Main {

    public static void main(String[] args) {
        firstThread();

        try {
            storingRefsToMultipleThread();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        usingThreadPool();

        usingThreadPoolCallable();
    }

    private static void firstThread() {

        String[] names = {"GLen", "Weny", "Wendy", "Faith", "fay", "Gee", "Michael", "clive", "CLIVE", "Gety"};
        String[] surnames = {"Chiridza", "Cee", "Wee", "Matingo", "Living", "<Mighter>", "Matingo", "congress", "Chiridza", "Tachiona"};
        //loop through the threads to  work on each looped item result on  a separate thread
        for (int i = 0; i < names.length; i++) {
            Adder adder = new Adder(names[i], surnames[i]);
            Thread thread = new Thread(adder);
            thread.start();
        }

        //problem with this approach
//        if our threads are in the background and the first one is done and the other
//        other threads are still working, if the main thread has no more work,
//        it may terminate before our other threads are done with their work and the entire process get shutdown
    }


    private static void storingRefsToMultipleThread() throws InterruptedException {
//store refs to multiple thread, this helps with the problem in the firstThread() implementation
        //make the main thread wait till our background thread is done

        String[] names = {"GLen", "Weny", "Wendy", "Faith", "fay", "Gee", "Michael", "clive", "CLIVE", "Gety"};
        String[] surnames = {"Chiridza", "Cee", "Wee", "Matingo", "Living", "<Mighter>", "Matingo", "congress", "Chiridza", "Tachiona"};

//        create an array of threads, thus able to store references of multiple threads
        Thread[] threads = new Thread[names.length];

        for (int i = 0; i < names.length; i++) {
            Adder adder = new Adder(names[i], surnames[i]);
            threads[i] = new Thread(adder);
            threads[i].start();
        }

        //to block the main thread, so it waits till the background work finishes
        for (Thread thread : threads) {
            thread.join(); // blocks main to wait for thread completion
        }


        //problem
        //if we have many tasks and each is assigned its own thread, it may be heavy on our system and slow down and probably crash
    }


//    thread pools
//            create a queue for tasks
//            assigns tasks into a pool of thread
//              handles details of managing threads

    private static void usingThreadPool() {
        String[] names = {"GLen", "Weny", "Wendy", "Faith", "fay", "Gee", "Michael", "clive", "CLIVE", "Gety"};
        String[] surnames = {"Chiridza", "Cee", "Wee", "Matingo", "Living", "<Mighter>", "Matingo", "congress", "Chiridza", "Tachiona"};

//        limit the number of thread pools that can exist per time
        ExecutorService es = Executors.newFixedThreadPool(3);


        for (int i = 0; i < names.length; i++) {
            Adder adder = new Adder(names[i], surnames[i]);
            es.submit(adder); //takes in the class that implements a runnable, or a runnable itself and submit into the thread pool
        }

        try {
            es.shutdown(); //shut it down when done,  == the first 3 threads and is there are subsequent ones, we do the next line to main thread
            es.awaitTermination(60, TimeUnit.SECONDS); //main thread should wait for the thread pool to finish the rest of the work, since we specified 3 pools at a time
            //once all job is done, await termination will return and our main thread will shutdown
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


//to know if a task succeeded or not
    // caller may need to get the results back
    //pass the results or exceptions from background thread directly to main thread
    //methods that allow us to do so
    //Callable interface - represents a task that can be run on a thread, like runnable except it can return results and exceptions
    //Future Interface - represents results of a thread task returned by ExecutorService.submit()
    //get method on future blocks until task completes, returns callable interface result
//    Future is where the Callable can harvest the results of a background thread from

    private static void usingThreadPoolCallable() {
        String[] names = {"GLen", "Weny", "Wendy", "Faith", "fay", "Gee", "Michael", "clive", "CLIVE", "Gety"};
        String[] surnames = {"Chiridza", "Cee", "Wee", "Matingo", "Living", "<Mighter>", "Matingo", "congress", "Chiridza", "Tachiona"};

//        limit the number of thread pools that can exist per time
        ExecutorService es = Executors.newFixedThreadPool(3);
        //future represent a background task
        Future<Integer>[] results = new Future[names.length];

        for (int i = 0; i < names.length; i++) {
            AdderCaller adder = new AdderCaller(names[i], surnames[i]);
            results[i] = es.submit(adder);
        }

        //getting our results back
        for (Future<Integer> result : results) {

            try {
                int value = result.get();//blocks until return value available
                System.out.println("Total: " + value);
            } catch (ExecutionException | InterruptedException e) {
                Throwable adderEx = e.getCause(); //get adder exception
                System.out.println("Error: adderEx");
            }
        }
    }

}
